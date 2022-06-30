package com.foursys.fourbank.repository;

import com.foursys.fourbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByCpfAndPassword(String cpf, String password);
}
