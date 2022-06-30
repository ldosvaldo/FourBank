package com.foursys.fourbank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foursys.fourbank.model.SavingsAccount;
import com.foursys.fourbank.model.TransactionalAccount;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {

    private Long id;
    private String agency;
    private String number;
    private String bankNumber;
    private Double yieldRate;
    private Double balanceLimit;
    private Double balance;
    private Double maintenanceFee;
    private String accountType;
    private Double limit;
    private String userName;
    private String cpf;
    private Long userId;
    private NotificationDTO notification;

    public AccountDTO(SavingsAccount savingsAccount){
        this.id = savingsAccount.getId();
        this.agency = savingsAccount.getAgency();
        this.number = savingsAccount.getNumber();
        this.bankNumber = savingsAccount.getBankNumber();
        this.yieldRate = savingsAccount.getYieldRate();
        this.balance = savingsAccount.getBalance();
        this.userName = savingsAccount.getUserName();
        this.cpf = savingsAccount.getCpf();

    }

    public AccountDTO(TransactionalAccount transactionalAccount){
        this.id = transactionalAccount.getId();
        this.agency = transactionalAccount.getAgency();
        this.number = transactionalAccount.getNumber();
        this.bankNumber = transactionalAccount.getBankNumber();
        this.maintenanceFee = transactionalAccount.getMaintenanceFee();
        this.balanceLimit = transactionalAccount.getBalanceLimit();
        this.balance = transactionalAccount.getBalance();
        this.limit = transactionalAccount.getBalanceLimit();
        this.balance = transactionalAccount.getBalance();
        this.userName = transactionalAccount.getUserName();
        this.cpf = transactionalAccount.getCpf();
    }
}

