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
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/{id}")
    public Optional<?> getUser(@PathVariable(name = "id") Optional<Long> id){
        //var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(id.isPresent()){
            return this.userRepository.findById(id.get());
        }
        return Optional.of(this.userRepository.findAll());
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

    @PostMapping("/user")
    public String savUser(@RequestBody UserEntity newUser){
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
