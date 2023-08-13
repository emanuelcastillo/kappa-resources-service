package com.kappa.resources.controller;

import com.kappa.resources.entity.UserEntity;
import com.kappa.resources.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ApiController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/user/{id}")
    public Optional<?> getUser(@PathVariable(name = "id") Long id){
        //return this.userRepository.findById(id);
        return this.userRepository.findById(id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity requestDataUser, @PathVariable(name = "id") Long id){
        Optional<UserEntity> storageUser = this.userRepository.findById(id);
        if (storageUser.isPresent()){
            UserEntity _user = storageUser.get();
            _user.setUsername(requestDataUser.getUsername());
            _user.setPassword(this.passwordEncoder.encode(requestDataUser.getUsername()));
            return new ResponseEntity<>(this.userRepository.save(_user), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id){
        var user = this.userRepository.findById(id);
        if (user.isPresent()){
            this.userRepository.delete(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/save")
    public String saveNewUser(@RequestBody UserEntity newUser){
        try {
            var user = new UserEntity();
            user.setId(newUser.getId());
            user.setEnabled(true);
            user.setUsername(newUser.getUsername());
            user.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
            this.userRepository.save(user);
            return "success";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}
