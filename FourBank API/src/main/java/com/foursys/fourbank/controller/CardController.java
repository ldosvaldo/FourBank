package com.foursys.fourbank.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.dto.CardDTO;
import com.foursys.fourbank.dto.CreditCardBillDTO;
import com.foursys.fourbank.service.CardService;
import com.foursys.fourbank.service.CreditCardBillService;

@RestController
@RequestMapping("/card")

public class CardController {

    @Autowired
    private CardService cardService;
    
    @Autowired
    private CreditCardBillService creditCardBillService;

    @PostMapping("/credit/{accountId}")
    public ResponseEntity<CardDTO> saveCreditCard(@RequestBody CardDTO cardDto, @PathVariable Long accountId){
        return  ResponseEntity.status(HttpStatus.CREATED).body(cardService.saveCreditCard(cardDto, accountId));
    }

    @PostMapping("/debit/{accountId}")
    public ResponseEntity<CardDTO> saveDebitCard(@RequestBody CardDTO cardDto, @PathVariable Long accountId){
        return  ResponseEntity.status(HttpStatus.CREATED).body(cardService.saveDebitCard(cardDto, accountId));
    }
    
    @PutMapping("/{cardId}")
    public ResponseEntity<CardDTO> update(@PathVariable Long cardId, @RequestBody CardDTO cardDto) {
    	return ResponseEntity.status(HttpStatus.OK).body(cardService.update(cardDto, cardId));
    }
    

    @GetMapping("/credit/bill/{billId}")
    public ResponseEntity<CreditCardBillDTO> ccbGetById(@PathVariable Long billId) {
        return ResponseEntity.ok().body(creditCardBillService.ccbGetById(billId));
    }

    @PutMapping("/credit/bill/alt/{billId}")
    public ResponseEntity<CreditCardBillDTO> updateccbGetById(@PathVariable Long billId) {
        return ResponseEntity.ok().body(creditCardBillService.updateccbGetById(billId));
    }
    
    @GetMapping("/{cardId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Long cardId) {
        return ResponseEntity.ok().body(cardService.getCardById(cardId));
    }
    
    @GetMapping("/byAccount/{accountId}")
    public ResponseEntity<List<CardDTO>> getCardsByAccount(@PathVariable Long accountId) {
    	return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardsByAccount(accountId));
    }

    
    
}
