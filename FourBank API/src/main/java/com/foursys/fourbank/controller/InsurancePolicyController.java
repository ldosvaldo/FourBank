package com.foursys.fourbank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.foursys.fourbank.dto.InsurancePolicyDTO;
import com.foursys.fourbank.service.InsurancePolicyService;

@RestController
@RequestMapping("/policy")
public class InsurancePolicyController {
	
	@Autowired
	private InsurancePolicyService insurancePolicyService;
	
	
	@GetMapping("/{cardId}")
	public ResponseEntity<List<InsurancePolicyDTO>> getAllInsurancePolicy(@PathVariable Long cardId) {
		return ResponseEntity.ok().body(insurancePolicyService.getAllInsurancePolicy(cardId));
	}
	
	@GetMapping("/byId/{id}")
	public ResponseEntity<InsurancePolicyDTO> getPolicyById(@PathVariable Long id) {
		return ResponseEntity.ok().body(insurancePolicyService.getPolicyById(id));
	}

	@PostMapping("/{cardId}")
	@Transactional
	public ResponseEntity<InsurancePolicyDTO> postPolicyByCardId(@PathVariable Long cardId, @RequestBody InsurancePolicyDTO insurancePolicyDTO) {
		return insurancePolicyService.postPolicyById(cardId, insurancePolicyDTO);
	}
}
