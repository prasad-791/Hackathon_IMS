package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.entity.User;
import com.example.inventory_management_system.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ims/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user){

        return this.service.addUser(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        return this.service.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,?> body){
        return this.service.loginUser(body);
    }
}
