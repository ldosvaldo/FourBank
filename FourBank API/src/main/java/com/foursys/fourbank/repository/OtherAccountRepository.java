package com.foursys.fourbank.repository;

import com.foursys.fourbank.model.OtherAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OtherAccountRepository extends JpaRepository<OtherAccount, Long> {
    OtherAccount findOtherAccountByBankNumberAndAgencyAndNumber(String bankNumber, String agency, String number);
}
