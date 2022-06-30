package com.foursys.fourbank.util;

import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.dto.AddressDTO;
import com.foursys.fourbank.dto.TransactionDTO;
import com.foursys.fourbank.dto.PurchaseDTO;
import com.foursys.fourbank.dto.UserDTO;
import com.foursys.fourbank.exception.UnreportedEssentialFieldException;
import com.foursys.fourbank.model.*;
import com.foursys.fourbank.dto.*;
import com.foursys.fourbank.model.CreditCard;
import com.foursys.fourbank.model.DebitCard;
import com.foursys.fourbank.exception.InvalidValueException;
import com.foursys.fourbank.model.Address;
import com.foursys.fourbank.model.Purchase;
import com.foursys.fourbank.model.User;

public class Converter {
    public static User dtoToUser(UserDTO userDTO) {
        User user = new User();

        if(userDTO.getName() != null) user.setName(userDTO.getName());
        if(userDTO.getCpf() != null) user.setCpf(userDTO.getCpf());
        if(userDTO.getBirthDate() != null) user.setBirthDate(userDTO.getBirthDate());
        if(userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if(userDTO.getPhone() != null) user.setPhone(userDTO.getPhone());
        if(userDTO.getPassword() != null) user.setPassword(userDTO.getPassword());

        if(userDTO.getAddress() != null) user.setAddress(dtoToAddress(userDTO.getAddress()));

        return user;
    }


    public static UserDTO userToDto(User user) {
        UserDTO userDTO = new UserDTO();

        if(user.getId() != null) userDTO.setId(user.getId());
        if(user.getName() != null) userDTO.setName(user.getName());
        if(user.getCpf() != null) userDTO.setCpf(user.getCpf());
        if(user.getBirthDate() != null) userDTO.setBirthDate(user.getBirthDate());
        if(user.getEmail() != null) userDTO.setEmail(user.getEmail());
        if(user.getPhone() != null) userDTO.setPhone(user.getPhone());
        if(user.getAddress() != null) userDTO.setAddress(addressToDto(user.getAddress()));

        return userDTO;
    }


    public static Address dtoToAddress(AddressDTO addressDTO) {
        Address address = new Address();

        if(addressDTO.getZipCode() != null) address.setZipCode(addressDTO.getZipCode());
        if(addressDTO.getNumber() != null) address.setNumber(addressDTO.getNumber());
        if(addressDTO.getPublicPlace() != null) address.setPublicPlace(addressDTO.getPublicPlace());
        if(addressDTO.getCity() != null) address.setCity(addressDTO.getCity());
        if(addressDTO.getState() != null) address.setState(addressDTO.getState());
        if(addressDTO.getCountry() != null) address.setCountry(addressDTO.getCountry());
        if(addressDTO.getComplement() != null) address.setComplement(addressDTO.getComplement());

        return address;
    }


    public static AddressDTO addressToDto(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        if(address.getId() != null) addressDTO.setId(address.getId());
        if(address.getZipCode() != null) addressDTO.setZipCode(address.getZipCode());
        if(address.getNumber() != null) addressDTO.setNumber(address.getNumber());
        if(address.getPublicPlace() != null) addressDTO.setPublicPlace(address.getPublicPlace());
        if(address.getCity() != null) addressDTO.setCity(address.getCity());
        if(address.getState() != null) addressDTO.setState(address.getState());
        if(address.getCountry() != null) addressDTO.setCountry(address.getCountry());
        if(address.getComplement() != null) addressDTO.setComplement(address.getComplement());

        return addressDTO;
    }

	public static DebitCard dtoToDebitCard(CardDTO cardDto) {
		DebitCard debitCard = new DebitCard();
        if(cardDto.getUserName() != null) debitCard.setUserName(cardDto.getUserName());
        if(cardDto.getNumber() != null) debitCard.setCardNumber(cardDto.getNumber());
        if(cardDto.getSecurityCode() != null) debitCard.setSecurityCode(cardDto.getSecurityCode());
        if(cardDto.getIsBlocked() != null) debitCard.setIsBlocked(cardDto.getIsBlocked());
        if(cardDto.getPassword() != null) debitCard.setCardPassword(cardDto.getPassword());
        if(cardDto.getFlag() != null) debitCard.setCardFlag(cardDto.getFlag());
        if(cardDto.getDailyLimit() != null) debitCard.setDailyLimit(cardDto.getDailyLimit());
        if(cardDto.getExpirationDate() != null) debitCard.setExpirationDate(cardDto.getExpirationDate());

		return debitCard;
	}
	
	public static CreditCard dtoToCreditCard(CardDTO cardDto) {
		CreditCard creditCard = new CreditCard();
        if(cardDto.getUserName() != null) creditCard.setUserName(cardDto.getUserName());
        if(cardDto.getNumber() != null) creditCard.setCardNumber(cardDto.getNumber());
        if(cardDto.getSecurityCode() != null) creditCard.setSecurityCode(cardDto.getSecurityCode());
        if(cardDto.getIsBlocked() != null) creditCard.setIsBlocked(cardDto.getIsBlocked());
        if(cardDto.getPassword() != null) creditCard.setCardPassword(cardDto.getPassword());
        if(cardDto.getFlag() != null) creditCard.setCardFlag(cardDto.getFlag());
        if(cardDto.getMaxLimit() != null) creditCard.setMaxLimit(cardDto.getMaxLimit());
        if(cardDto.getCurrentLimit() != null) creditCard.setCurrentLimit(cardDto.getCurrentLimit());
        if(cardDto.getAvailableLimit() != null) creditCard.setAvailableLimit(cardDto.getAvailableLimit());
        if(cardDto.getUsageFee() != null) creditCard.setUsageFee(cardDto.getUsageFee());
        if(cardDto.getCreditCardType() != null) creditCard.setCreditCardType(cardDto.getCreditCardType());
        if(cardDto.getExpirationDate() != null) creditCard.setExpirationDate(cardDto.getExpirationDate());

		return creditCard;
	}


	public static CardDTO debitCardToDto(DebitCard card) {
		CardDTO debitCardDTO = new CardDTO();
        if(card.getId() != null) debitCardDTO.setId(card.getId());
        if(card.getUserName() != null) debitCardDTO.setUserName(card.getUserName());
        if(card.getCardNumber() != null) debitCardDTO.setNumber(card.getCardNumber());
        if(card.getSecurityCode() != null) debitCardDTO.setSecurityCode(card.getSecurityCode());
        if(card.getIsBlocked() != null) debitCardDTO.setIsBlocked(card.getIsBlocked());
        if(card.getCardPassword() != null) debitCardDTO.setPassword(card.getCardPassword());
        if(card.getCardFlag() != null) debitCardDTO.setFlag(card.getCardFlag());
        if(card.getDailyLimit() != null) debitCardDTO.setDailyLimit(card.getDailyLimit());
        if(card.getExpirationDate() != null) debitCardDTO.setExpirationDate(card.getExpirationDate());

		return debitCardDTO;
	}


	public static CardDTO creditCardToDto(CreditCard card) {
		CardDTO creditCardDTO = new CardDTO();
        if(card.getId() != null) creditCardDTO.setId(card.getId());
        if(card.getUserName() != null) creditCardDTO.setUserName(card.getUserName());
        if(card.getCardNumber() != null) creditCardDTO.setNumber(card.getCardNumber());
        if(card.getSecurityCode() != null) creditCardDTO.setSecurityCode(card.getSecurityCode());
        if(card.getIsBlocked() != null) creditCardDTO.setIsBlocked(card.getIsBlocked());
        if(card.getCardPassword() != null) creditCardDTO.setPassword(card.getCardPassword());
        if(card.getCardFlag() != null) creditCardDTO.setFlag(card.getCardFlag());
        if(card.getMaxLimit() != null) creditCardDTO.setMaxLimit(card.getMaxLimit());
        if(card.getCurrentLimit() != null) creditCardDTO.setCurrentLimit(card.getCurrentLimit());
        if(card.getAvailableLimit() != null) creditCardDTO.setAvailableLimit(card.getAvailableLimit());
        if(card.getUsageFee() != null) creditCardDTO.setUsageFee(card.getUsageFee());
        if(card.getCreditCardType() != null) creditCardDTO.setCreditCardType(card.getCreditCardType());
        if(card.getExpirationDate() != null) creditCardDTO.setExpirationDate(card.getExpirationDate());
        return creditCardDTO;
    }
    
    public static PixKeysDTO pixToDto(Pix pix) {
    	PixKeysDTO pixKeysDTO = new PixKeysDTO();
    	if(pix.getId() != null) pixKeysDTO.setId(pix.getId());
    	if(pix.getPixKeyValue() != null) pixKeysDTO.setPixKeyValue(pix.getPixKeyValue());
    	if(pix.getPixType() != null) pixKeysDTO.setPixType(pix.getPixType());

    	return pixKeysDTO;
    }
    
    public static PurchaseDTO purchaseToDTO(Purchase purchase){
        PurchaseDTO purchaseDto = new PurchaseDTO();

        if(purchase.getId() != null) purchaseDto.setId(purchase.getId());
        if(purchase.getValue() != null) purchaseDto.setValue(purchase.getValue());
        if(purchase.getDate() != null) purchaseDto.setDate(purchase.getDate());
        if(purchase.getStore() != null) purchaseDto.setStore(purchase.getStore());

        return purchaseDto;
    }

    public static CreditCardBillDTO creditCardBillToDto(CreditCardBill creditCardBill){
        CreditCardBillDTO creditCardBillDto = new CreditCardBillDTO();

        if(creditCardBill.getId() != null) creditCardBillDto.setId(creditCardBill.getId());
        if(creditCardBill.getValue() != null) creditCardBillDto.setValue(creditCardBill.getValue());
        if(creditCardBill.getDueDate() != null) creditCardBillDto.setDueDate(creditCardBill.getDueDate());
        if(creditCardBill.getIsPaid() != null) creditCardBillDto.setIsPaid(creditCardBill.getIsPaid());
        if(creditCardBill.getIsOverdue() != null) creditCardBillDto.setIsOverdue(creditCardBill.getIsOverdue());

        if(creditCardBill.getPurchases() != null) {
            creditCardBillDto.setPurchase(creditCardBill.getPurchases().stream().map(purchase -> {
                return purchaseToDto(purchase);
            }).toList());
        }
        return creditCardBillDto;
    }

    public static AccountDTO fourBankAccountToDTO(FourBankAccount fourBankAccount) {
    	AccountDTO accountDTO = new AccountDTO();

        if(fourBankAccount.getId() != null) accountDTO.setId(fourBankAccount.getId());
        if(fourBankAccount.getAgency() != null) accountDTO.setAgency(fourBankAccount.getAgency());
        if(fourBankAccount.getNumber() != null) accountDTO.setNumber(fourBankAccount.getNumber());
        if(fourBankAccount.getBankNumber() != null) accountDTO.setBankNumber(fourBankAccount.getBankNumber());
        if(fourBankAccount.getBalance() != null) accountDTO.setBalance(fourBankAccount.getBalance());
        if(fourBankAccount.getUserName() != null) accountDTO.setUserName(fourBankAccount.getUserName());
        if(fourBankAccount.getCpf() != null) accountDTO.setCpf(fourBankAccount.getCpf());

        return accountDTO;
    }
    
    public static AccountDTO accountToDTO(Account account) {
    	AccountDTO accountDTO = new AccountDTO();

        if(account.getId() != null) accountDTO.setId(account.getId());
        if(account.getAgency() != null) accountDTO.setAgency(account.getAgency());
        if(account.getNumber() != null) accountDTO.setNumber(account.getNumber());
        if(account.getBankNumber() != null) accountDTO.setBankNumber(account.getBankNumber());
        if(account.getUserName() != null) accountDTO.setUserName(account.getUserName());
        if(account.getCpf() != null) accountDTO.setCpf(account.getCpf());

        return accountDTO;
    }
    
    public static TransactionDTO transactionToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();

        if(transaction.getId() != null) transactionDTO.setId(transaction.getId());
        if(transaction.getValue() != null) transactionDTO.setValue(transaction.getValue());
        if(transaction.getTransactionDate() != null) transactionDTO.setTransactionDate(transaction.getTransactionDate());
        if(transaction.getAppointmentDate() != null) transactionDTO.setAppointmentDate(transaction.getAppointmentDate());
        if(transaction.getTransactionType() != null) transactionDTO.setTransactionType(transaction.getTransactionType());
        if(transaction.getSourceAccount() != null) transactionDTO.setSourceAccount(fourBankAccountToDTO(transaction.getSourceAccount()));
        if(transaction.getDestinationAccount() != null) transactionDTO.setDestinationAccount(accountToDTO(transaction.getDestinationAccount()));

        return transactionDTO;
    }

    public static InsurancePolicyDTO insurancePolicyToDto(InsurancePolicy insurancePolicy) {
        InsurancePolicyDTO insurancePolicyDTO = new InsurancePolicyDTO();

        if(insurancePolicy.getId() != null) insurancePolicyDTO.setId(insurancePolicy.getId());
        if(insurancePolicy.getNumber() != null) insurancePolicyDTO.setNumber(insurancePolicy.getNumber());
        if(insurancePolicy.getHiringDate() != null) insurancePolicyDTO.setHiringDate(insurancePolicy.getHiringDate());
        if(insurancePolicy.getValue() != null) insurancePolicyDTO.setValue(insurancePolicy.getValue());
        if(insurancePolicy.getRoles() != null) insurancePolicyDTO.setRoles(insurancePolicy.getRoles());

        return insurancePolicyDTO;
    }

    public static InsurancePolicy dtoToInsurancePolicy(InsurancePolicyDTO insurancePolicyDTO) {
        InsurancePolicy insurancePolicy = new InsurancePolicy();

        if(insurancePolicyDTO.getNumber() != null) insurancePolicy.setNumber(insurancePolicyDTO.getNumber());
        if(insurancePolicyDTO.getHiringDate() != null) insurancePolicy.setHiringDate(insurancePolicyDTO.getHiringDate());
        if(insurancePolicyDTO.getValue() != null) insurancePolicy.setValue(insurancePolicyDTO.getValue());
        if(insurancePolicyDTO.getRoles() != null) insurancePolicy.setRoles(insurancePolicyDTO.getRoles());

        return insurancePolicy;
    }
    
    public static PurchaseDTO purchaseToDto(Purchase purchase){
    	PurchaseDTO purchaseDto = new PurchaseDTO();

        if(purchase.getId()!=null) purchaseDto.setId(purchase.getId());
        if(purchase.getValue() != null) purchaseDto.setValue(purchase.getValue());
        if (purchase.getDate()!=null) purchaseDto.setDate(purchase.getDate());
        if(purchase.getStore()!=null) purchaseDto.setStore(purchase.getStore());

        return purchaseDto;
    }
    
}
    

