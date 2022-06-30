package com.foursys.fourbank.repository;



import com.foursys.fourbank.model.SavingsAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {

    boolean existsSavingsAccountByNumber(String number);
    SavingsAccount findSavingsAccountByAgencyAndNumber(String agency, String number);
    Optional<SavingsAccount> findByUserId(Long userId);
}
