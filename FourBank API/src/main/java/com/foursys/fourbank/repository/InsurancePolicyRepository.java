package com.foursys.fourbank.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.foursys.fourbank.model.CreditCard;
import com.foursys.fourbank.model.InsurancePolicy;

import java.util.List;

import com.foursys.fourbank.model.InsurancePolicy;

@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
	
	List<InsurancePolicy> findInsurancePolicyByCreditCard(CreditCard creditCard);
}

