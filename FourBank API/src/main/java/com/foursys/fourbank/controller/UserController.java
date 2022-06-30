package com.foursys.fourbank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.foursys.fourbank.dto.UserDTO;
import org.springframework.web.bind.annotation.*;
import com.foursys.fourbank.dto.UserDTO;
import com.foursys.fourbank.service.UserService;
import com.foursys.fourbank.dto.UserPasswordDTO;
import com.foursys.fourbank.model.User;
import com.foursys.fourbank.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> update(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
    	return ResponseEntity.status(HttpStatus.OK).body(userService.update(userDTO, userId));
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    	return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
       userService.deleteById(id);
       return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/password/{userId}")
    public ResponseEntity<UserPasswordDTO> updatePassword(@PathVariable Long userId,
    													  @RequestBody UserPasswordDTO userPasswordDTO) {
    	return ResponseEntity.status(HttpStatus.OK).body(userService.updatePassword(userPasswordDTO, userId));
    }
}

