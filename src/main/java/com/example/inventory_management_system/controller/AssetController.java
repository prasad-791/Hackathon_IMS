package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.config.JwtService;
import com.example.inventory_management_system.entity.Asset;
import com.example.inventory_management_system.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ims/api/v1/asset")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssetController {

    private final AssetService assetService;
    private final JwtService jwtService;

    @Autowired
    public AssetController(AssetService assetService, JwtService jwtService) {
        this.assetService = assetService;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAsset(@RequestParam(name = "categoryID") Long id,@RequestBody Asset asset,@RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.assetService.addAsset(id,asset);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAssets(){
        return this.assetService.getAllAsset();
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAssetsForCategory(@RequestParam(name = "categoryID") Long categoryID){
        return this.assetService.getAllAssetForCategory(categoryID);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAsset(@RequestParam(name = "id") Long id,@RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.assetService.deleteAsset(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAsset(@RequestParam(name = "id") Long id, @RequestBody Map<String,?> body,@RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.assetService.editAsset(id,body);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllAssetsForCategory(@RequestParam(name = "categoryID") Long categoryID,@RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.assetService.deleteAllAssetsForCategory(categoryID);
    }
}
