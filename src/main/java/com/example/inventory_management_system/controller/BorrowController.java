package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.config.JwtService;
import com.example.inventory_management_system.entity.Borrow;
import com.example.inventory_management_system.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ims/api/v1/borrow")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BorrowController {
    private final BorrowService service;

    private final JwtService jwtService;

    @Autowired
    public BorrowController(BorrowService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBorrowDetails(@RequestParam(name = "userID") Long userID, @RequestParam(name = "assetID") Long assetID, @RequestBody Borrow body, @RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.service.addBorrowDetails(userID,assetID,body);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBorrowDetails(){
        return this.service.getAllBorrowDetails();
    }

    @GetMapping("")
    public ResponseEntity<?> getBorrowDetailsForID(@RequestParam(name = "id") Long id){
        return this.service.getBorrowDetailsForID(id);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editBorrowDetails(@RequestParam(name = "id") Long id, @RequestBody Map<String,?> body, @RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.service.editBorrowDetails(id,body);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBorrowDetailsByID(@RequestParam(name = "id") Long id, @RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.service.deleteBorrowDetailsByID(id);
    }
}
