package com.foursys.fourbank.repository;


import com.foursys.fourbank.model.Purchase;
import com.foursys.fourbank.model.CreditCardBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByCreditCardBill(CreditCardBill creditCardBill);
}
