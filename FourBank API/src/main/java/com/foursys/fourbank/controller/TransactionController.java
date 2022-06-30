package com.foursys.fourbank.controller;

import com.foursys.fourbank.dto.PixChargeDTO;
import com.foursys.fourbank.dto.PixChargeFORM;
import com.foursys.fourbank.dto.PixTransactionDTO;
import com.foursys.fourbank.dto.PurchaseDTO;
import com.foursys.fourbank.dto.TransactionDTO;
import com.foursys.fourbank.service.TransactionService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    

    @PostMapping("/credit/{idCreditCard}")
    public ResponseEntity<PurchaseDTO> buyWithCreditCard(@PathVariable Long idCreditCard , @RequestBody @Valid PurchaseDTO purchaseDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.save(idCreditCard,purchaseDto));
    }

    @PostMapping("/pix/copyAndPast/{userId}")
    public ResponseEntity<PixChargeDTO> pixCharge(@PathVariable Long userId, @RequestBody PixChargeFORM pixChargeFORM) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.pixCharge(userId, pixChargeFORM));
    }

    @GetMapping("/pix/copyAndPast/{accountId}")
    public ResponseEntity<PixChargeFORM> getPixCharge(@PathVariable Long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getPixCharge(accountId));
    }

    @PostMapping("/pix")
    public ResponseEntity<TransactionDTO> savePix(@RequestBody PixTransactionDTO pixTransactionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.savePix(pixTransactionDTO));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> saveTransfer(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.saveTransfer(transactionDTO));
    }

    @GetMapping("/pix/{transactionId}")
    public TransactionDTO getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @PostMapping("/accountDebit")
    public ResponseEntity<?> paymentDebitAccount(@RequestBody @Valid TransactionDTO transactionDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.savePaymentDebit(transactionDTO));
    }

}
