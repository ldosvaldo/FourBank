package com.foursys.fourbank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.foursys.fourbank.exception.UnreportedEssentialFieldException;
import com.foursys.fourbank.model.CreditCard;
import com.foursys.fourbank.model.User;
import com.foursys.fourbank.util.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foursys.fourbank.dto.InsurancePolicyDTO;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.model.InsurancePolicy;
import com.foursys.fourbank.repository.CreditCardRepository;
import com.foursys.fourbank.repository.InsurancePolicyRepository;

@Service
public class InsurancePolicyService {

	@Autowired
	private InsurancePolicyRepository insurancePolicyRepository;

	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public List<InsurancePolicyDTO> getAllInsurancePolicy(Long id) {

		if (creditCardRepository.findById(id).isEmpty()) {

			throw new EntityNotFoundException("there is no card with this id");

		} else {

		    List<InsurancePolicy> insurancePolicy = insurancePolicyRepository.findInsurancePolicyByCreditCard(creditCardRepository.findById(id).get());
		    
		    return insurancePolicy.stream().map(this::toInsurancePolicyDTO).collect(Collectors.toList());
		    
		}
	}
	
	public InsurancePolicyDTO getPolicyById(Long id) {
		return insurancePolicyRepository.findById(id)
				.map(InsurancePolicy -> modelMapper.map(InsurancePolicy, InsurancePolicyDTO.class))
				.orElseThrow(() -> new EntityNotFoundException("there is no policy key with this id"));
	}
	
	public InsurancePolicyDTO toInsurancePolicyDTO(InsurancePolicy insurancePolicy) {
		return modelMapper.map(insurancePolicy, InsurancePolicyDTO.class );
	}

    public ResponseEntity<InsurancePolicyDTO> postPolicyById(Long cardId, InsurancePolicyDTO insurancePolicyDTO) {
		Optional<CreditCard> creditCard = creditCardRepository.findById(cardId);
		if(creditCard.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		if(insurancePolicyDTO.getNumber() == null) throw new UnreportedEssentialFieldException("number attribute not informed");
		if(insurancePolicyDTO.getHiringDate() == null) throw new UnreportedEssentialFieldException("hiringDate attribute not informed");
		if(insurancePolicyDTO.getValue() == null) throw new UnreportedEssentialFieldException("value attribute not informed");
		if(insurancePolicyDTO.getRoles() == null) throw new UnreportedEssentialFieldException("roles attribute not informed");
		InsurancePolicy insurancePolicy = Converter.dtoToInsurancePolicy(insurancePolicyDTO);
		insurancePolicy.setCreditCard(creditCard.get());
		insurancePolicy.setId(null);
		insurancePolicy = insurancePolicyRepository.save(insurancePolicy);
		return ResponseEntity.status(HttpStatus.CREATED).body(Converter.insurancePolicyToDto(insurancePolicy));
    }
}









