package com.example.inventory_management_system.services;

import com.example.inventory_management_system.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IUserService {
    public ResponseEntity<?> addUser(User user);
    public ResponseEntity<?> loginUser(Map<String,?> body);
    public ResponseEntity<?> getAllUsers();
}
