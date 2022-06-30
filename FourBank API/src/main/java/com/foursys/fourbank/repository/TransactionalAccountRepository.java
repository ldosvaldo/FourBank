package com.foursys.fourbank.repository;



import com.foursys.fourbank.model.TransactionalAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foursys.fourbank.model.TransactionalAccount;

@Repository
public interface TransactionalAccountRepository extends JpaRepository<TransactionalAccount, Long> {
    boolean existsTransactionalAccountByNumber(String number);
    TransactionalAccount findTransactionalAccountByAgencyAndNumber(String agency, String number);
	Optional<TransactionalAccount> findByUserId(Long userId);

}