package com.foursys.fourbank.repository;

import com.foursys.fourbank.model.FourBankAccount;
import com.foursys.fourbank.enums.PixType;
import com.foursys.fourbank.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.foursys.fourbank.model.FourBankAccount;
import java.util.List;

import java.util.List;

import com.foursys.fourbank.model.Pix;

@Repository
public interface PixRepository extends JpaRepository<Pix, Long> {
    Pix findPixByPixTypeAndPixKeyValue(PixType pixType, String pixKeyValue);
}
