package com.example.inventory_management_system.services;

import com.example.inventory_management_system.entity.Asset;
import com.example.inventory_management_system.entity.Category;
import com.example.inventory_management_system.repository.IAssetRepository;
import com.example.inventory_management_system.repository.ICategoryRepository;
import com.example.inventory_management_system.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AssetService implements IAssetService{

    private static final Logger logger = LogManager.getLogger(AssetService.class);

    @Autowired
    private IAssetRepository repository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> addAsset(Long categoryID,Asset asset) {
        try{
            Optional<Category> category = this.categoryRepository.findById(categoryID);
            if(category.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            asset.setCategory(category.get());
            this.repository.save(asset);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> editAsset(Long id, Map<String, ?> data) {
        try{
            Optional<Asset> asset = this.repository.findById(id);
            if(asset.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!data.containsKey("name") && !data.containsKey("quantity") && !data.containsKey("receivedDate") && !data.containsKey("locationOfStorage")){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(data.containsKey("name")){
                asset.get().setName(data.get("name").toString());
            }
            if(data.containsKey("quantity")){
                asset.get().setQuantity(Long.valueOf(data.get("quantity").toString()));
            }
            if(data.containsKey("receivedDate")){
                LocalDate date = LocalDate.parse(data.get("receivedDate").toString());
                asset.get().setReceivedDate(date);
            }
            if(data.containsKey("locationOfStorage")){
                asset.get().setLocationOfStorage(data.get("locationOfStorage").toString());
            }
            if(data.containsKey("categoryID")){
                Optional<Category> category = this.categoryRepository.findById(Long.valueOf(data.get("categoryID").toString()));
                if(category.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                asset.get().setCategory(category.get());
            }
            this.repository.save(asset.get());
            return new ResponseEntity<>(asset.get(),HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteAsset(Long id) {
        try{
            Optional<Asset> asset = this.repository.findById(id);
            if(asset.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            this.repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllAsset() {
        try{
            List<Asset> assetList = this.repository.findAll();
            return new ResponseEntity<>(assetList,HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllAssetForCategory(Long categoryID) {
        try{
            Optional<Category> category = this.categoryRepository.findById(categoryID);
            if(category.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Asset> assetList = this.repository.findAllAssetsForCategory(categoryID);
            return new ResponseEntity<>(assetList,HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteAllAssetsForCategory(Long categoryID) {
        try{
            Optional<Category> category = this.categoryRepository.findById(categoryID);
            if(category.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            this.repository.deleteAllAssetsForCategory(categoryID);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
