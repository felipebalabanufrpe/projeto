package com.miromori.project_sympla_entrega_2.controllers;

import com.miromori.project_sympla_entrega_2.models.User;
import com.miromori.project_sympla_entrega_2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }


    public ResponseEntity<Optional<User>> findById(@PathVariable Long id){
        return ResponseEntity.ok(userRepository.findById(id));
    }


    public ResponseEntity<User> save(@RequestBody User user){
        return ResponseEntity.ok(userRepository.save(user));
    }


    public ResponseEntity<User> delete(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
        return ResponseEntity.ok(user);
    }


    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
