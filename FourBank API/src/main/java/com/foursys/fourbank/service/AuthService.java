package com.foursys.fourbank.service;

import com.foursys.fourbank.dto.LoginDTO;
import com.foursys.fourbank.dto.UserDTO;
import com.foursys.fourbank.exception.InvalidValueException;
import com.foursys.fourbank.exception.UnreportedEssentialFieldException;
import com.foursys.fourbank.model.User;
import com.foursys.fourbank.repository.UserRepository;
import com.foursys.fourbank.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    public UserRepository userRepository;

    public UserDTO authenticate(LoginDTO loginDTO) {
        if(loginDTO.getCpf() == null || loginDTO.getPassword() == null) {
            throw new UnreportedEssentialFieldException("Missing credentials");
        }
        User user = userRepository.findUserByCpfAndPassword(loginDTO.getCpf(), loginDTO.getPassword());
        if(user == null) throw new InvalidValueException("invalid credentials");
        return Converter.userToDto(user);
    }
}
