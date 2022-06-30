package com.foursys.fourbank.service;

import com.foursys.fourbank.enums.CardFlag;
import com.foursys.fourbank.enums.CreditCardType;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.exception.InvalidValueException;
import com.foursys.fourbank.model.CreditCard;
import com.foursys.fourbank.model.DebitCard;
import com.foursys.fourbank.model.FourBankAccount;
import com.foursys.fourbank.model.SavingsAccount;
import com.foursys.fourbank.model.TransactionalAccount;
import com.foursys.fourbank.repository.CreditCardRepository;
import com.foursys.fourbank.repository.DebitCardRepository;
import com.foursys.fourbank.repository.SavingsAccountRepository;
import com.foursys.fourbank.repository.TransactionalAccountRepository;
import com.foursys.fourbank.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.dto.CardDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;


@Service
public class CardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private DebitCardRepository debitCardRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionalAccountRepository transactionalAccountRepository;

    //==================================================== save:
    public CardDTO saveCreditCard(CardDTO cardDto, Long accountId) {
        CreditCard creditCard = new CreditCard();
        Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository.findById(accountId);
        Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository.findById(accountId);
        
        if (optionalSavingsAccount.isPresent()) {
            creditCard.setAccount(optionalSavingsAccount.get());
            creditCard.setUserName(optionalSavingsAccount.get().getUser().getName());
        } else if (optionalTransactionalAccount.isPresent()) {
            creditCard.setAccount(optionalTransactionalAccount.get());
            creditCard.setUserName(optionalTransactionalAccount.get().getUser().getName());
        } else throw new EntityNotFoundException("There is no account with the given id");
        
        creditCard.setCardNumber(generateCardNumbers());
        creditCard.setExpirationDate(expirationDateCard());
        creditCard.setSecurityCode(generateSecurityCode());
        creditCard.setIsBlocked(true);
        creditCard.setCardPassword(createPassword());
        creditCard.setMaxLimit(2000.00);
        creditCard.setCurrentLimit(creditCard.getMaxLimit());
        creditCard.setAvailableLimit(creditCard.getCurrentLimit());
        creditCard.setUsageFee(10.0);
        
        if(cardDto.getCreditCardType() != null) {
            creditCard.setCreditCardType(cardDto.getCreditCardType());
        } else {
            creditCard.setCreditCardType(CreditCardType.COMMON);
        }
        
        if(cardDto.getFlag() != null) {
            creditCard.setCardFlag(cardDto.getFlag());
        } else {
            creditCard.setCardFlag(CardFlag.VISA);
        }

        creditCardRepository.save(creditCard);
        cardDto = Converter.creditCardToDto(creditCard);

        return cardDto;
    }

    public CardDTO saveDebitCard(CardDTO cardDto, Long accountId) {
        DebitCard debitCard = new DebitCard();
        Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository.findById(accountId);
        Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository.findById(accountId);
        if (optionalSavingsAccount.isPresent()) {
            debitCard.setAccount(optionalSavingsAccount.get());
            debitCard.setUserName(optionalSavingsAccount.get().getUser().getName());
        } else if (optionalTransactionalAccount.isPresent()) {
            debitCard.setAccount(optionalTransactionalAccount.get());
            debitCard.setUserName(optionalTransactionalAccount.get().getUser().getName());
        } else throw new EntityNotFoundException("There is no account with the given id");
        
        debitCard.setCardNumber(generateCardNumbers());
        debitCard.setExpirationDate(expirationDateCard());
        debitCard.setSecurityCode(generateSecurityCode());
        debitCard.setIsBlocked(true);
        debitCard.setCardPassword(createPassword());
        debitCard.setDailyLimit(1000.0);
        
        if(cardDto.getFlag() != null) {
        	debitCard.setCardFlag(cardDto.getFlag());
        } else {
        	debitCard.setCardFlag(CardFlag.VISA);
        }

        debitCardRepository.save(debitCard);
        cardDto = Converter.debitCardToDto(debitCard);

        return cardDto;
    }

    public String generateCardNumbers() {
        Random random = new Random();
        Integer num1 = random.nextInt(1111, 9999);
        Integer num2 = random.nextInt(1111, 9998);
        Integer num3 = random.nextInt(1111, 9997);
        Integer num4 = random.nextInt(1111, 9996);

        String number = Integer.toString(num1) + " " + Integer.toString(num2) + " " + Integer.toString(num3) + " " + Integer.toString(num4);

        return number;
    }

    public LocalDate expirationDateCard() {
        LocalDate expirationDate = LocalDate.now();
        expirationDate = expirationDate.plusYears(5);

        return expirationDate;
    }

    public String generateSecurityCode() {
        Random random = new Random();
        Integer securityNumber = random.nextInt(100, 999);

        String securityCode = Integer.toString(securityNumber);

        return securityCode;
    }

    public String createPassword() {
        Random random = new Random();
        Integer passwordCreate = random.nextInt(1000, 9999);

        String password = Integer.toString(passwordCreate);

        return password;
    }
    
    //==================================================== update:
    
	public CardDTO update(CardDTO cardDto, Long cardId) {
		//Esse método atualiza tanto atributos de cartão de crédito quanto cartão de débito
		Optional<DebitCard> optionalDebitCard = debitCardRepository.findById(cardId);
		Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(cardId);
		
		if(optionalDebitCard.isPresent()) {
			return updateDebitCard(optionalDebitCard, cardDto);
		} else if (optionalCreditCard.isPresent()) {
			return updateCreditCard(optionalCreditCard, cardDto);
		} else {
			throw new EntityNotFoundException("Card not found");
		}
		
	
	}

	private CardDTO updateCreditCard(Optional<CreditCard> optionalCreditCard, CardDTO cardDto) {
		CreditCard updatedCard = Converter.dtoToCreditCard(cardDto);
		CreditCard currentCard = optionalCreditCard.get();
		
    	if(updatedCard.getIsBlocked() != null) {
		currentCard.setIsBlocked(updatedCard.getIsBlocked());
    	}
		if(updatedCard.getCardPassword() != null) {
			currentCard.setCardPassword(updatedCard.getCardPassword());
	    }
		if(updatedCard.getCardFlag() != null) {
			currentCard.setCardFlag(updatedCard.getCardFlag());
	    }
		if(updatedCard.getMaxLimit() != null) {
			currentCard.setMaxLimit(updatedCard.getMaxLimit());
	    }
		if(updatedCard.getCurrentLimit() != null) {
			if(updatedCard.getCurrentLimit() > currentCard.getMaxLimit()) {
				throw new InvalidValueException("currentLimit cannot be bigger than maxLimit");
			}
			//se o valor do limite disponível for maior do que o novo limite atual, o limite disponível será igual o limite atual
			if(currentCard.getAvailableLimit() > updatedCard.getCurrentLimit()) {
				currentCard.setAvailableLimit(updatedCard.getCurrentLimit());
			}
			
			currentCard.setCurrentLimit(updatedCard.getCurrentLimit());
	    }
		if(updatedCard.getAvailableLimit() != null) {
			if(updatedCard.getAvailableLimit() > currentCard.getCurrentLimit()) {
				throw new InvalidValueException("availableLimit cannot be bigger than currentLimit");
			}
			
			currentCard.setAvailableLimit(updatedCard.getAvailableLimit());
	
	    }
		if(updatedCard.getUsageFee() != null) {
			currentCard.setUsageFee(updatedCard.getUsageFee());
	    }
		if(updatedCard.getCreditCardType() != null) {
			currentCard.setCreditCardType(updatedCard.getCreditCardType());
	    }
		
		creditCardRepository.save(currentCard);
    	cardDto = Converter.creditCardToDto(currentCard);

		return cardDto;
	}

	private CardDTO updateDebitCard(Optional<DebitCard> optionalDebitCard, CardDTO cardDto) {
		DebitCard updatedCard = Converter.dtoToDebitCard(cardDto);
		DebitCard currentCard = optionalDebitCard.get();
		
		if(updatedCard.getIsBlocked() != null) {
			currentCard.setIsBlocked(updatedCard.getIsBlocked());
	    }
		if(updatedCard.getCardPassword() != null) {
			currentCard.setCardPassword(updatedCard.getCardPassword());
	    }
		if(updatedCard.getCardFlag() != null) {
			currentCard.setCardFlag(updatedCard.getCardFlag());
	    }
		if(updatedCard.getDailyLimit() != null) {
			currentCard.setDailyLimit(updatedCard.getDailyLimit());
	    }
		
		debitCardRepository.save(currentCard);
    	cardDto = Converter.debitCardToDto(currentCard);

		return cardDto;

	}
	
	public CardDTO getCardById(Long cardId) {
		Optional<DebitCard> optionalDebitCard = debitCardRepository.findById(cardId);
		Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(cardId);
		
		if(optionalDebitCard.isPresent()) {
			return Converter.debitCardToDto(optionalDebitCard.get());	
		} else if (optionalCreditCard.isPresent()) {
			return Converter.creditCardToDto(optionalCreditCard.get());
		} else {
			throw new EntityNotFoundException("Card not found");
		}
	}

	public List<CardDTO> getCardsByAccount(Long accountId) {		
		FourBankAccount fourbankAccount;
		Optional<TransactionalAccount> transactionalAccountOptional = transactionalAccountRepository.findById(accountId);
		Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(accountId);
		
		if(transactionalAccountOptional.isPresent()) {
			fourbankAccount = transactionalAccountOptional.get();
		} else if (savingsAccountOptional.isPresent()) {
			fourbankAccount = savingsAccountOptional.get();
		} else {
            throw new EntityNotFoundException("no bank account found");
		}
		
		
		List<DebitCard> debitCardList = debitCardRepository.findAllByAccount(fourbankAccount);
		List<CreditCard> creditCardList = creditCardRepository.findAllByAccount(fourbankAccount);
		
		
		
		Stream debitCardDtoStream = debitCardList.stream().map(card -> {
			return Converter.debitCardToDto(card);
		});
		
		
		Stream creditCardDtoStream = creditCardList.stream().map(card -> {
			return Converter.creditCardToDto(card);
		});
		
		List<CardDTO> cardDtoList = Stream.concat(debitCardDtoStream, creditCardDtoStream).toList();
		
		return cardDtoList;
		
		
	}
}
