package com.kappa.resources.controller;

import com.kappa.resources.entity.UserEntity;
import com.kappa.resources.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> savUser(@RequestBody UserEntity newUser){
        Map<String, Boolean> response = new HashMap<>();
        try {
            var user = new UserEntity();
            user.setId(newUser.getId());
            user.setEnabled(true);
            user.setUsername(newUser.getUsername());
            user.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
            this.userRepository.save(user);
            response.put("success", true);
            return  ResponseEntity.ok(response);
        }
        catch (Exception e){
            response.put("success", false);
            return ResponseEntity.ok(response);
        }
    }

}
