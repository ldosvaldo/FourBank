package com.foursys.fourbank.repository;


import com.foursys.fourbank.model.CreditCard;
import com.foursys.fourbank.model.FourBankAccount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

	List<CreditCard> findAllByAccount(FourBankAccount account);
}
