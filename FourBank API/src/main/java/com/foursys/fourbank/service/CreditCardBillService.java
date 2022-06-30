package com.foursys.fourbank.service;

import com.foursys.fourbank.dto.CreditCardBillDTO;
import com.foursys.fourbank.exception.EntityNotFoundException;
import com.foursys.fourbank.model.CreditCardBill;
import com.foursys.fourbank.repository.CreditCardBillRepository;
import com.foursys.fourbank.repository.CreditCardRepository;
import com.foursys.fourbank.repository.PurchaseRepository;
import com.foursys.fourbank.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardBillService {

    @Autowired
    private CreditCardBillRepository creditCardBillRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCardBillDTO ccbGetById(Long billId){ //ccb = CreditCardBill abreviado.
        CreditCardBill creditCardBill = creditCardBillRepository.findById(billId).orElseThrow(() ->{
            return new EntityNotFoundException("There is no bill with the given id");
        });

        creditCardBill.setPurchases(purchaseRepository.findAllByCreditCardBill(creditCardBill));
        return Converter.creditCardBillToDto(creditCardBill);
    }

    public CreditCardBillDTO updateccbGetById(Long billId) {
        Optional<CreditCardBill> creditCardBill = creditCardBillRepository.findById(billId);

        if(creditCardBill.isEmpty()){
            throw new EntityNotFoundException("invoice id does not exist.");
        }

        creditCardBill.get().setIsPaid(true);

        return Converter.creditCardBillToDto(creditCardBillRepository.save(creditCardBill.get()));
    }
}
