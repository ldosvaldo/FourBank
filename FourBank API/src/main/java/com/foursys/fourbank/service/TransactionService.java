package com.foursys.fourbank.service;

import com.foursys.fourbank.dto.*;
import com.foursys.fourbank.enums.TransactionDirection;
import com.foursys.fourbank.enums.TransactionType;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.exception.InvalidValueException;
import com.foursys.fourbank.exception.UnauthorizedOperationException;
import com.foursys.fourbank.exception.UnreportedEssentialFieldException;

import com.foursys.fourbank.model.*;
import com.foursys.fourbank.repository.*;
import com.foursys.fourbank.util.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private SavingsAccountRepository savingsAccountRepository;
	@Autowired
	private TransactionalAccountRepository transactionalAccountRepository;
	@Autowired
	private OtherAccountRepository otherAccountRepository;
	@Autowired
	private PixRepository pixRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private CreditCardBillRepository creditCardBillRepository;
	@Autowired
	private UserRepository userRepository;

	PixChargeFORM pixChargeFORM = new PixChargeFORM();

	public TransactionDTO getTransactionById(Long id) {
		return Converter.transactionToDTO(transactionRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("There is no transaction with given id");
		}));
	}

	// Trocar em transactionController

	public PixChargeDTO pixCharge(Long userId, PixChargeFORM pixChargeFORM) {
	        Optional<User> userOpt = userRepository.findById(userId);

	        if(userOpt.isPresent()) {
	            if(pixChargeFORM.getValue() == null) throw new UnreportedEssentialFieldException("value attribute not informed");
	            this.pixChargeFORM.setValue(pixChargeFORM.getValue());

	            this.pixChargeFORM.getDestinationAccount().setUserName(userOpt.get().getName());
	            this.pixChargeFORM.getDestinationAccount().setBankName("FutureBank");
	            this.pixChargeFORM.getDestinationAccount().setCpf(userOpt.get().getCpf());

	            PixChargeDTO pixChargeDTO = new PixChargeDTO();
	            pixChargeDTO.setPixCopyAndPaste("00020126330014BR.GOV.BCB.PIX011106160032933520400005303986540530.005802BR5925"
	                                          + userOpt.get().getName() + " 6009SAO PAULO61080540900062070503***63041F1E");

	            return pixChargeDTO;
	        }

	        throw new EntityNotFoundException("user id does not exist.");
	    }

	    public PixChargeFORM getPixCharge(Long accountId) {

	        Optional<TransactionalAccount> transactionalAccountOpt = transactionalAccountRepository.findById(accountId);
	        Optional<SavingsAccount> savingsAccountOpt = savingsAccountRepository.findById(accountId);

	        if(transactionalAccountOpt.isPresent()) {
	            this.pixChargeFORM.getDestinationAccount().setAgency(transactionalAccountOpt.get().getAgency());
	            this.pixChargeFORM.getDestinationAccount().setBankNumber(transactionalAccountOpt.get().getBankNumber());
	            this.pixChargeFORM.getDestinationAccount().setNumber(transactionalAccountOpt.get().getNumber());
	            this.pixChargeFORM.getDestinationAccount().setAccountType("Corrente");

	            return this.pixChargeFORM;
	        } else if(savingsAccountOpt.isPresent()) {
	            this.pixChargeFORM.getDestinationAccount().setAgency(savingsAccountOpt.get().getAgency());
	            this.pixChargeFORM.getDestinationAccount().setBankNumber(savingsAccountOpt.get().getBankNumber());
	            this.pixChargeFORM.getDestinationAccount().setNumber(savingsAccountOpt.get().getNumber());
	            this.pixChargeFORM.getDestinationAccount().setAccountType("Poupança");

	            return this.pixChargeFORM;
	        }

	        throw new EntityNotFoundException("account id does not exist.");
	    }

	public TransactionDTO savePix(PixTransactionDTO pixTransactionDTO) {
		if (pixTransactionDTO.getValue() == null)
			throw new UnreportedEssentialFieldException("value attribute not informed");
		else if (pixTransactionDTO.getValue() <= 0)
			throw new InvalidValueException("attribute value must be a positive value");

		if (pixTransactionDTO.getIdSourceAccount() == null)
			throw new UnreportedEssentialFieldException("the idSourceAccount attribute was not informed");

		if (pixTransactionDTO.getPixKeys() == null)
			throw new UnreportedEssentialFieldException("the pixKeys attribute was not informed");
		else if (pixTransactionDTO.getPixKeys().getPixType() == null)
			throw new UnreportedEssentialFieldException("the pixType attribute was not informed");
		else if (pixTransactionDTO.getPixKeys().getPixKeyValue() == null)
			throw new UnreportedEssentialFieldException("the pixKeyValue attribute was not informed");

		Transaction transaction = new Transaction();

		transaction.setValue(pixTransactionDTO.getValue());

		transaction.setAppointmentDate(LocalDateTime.now());

		if (pixTransactionDTO.getTransactionDate() == null)
			transaction.setTransactionDate(LocalDateTime.now());
		else if (pixTransactionDTO.getTransactionDate().compareTo(LocalDateTime.now()) < 0)
			throw new InvalidValueException("transactionDate attribute value is invalid");
		else
			transaction.setTransactionDate(pixTransactionDTO.getTransactionDate());

		transaction.setTransactionType(TransactionType.PIX);

		transaction.setTransactionDirection(TransactionDirection.EXIT);

		Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository
				.findById(pixTransactionDTO.getIdSourceAccount());
		Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository
				.findById(pixTransactionDTO.getIdSourceAccount());

		Account destinationAccount;
		if (optionalTransactionalAccount.isPresent()) {
			TransactionalAccount transactionalAccount = optionalTransactionalAccount.get();
			if (transactionalAccount.getBalance() + transactionalAccount.getBalanceLimit() < pixTransactionDTO
					.getValue())
				throw new InvalidValueException("insufficient balance");

			transaction.setDestinationAccount(getAndCreditDestinationAccount(pixTransactionDTO));

			transactionalAccount.setBalance(transactionalAccount.getBalance() - pixTransactionDTO.getValue());
			transactionalAccountRepository.save(transactionalAccount);
			transaction.setSourceAccount(transactionalAccount);
		} else if (optionalSavingsAccount.isPresent()) {
			SavingsAccount savingsAccount = optionalSavingsAccount.get();
			if (savingsAccount.getBalance() < pixTransactionDTO.getValue())
				throw new InvalidValueException("insufficient balance");

			transaction.setDestinationAccount(getAndCreditDestinationAccount(pixTransactionDTO));

			savingsAccount.setBalance(savingsAccount.getBalance() - pixTransactionDTO.getValue());
			savingsAccountRepository.save(savingsAccount);
			transaction.setSourceAccount(savingsAccount);
		} else {
			throw new EntityNotFoundException("There is no account with the given id");
		}

		return Converter.transactionToDTO(transactionRepository.save(transaction));
	}

	private Account getAndCreditDestinationAccount(PixTransactionDTO pixTransactionDTO) {
		Pix pix = pixRepository.findPixByPixTypeAndPixKeyValue(pixTransactionDTO.getPixKeys().getPixType(),
				pixTransactionDTO.getPixKeys().getPixKeyValue());
		if (pix == null)
			throw new EntityNotFoundException("There is no account with the given pix");
		Account destinationAccount = pix.getAccount();

		if (transactionalAccountRepository.existsById(destinationAccount.getId())) {
			TransactionalAccount destinationTransactionalAccount = transactionalAccountRepository
					.findById(destinationAccount.getId()).get();
			destinationTransactionalAccount
					.setBalance(destinationTransactionalAccount.getBalance() + pixTransactionDTO.getValue());
			return transactionalAccountRepository.save(destinationTransactionalAccount);
		} else if (savingsAccountRepository.existsById(destinationAccount.getId())) {
			SavingsAccount destinationSavingsAccount = savingsAccountRepository.findById(destinationAccount.getId())
					.get();
			destinationSavingsAccount.setBalance(destinationSavingsAccount.getBalance() + pixTransactionDTO.getValue());
			return savingsAccountRepository.save(destinationSavingsAccount);
		} else {
			return otherAccountRepository.findById(destinationAccount.getId()).get();
		}
	}

	public TransactionDTO saveTransfer(TransactionDTO transactionDTO) {
		if (transactionDTO.getValue() == null)
			throw new UnreportedEssentialFieldException("value attribute not informed");
		else if (transactionDTO.getValue() <= 0)
			throw new InvalidValueException("attribute value must be a positive value");

		if (transactionDTO.getSourceAccount() == null || transactionDTO.getSourceAccount().getId() == null)
			throw new UnreportedEssentialFieldException("sourceAccount attribute not informed");

		if (transactionDTO.getDestinationAccount() == null)
			throw new UnreportedEssentialFieldException("destinationAccount attribute not informed");
		else if (transactionDTO.getDestinationAccount().getAgency() == null)
			throw new UnreportedEssentialFieldException("agency attribute not informed");
		else if (transactionDTO.getDestinationAccount().getNumber() == null)
			throw new UnreportedEssentialFieldException("number attribute not informed");
		else if (transactionDTO.getDestinationAccount().getBankNumber() == null)
			throw new UnreportedEssentialFieldException("bankNumber attribute not informed");

		Transaction transaction = new Transaction();

		transaction.setValue(transactionDTO.getValue());

		transaction.setAppointmentDate(LocalDateTime.now());

		if (transactionDTO.getTransactionDate() == null)
			transaction.setTransactionDate(LocalDateTime.now());
		else if (transactionDTO.getTransactionDate().compareTo(LocalDateTime.now()) < 0)
			throw new InvalidValueException("transaction date attribute value is invalid");
		else
			transaction.setTransactionDate(transactionDTO.getTransactionDate());

		transaction.setTransactionType(TransactionType.TRANSFER);

		transaction.setTransactionDirection(TransactionDirection.EXIT);

		Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository
				.findById(transactionDTO.getSourceAccount().getId());
		Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository
				.findById(transactionDTO.getSourceAccount().getId());

		if (optionalTransactionalAccount.isPresent()) {
			TransactionalAccount transactionalAccount = optionalTransactionalAccount.get();
			if (transactionalAccount.getBalance() + transactionalAccount.getBalanceLimit() < transactionDTO.getValue())
				throw new InvalidValueException("insufficient balance");
			transaction.setDestinationAccount(findAndCreditToOtherAccount(transactionDTO.getValue(),
					transactionDTO.getDestinationAccount().getBankNumber(),
					transactionDTO.getDestinationAccount().getAgency(),
					transactionDTO.getDestinationAccount().getNumber()));
			transactionalAccount.setBalance(transactionalAccount.getBalance() - transactionDTO.getValue());
			transactionalAccountRepository.save(transactionalAccount);
			transaction.setSourceAccount(transactionalAccount);
		} else if (optionalSavingsAccount.isPresent()) {
			SavingsAccount savingsAccount = optionalSavingsAccount.get();
			if (savingsAccount.getBalance() < transactionDTO.getValue())
				throw new InvalidValueException("insufficient balance");
			transaction.setDestinationAccount(findAndCreditToOtherAccount(transactionDTO.getValue(),
					transactionDTO.getDestinationAccount().getBankNumber(),
					transactionDTO.getDestinationAccount().getAgency(),
					transactionDTO.getDestinationAccount().getNumber()));
			savingsAccount.setBalance(savingsAccount.getBalance() - transactionDTO.getValue());
			savingsAccountRepository.save(savingsAccount);
			transaction.setSourceAccount(savingsAccount);
		} else {
			throw new EntityNotFoundException("There is no account with the given id");
		}

		return Converter.transactionToDTO(transactionRepository.save(transaction));
	}

	private Account findAndCreditToOtherAccount(Double value, String bankNumber, String agency, String number) {
		System.out.println("\n\n--------------------------------------------------------------------------");
		System.out.println("value = " + value);
		System.out.println("bankNumber = " + bankNumber);
		System.out.println("agency = " + agency);
		System.out.println("number = " + number);
		System.out.println("---------------------------------------------------------------------------------\n\n");
		if (bankNumber.equals("001")) {
			TransactionalAccount transactionalAccount = transactionalAccountRepository
					.findTransactionalAccountByAgencyAndNumber(agency, number);
			if (transactionalAccount != null) {
				transactionalAccount.setBalance(transactionalAccount.getBalance() + value);
				return transactionalAccountRepository.save(transactionalAccount);
			}

			SavingsAccount savingsAccount = savingsAccountRepository.findSavingsAccountByAgencyAndNumber(agency,
					number);
			if (savingsAccount != null) {
				savingsAccount.setBalance(savingsAccount.getBalance() + value);
				return savingsAccountRepository.save(savingsAccount);
			}

			throw new EntityNotFoundException("There is no account with the agency and the number provided");
		}
		OtherAccount otherAccount = otherAccountRepository.findOtherAccountByBankNumberAndAgencyAndNumber(bankNumber,
				agency, number);
		if (otherAccount != null)
			return otherAccount;
		throw new EntityNotFoundException("There is no account with the agency and the number provided");
	}

	@Transactional
	public PurchaseDTO save(Long idCreditCard, PurchaseDTO purchaseDto) {

		CreditCard creditCard = creditCardRepository.findById(idCreditCard)
				.orElseThrow(() -> new EntityNotFoundException("could not find an card with this id"));

		if (creditCard.getIsBlocked()) {
			throw new UnauthorizedOperationException("The card is blocked");
		}

		Purchase purchase = new Purchase();
		purchase.setDate(LocalDate.now());
		if (purchaseDto.getValue() == null)
			throw new UnsupportedOperationException("Value attribute not informed");
		if (purchaseDto.getValue() <= 0)
			throw new InvalidValueException("The value must be greater than 0");
		purchase.setValue(purchaseDto.getValue());
		if (purchaseDto.getStore() == null)
			throw new UnsupportedOperationException("Store attribute not informed");
		purchase.setStore(purchaseDto.getStore());

		if (purchaseDto.getValue() > creditCard.getAvailableLimit()) {
			throw new UnauthorizedOperationException("insufficient limit");
		}

		CreditCardBill creditCardBill = new CreditCardBill();
		if (creditCard.getBills().size() == 0) {
			creditCardBill.setCreditCard(creditCard);
			LocalDate dueDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
			dueDate = dueDate.plusMonths(1);
			creditCardBill.setDueDate(dueDate);
			creditCardBill.setIsOverdue(false);
			creditCardBill.setIsPaid(false);
			creditCardBill.setValue(0.0);
			creditCardBill = creditCardBillRepository.save(creditCardBill);
		}

		creditCard = creditCardRepository.findById(idCreditCard).get();
		
		List<CreditCardBill> creditCardBillList = creditCardBillRepository.findCreditCardBillsByCreditCard(creditCard);
		CreditCardBill lastBill = creditCardBillList.get(creditCardBillList.size() - 1);
		lastBill.setValue(lastBill.getValue() + purchase.getValue());
		lastBill = creditCardBillRepository.save(lastBill);
		purchase.setCreditCardBill(lastBill);

		creditCard.setAvailableLimit(creditCard.getAvailableLimit() - purchase.getValue());

		Converter converterPurchaseDto = new Converter();
		PurchaseDTO purchaseDto1 = converterPurchaseDto.purchaseToDto(purchaseRepository.save(purchase));

		return purchaseDto1;
	}

    public Object savePaymentDebit(TransactionDTO transactionDTO) {
        if(transactionDTO.getValue() == null) throw new UnreportedEssentialFieldException("value attribute not informed");
        else if(transactionDTO.getValue() <= 0) throw new InvalidValueException("attribute value must be a positive value");

        if(transactionDTO.getId() == null) throw new UnreportedEssentialFieldException("ID attribute not informed");

        Transaction transaction = new Transaction();

        transaction.setValue(transactionDTO.getValue());

        transaction.setAppointmentDate(LocalDateTime.now());

        if(transactionDTO.getTransactionDate() == null) transaction.setTransactionDate(LocalDateTime.now());
        else if(transactionDTO.getTransactionDate().compareTo(LocalDateTime.now()) < 0) throw new InvalidValueException("transaction date attribute value is invalid");
        else transaction.setTransactionDate(transactionDTO.getTransactionDate());

        transaction.setTransactionType(TransactionType.TRANSFER);

        transaction.setTransactionDirection(TransactionDirection.EXIT);

        Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository.findById(transactionDTO.getId());
        Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository.findById(transactionDTO.getId());

        if(optionalTransactionalAccount.isPresent()) {
            TransactionalAccount transactionalAccount = optionalTransactionalAccount.get();

            transactionalAccount.setBalance(transactionalAccount.getBalance() - transactionDTO.getValue());
            transactionalAccountRepository.save(transactionalAccount);

        } else if(optionalSavingsAccount.isPresent()) {
            SavingsAccount savingsAccount = optionalSavingsAccount.get();
            if(savingsAccount.getBalance() < transactionDTO.getValue()) throw new InvalidValueException("insufficient balance");

            savingsAccount.setBalance(savingsAccount.getBalance() - transactionDTO.getValue());
            savingsAccountRepository.save(savingsAccount);

        } else {
            throw new EntityNotFoundException("There is no account with the given id");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
