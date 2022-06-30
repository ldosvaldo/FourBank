package com.foursys.fourbank.controller;

import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.service.AccountService;
import java.util.ArrayList;
import com.foursys.fourbank.dto.NotificationDTO;
import com.foursys.fourbank.dto.PixDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.foursys.fourbank.service.PixService;
import com.foursys.fourbank.dto.AccountDTO;
import com.foursys.fourbank.model.InsurancePolicy;
import com.foursys.fourbank.dto.PixKeysDTO;
import com.foursys.fourbank.dto.NotificationDTO;
import com.foursys.fourbank.service.AccountService;
import com.foursys.fourbank.service.NotificationService;

import java.util.ArrayList;
import java.util.List;
import com.foursys.fourbank.service.PixService;
import com.foursys.fourbank.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

//package com.foursys.fourbank.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

    @Autowired
    private PixService pixService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> list(@PathVariable Long accountId){
        return ResponseEntity.ok().body(accountService.getById(accountId));
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<ArrayList<AccountDTO>> getAccountsByUser(@PathVariable Long userId) {
    	return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountsByUser(userId));
    }

    @DeleteMapping("/pix/{pixId}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable("pixId") Long pixId) {
        return ResponseEntity.status(HttpStatus.OK).body(pixService.remove(pixId));
    }
    
    @GetMapping("/notification/{accountId}")
    public ResponseEntity<NotificationDTO> showNotification(@PathVariable Long accountId) {
    	return ResponseEntity.status(HttpStatus.OK).body(accountService.showNotification(accountId));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity deleteAccount(@PathVariable(value = "accountId") Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/pix/{accountId}")
    public ResponseEntity<List<PixKeysDTO>> showAllPixKeys(@PathVariable Long accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.showAllPixKeys(accountId));
    }

    @GetMapping("/insurance/{idInsurance}")
    public ResponseEntity<List<InsurancePolicy>> getInsurance(@PathVariable Long idInsurance) {
        return ResponseEntity.ok().body(accountService.getInsurance(idInsurance));
    }

    @PostMapping("/saving")
    public ResponseEntity<Object> createSavingAccount(@RequestBody AccountDTO accountDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createSavingAccount(accountDto));
    }

    @PostMapping("/transactional")
    public ResponseEntity<Object> createTransactionalAccount(@RequestBody AccountDTO accountDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createTransactionalAccount(accountDto));
    }

    @PutMapping("/notification/{accountId}")
    @Transactional
    public ResponseEntity<NotificationDTO> update(@PathVariable Long accountId, @RequestBody NotificationDTO notificationDto) {
        return notificationService.update(accountId, notificationDto);
    }

	@PostMapping("/pix/{accountId}")
	public ResponseEntity<PixDTO> savePix(@RequestBody PixDTO pixDTO, @PathVariable Long accountId) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pixService.savePix(pixDTO, accountId));
	}
}
