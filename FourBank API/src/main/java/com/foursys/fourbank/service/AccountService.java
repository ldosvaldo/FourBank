package com.foursys.fourbank.service;

import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.dto.NotificationDTO;
import com.foursys.fourbank.dto.AccountDTO;
import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import com.foursys.fourbank.dto.PixKeysDTO;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.model.InsurancePolicy;
import com.foursys.fourbank.model.Notification;
import com.foursys.fourbank.model.SavingsAccount;
import com.foursys.fourbank.model.TransactionalAccount;
import com.foursys.fourbank.repository.InsurancePolicyRepository;
import com.foursys.fourbank.repository.NotificationRepository;
import com.foursys.fourbank.repository.OtherAccountRepository;
import com.foursys.fourbank.repository.PixRepository;
import com.foursys.fourbank.repository.SavingsAccountRepository;
import com.foursys.fourbank.repository.TransactionalAccountRepository;
import com.foursys.fourbank.repository.UserRepository;
import com.foursys.fourbank.util.Converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.dto.NotificationDTO;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.model.Notification;
import com.foursys.fourbank.model.SavingsAccount;
import com.foursys.fourbank.model.TransactionalAccount;
import com.foursys.fourbank.repository.NotificationRepository;
import com.foursys.fourbank.repository.OtherAccountRepository;
import com.foursys.fourbank.repository.SavingsAccountRepository;
import com.foursys.fourbank.repository.TransactionalAccountRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtherAccountRepository otherAccountRepository;
    @Autowired
    private SavingsAccountRepository savingsAccountRepository;
    @Autowired
    private TransactionalAccountRepository transactionalAccountRepository;
    
    @Autowired
	private NotificationRepository notificationRepository;

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;
    private PixRepository pixRepository;
    
	@Autowired
	private ModelMapper modelMapper;

    public AccountDTO getById(Long accountId){
        Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository.findById(accountId);
        if (optionalSavingsAccount.isPresent()) {
            return new AccountDTO(optionalSavingsAccount.get());
        }
        
        Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository.findById(accountId);
        if (optionalTransactionalAccount.isPresent()) {
            return new AccountDTO(optionalTransactionalAccount.get());
        }

        throw new EntityNotFoundException("could not find an account with this id");

    }

    public List<InsurancePolicy> getInsurance(Long id) {
        InsurancePolicy insurancePolicy = insurancePolicyRepository.findById(id).get();
        insurancePolicy.setCreditCard(null);
        return List.of(insurancePolicy);
    }

	public ArrayList<AccountDTO> getAccountsByUser(Long userId) {
		ArrayList<AccountDTO> accountList = new ArrayList<AccountDTO>();
		Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository.findByUserId(userId);
        Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository.findByUserId(userId);

        if ((!optionalSavingsAccount.isPresent()) && (!optionalTransactionalAccount.isPresent())) {
            throw new EntityNotFoundException("no bank account found");
        }

        if(optionalTransactionalAccount.isPresent()) {
        	AccountDTO transactionalAccountDTO = new AccountDTO(optionalTransactionalAccount.get());
        	accountList.add(transactionalAccountDTO);
        }

        if(optionalSavingsAccount.isPresent()) {
        	AccountDTO savingsAccountDTO = new AccountDTO(optionalSavingsAccount.get());
        	accountList.add(savingsAccountDTO);
        }

		return accountList;
	}
	
	public NotificationDTO showNotification(Long accountId) {
		
        Optional<SavingsAccount> savingsAccount = savingsAccountRepository.findById(accountId);
        Optional<TransactionalAccount> transactionalAccount = transactionalAccountRepository.findById(accountId);
        
        if (savingsAccount.isPresent()) {
            
            return notificationRepository.findById(accountId).
            		map(notification -> modelMapper.map(notification, NotificationDTO.class))
    				.orElseThrow(() -> new EntityNotFoundException("there is no notification with this id"));
            
        } else if (transactionalAccount.isPresent()) {
            
        	 return notificationRepository.findById(accountId).
             		map(notification -> modelMapper.map(notification, NotificationDTO.class))
     				.orElseThrow(() -> new EntityNotFoundException("there is no notification with this id"));
        }

         throw new EntityNotFoundException("there is no notification with this id");
    }
	
	public NotificationDTO toNotificationDTO(Notification notification) {
		return modelMapper.map(notification, NotificationDTO.class);
	}


    public List<PixKeysDTO> showAllPixKeys(Long id) {
        Optional<TransactionalAccount> transactionalAccountOpt = transactionalAccountRepository.findById(id);
        Optional<SavingsAccount> savingsAccountOpt = savingsAccountRepository.findById(id);

        if(transactionalAccountOpt.isPresent()) {
            return transactionalAccountOpt.get().getPixList().stream().map(pix -> {
            	return Converter.pixToDto(pix);
            }).toList();
        } else if(savingsAccountOpt.isPresent()) {
            return savingsAccountOpt.get().getPixList().stream().map(pix -> {
            	return Converter.pixToDto(pix);
            }).toList();
        } else {
            throw new EntityNotFoundException("there is no account with the given id");
        }
    }

    @Transactional
    public AccountDTO createTransactionalAccount(AccountDTO accountDto){

            var transactionalAccount = new TransactionalAccount();
            var notification = new Notification();

            transactionalAccount.setUser(userRepository.findById(accountDto.getUserId()).orElseThrow(()->{
                return new EntityNotFoundException("Does not have user with the given id");
                    }
                )
            );

            accountDto.setAgency("6878");
            accountDto.setNumber(accountNumberGenerator("T"));
            accountDto.setBankNumber("001");
            accountDto.setMaintenanceFee(10.0);
            accountDto.setBalanceLimit(100.0);
            accountDto.setBalance(100.0);
            
            var notificationDto = new NotificationDTO();
            notificationDto.setEmailNotification(false);
            notificationDto.setSmsNotification(false);
            accountDto.setNotification(notificationDto);
            BeanUtils.copyProperties(notificationDto,notification);
            Notification savedNotification = notificationRepository.save(notification);
            transactionalAccount.setNotification(savedNotification);

            BeanUtils.copyProperties(accountDto,transactionalAccount);
            transactionalAccountRepository.save(transactionalAccount);
            BeanUtils.copyProperties(transactionalAccount,accountDto);

        return accountDto;
    }

    @Transactional
    public AccountDTO createSavingAccount(AccountDTO accountDto){
        var savingAccount = new SavingsAccount();
        var notification = new Notification();

        savingAccount.setUser(userRepository.findById(accountDto.getUserId()).orElseThrow(()->{
            return new EntityNotFoundException("Does not have user with the given id");
                }
            )
        );

        accountDto.setAgency("6878");
        accountDto.setNumber(accountNumberGenerator("S"));
        accountDto.setBankNumber("001");
        accountDto.setYieldRate(10.0);
        accountDto.setBalance(100.0);
        
        var notificationDto = new NotificationDTO();
        notificationDto.setEmailNotification(false);
        notificationDto.setSmsNotification(false);
        accountDto.setNotification(notificationDto);
        BeanUtils.copyProperties(notificationDto,notification);
        Notification savedNotification = notificationRepository.save(notification);
        savingAccount.setNotification(savedNotification);


        BeanUtils.copyProperties(accountDto,savingAccount);
        savingsAccountRepository.save(savingAccount);
        BeanUtils.copyProperties(savingAccount,accountDto);

        return accountDto;
    }

    private String accountNumberGenerator(String accountType){
        String retorno = "";
        int number = 0;
        int digit = 0;
        Random random = new Random();
        while(true) {
            if (accountType.equals("T")) {
                number = random.ints(10000,99999).findFirst().getAsInt();
                digit = random.ints(0,9).findFirst().getAsInt();
                retorno = number + "-" + digit;

                if (!transactionalAccountRepository.existsTransactionalAccountByNumber(retorno)){
                    return retorno;
                }
            } else if (accountType.equals("S")) {
                number = random.ints(10000,99999).findFirst().getAsInt();
                digit = random.ints(0,9).findFirst().getAsInt();
                retorno = number + "-" + digit;

                if (!savingsAccountRepository.existsSavingsAccountByNumber(retorno)){
                    return retorno;
                }
            }
        }
    }
    public void deleteAccount(Long id) {

        Optional<SavingsAccount> savingsAccount = savingsAccountRepository.findById(id);
        Optional<TransactionalAccount> transactionalAccount;

        if(savingsAccount.isPresent()) {

            savingsAccountRepository.deleteById(id);

        } else if (transactionalAccountRepository.findById(id).isPresent()) {

            transactionalAccountRepository.deleteById(id);

        } else {

            throw new EntityNotFoundException("There is no account with the given id");
        }
    }
}

