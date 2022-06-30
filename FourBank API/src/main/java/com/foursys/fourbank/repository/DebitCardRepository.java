package com.foursys.fourbank.repository;
import com.foursys.fourbank.model.Account;
import com.foursys.fourbank.model.DebitCard;
import com.foursys.fourbank.model.FourBankAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {

	List<DebitCard> findAllByAccount(FourBankAccount account);
}
