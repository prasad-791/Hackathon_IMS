package com.example.inventory_management_system.services;

import com.example.inventory_management_system.entity.Asset;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public interface IAssetService {
    public ResponseEntity<?> addAsset(Long categoryID, Asset asset);
    public ResponseEntity<?> editAsset(Long id, Map<String,?> data);
    public ResponseEntity<?> deleteAsset(Long id);
    public ResponseEntity<?> getAllAsset();
    public ResponseEntity<?> getAllAssetForCategory(Long categoryID);
    public ResponseEntity<?> deleteAllAssetsForCategory(Long categoryID);
}
