package com.example.inventory_management_system.services;

import com.example.inventory_management_system.entity.Borrow;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IBorrowService {
    public ResponseEntity<?> addBorrowDetails(Long userID, Long assetID, Borrow data);
    public ResponseEntity<?> getAllBorrowDetails();
    public ResponseEntity<?> getBorrowDetailsForID(Long id);
    public ResponseEntity<?> editBorrowDetails(Long id, Map<String,?> data);
    public ResponseEntity<?> deleteBorrowDetailsByID(Long id);
}
