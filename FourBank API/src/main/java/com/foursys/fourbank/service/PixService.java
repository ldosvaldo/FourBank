package com.foursys.fourbank.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foursys.fourbank.dto.PixDTO;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.model.Pix;
import com.foursys.fourbank.model.SavingsAccount;
import com.foursys.fourbank.model.TransactionalAccount;
import com.foursys.fourbank.repository.PixRepository;
import com.foursys.fourbank.repository.SavingsAccountRepository;
import com.foursys.fourbank.repository.TransactionalAccountRepository;
import org.springframework.http.ResponseEntity;

@Service
public class PixService {

    @Autowired
    private PixRepository pixRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionalAccountRepository transactionalAccountRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PixDTO savePix(PixDTO pixDTO, Long accountId) {

    	Optional<SavingsAccount> savingAccount = savingsAccountRepository.findById(accountId);
    	Optional<TransactionalAccount> transactionalAccount;

    	Pix pix = new Pix();
    	pix.setPixType(pixDTO.getPixType());
    	pix.setPixKeyValue(pixDTO.getPixKeyValue());

    	if (savingAccount.isPresent()) {

    		pix.setAccount(savingAccount.get());

    		pix = pixRepository.save(pix);

    		PixDTO pixDTOResponse = new PixDTO();

    		pixDTOResponse.setPixType(pix.getPixType());
    		pixDTOResponse.setPixKeyValue(pix.getPixKeyValue());
    		pixDTOResponse.setId(pix.getId());

    		return pixDTOResponse;

    	} else if (transactionalAccountRepository.findById(accountId).isPresent()){

    		transactionalAccount = transactionalAccountRepository.findById(accountId);

    		pix.setAccount(transactionalAccount.get());

    		pixRepository.save(pix);

    		PixDTO pixDTOResponse = new PixDTO();

    		pixDTOResponse.setPixType(pix.getPixType());
    		pixDTOResponse.setPixKeyValue(pix.getPixKeyValue());
    		pixDTOResponse.setId(pix.getId());

    		return pixDTOResponse;

    	} else {

        	throw new EntityNotFoundException("There is no account with the given id");

    	}

    }

    public ResponseEntity<?> remove(Long pixId) {
        Optional<Pix> optional = pixRepository.findById(pixId);
        if (optional.isPresent()) {
            pixRepository.deleteById(pixId);
            return ResponseEntity.ok().build();
        }else {
            throw new EntityNotFoundException("Pix not found with this ID ");
        }
    }
}
