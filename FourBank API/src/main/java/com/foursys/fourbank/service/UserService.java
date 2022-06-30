package com.foursys.fourbank.service;

import java.util.Optional;

import com.foursys.fourbank.dto.TransactionDTO;
import com.foursys.fourbank.exception.InvalidValueException;
import com.foursys.fourbank.exception.UnauthorizedOperationException;
import com.foursys.fourbank.exception.UnreportedEssentialFieldException;
import com.foursys.fourbank.model.Address;
import com.foursys.fourbank.model.FourBankAccount;
import com.foursys.fourbank.model.SavingsAccount;
import com.foursys.fourbank.model.TransactionalAccount;
import com.foursys.fourbank.repository.AddressRepository;
import com.foursys.fourbank.dto.UserPasswordDTO;
import com.foursys.fourbank.repository.SavingsAccountRepository;
import com.foursys.fourbank.repository.TransactionalAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.foursys.fourbank.dto.UserDTO;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.model.User;
import com.foursys.fourbank.repository.UserRepository;
import com.foursys.fourbank.util.Converter;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private SavingsAccountRepository savingsAccountRepository;
	@Autowired
	private TransactionalAccountRepository transactionalAccountRepository;

	public UserDTO update(UserDTO userDTO, Long userId) {
    	Optional<User> user = userRepository.findById(userId);    	
    	if(user.isEmpty()) {
    		throw new EntityNotFoundException("User not found");
    	}
    	
    	User updatedUser = Converter.dtoToUser(userDTO);
    	User currentUser = user.get();	
    	
    	if(updatedUser.getName() != null) {
    		currentUser.setName(updatedUser.getName());
    	}
     	if(updatedUser.getCpf() != null) {
    		currentUser.setCpf(updatedUser.getCpf());
    	}    	
    	if(updatedUser.getBirthDate() != null) {
    		currentUser.setBirthDate(updatedUser.getBirthDate());
    	}    	
    	if(updatedUser.getEmail() != null) {
    		currentUser.setEmail(updatedUser.getEmail());
    	}
    	if(updatedUser.getPhone() != null) {
    		currentUser.setPhone(updatedUser.getPhone());
    	}
    	if(updatedUser.getPassword() != null) {
        	currentUser.setPassword(updatedUser.getPassword());
    	}
    	if(updatedUser.getAddress() != null) {
    		if(updatedUser.getAddress().getZipCode() != null) {
            	currentUser.getAddress().setZipCode(updatedUser.getAddress().getZipCode());
        	}
        	if(updatedUser.getAddress().getNumber() != null) {
            	currentUser.getAddress().setNumber(updatedUser.getAddress().getNumber());
        	}
        	if(updatedUser.getAddress().getPublicPlace() != null) {
            	currentUser.getAddress().setPublicPlace(updatedUser.getAddress().getPublicPlace());
        	}
        	if(updatedUser.getAddress().getCity() != null) {
            	currentUser.getAddress().setCity(updatedUser.getAddress().getCity());
        	}
        	if(updatedUser.getAddress().getState() != null) {
            	currentUser.getAddress().setState(updatedUser.getAddress().getState());
        	}
        	if(updatedUser.getAddress().getCountry() != null) {
            	currentUser.getAddress().setCountry(updatedUser.getAddress().getCountry());
        	}
           	if(updatedUser.getAddress().getComplement() != null) {
            	currentUser.getAddress().setComplement(updatedUser.getAddress().getComplement());
        	}
    	}
    	   	
    	userRepository.save(currentUser);
    	
    	userDTO = Converter.userToDto(currentUser);
    	
		return userDTO;	
    }

	public void deleteById(Long id) {
		Optional<TransactionalAccount> optionalTransactionalAccount = transactionalAccountRepository.findByUserId(id);
		Optional<SavingsAccount> optionalSavingsAccount = savingsAccountRepository.findByUserId(id);

		if (optionalSavingsAccount.isPresent() || optionalTransactionalAccount.isPresent()) {
			throw new UnauthorizedOperationException("User has a bank account and cannot be deleted");
		}

		userRepository.delete(userRepository.findById(id).orElseThrow(() -> {
			return new EntityNotFoundException("There is no user with the given id");
		}));
	}

    public UserDTO save(UserDTO userDTO) {
        if(userDTO.getName() == null) throw new UnreportedEssentialFieldException("name attribute not informed");
        if(userDTO.getCpf() == null) throw new UnreportedEssentialFieldException("cpf attribute not informed");
        if(userDTO.getBirthDate() == null) throw new UnreportedEssentialFieldException("birthDate attribute not informed");
        if(userDTO.getEmail() == null) throw new UnreportedEssentialFieldException("email attribute not informed");
        if(userDTO.getPhone() == null) throw new UnreportedEssentialFieldException("phone attribute not informed");
        if(userDTO.getPassword() == null) throw new UnreportedEssentialFieldException("password attribute not informed");

        Address address = new Address();
        if(userDTO.getAddress() != null) {
            if(userDTO.getAddress().getZipCode() == null) throw new UnreportedEssentialFieldException("zipCode attribute not informed");
            if(userDTO.getAddress().getNumber() == null) throw new UnreportedEssentialFieldException("number attribute not informed");
            if(userDTO.getAddress().getPublicPlace() == null) throw new UnreportedEssentialFieldException("publicPlace attribute not informed");
            if(userDTO.getAddress().getCity() == null) throw new UnreportedEssentialFieldException("city attribute not informed");
            if(userDTO.getAddress().getState() == null) throw new UnreportedEssentialFieldException("state attribute not informed");
            if(userDTO.getAddress().getCountry() == null) throw new UnreportedEssentialFieldException("country attribute not informed");
            address = Converter.dtoToAddress(userDTO.getAddress());
            address.setId(null);
        }

        Address addressSaved = addressRepository.save(address);

        User userSaved = Converter.dtoToUser(userDTO);
        userSaved.setId(null);
        userSaved.setAddress(addressSaved);
        userSaved = userRepository.save(userSaved);

        return Converter.userToDto(userSaved);
    }
	
	public UserDTO getUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return Converter.userToDto(user.get());
		}
		throw new EntityNotFoundException("User not found");
	}


	public UserPasswordDTO updatePassword(UserPasswordDTO userPasswordDTO, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new EntityNotFoundException("User not found");
		}

		if(userPasswordDTO.getNewPassword() == null) throw new UnreportedEssentialFieldException("password attribute not informed");

		User currentUser = user.get();
		currentUser.setPassword(userPasswordDTO.getNewPassword());
		userRepository.save(currentUser);

		return userPasswordDTO;

	}
}
