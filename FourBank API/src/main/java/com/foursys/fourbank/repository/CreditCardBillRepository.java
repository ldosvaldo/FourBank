package com.foursys.fourbank.repository;


import com.foursys.fourbank.model.CreditCard;
import com.foursys.fourbank.model.CreditCardBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CreditCardBillRepository extends JpaRepository<CreditCardBill, Long> {

    List<CreditCardBill> findCreditCardBillsByCreditCard(CreditCard creditCard);
}
