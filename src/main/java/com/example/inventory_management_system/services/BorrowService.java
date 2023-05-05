package com.example.inventory_management_system.services;

import com.example.inventory_management_system.entity.Asset;
import com.example.inventory_management_system.entity.Borrow;
import com.example.inventory_management_system.entity.User;
import com.example.inventory_management_system.repository.IAssetRepository;
import com.example.inventory_management_system.repository.IBorrowRepository;
import com.example.inventory_management_system.repository.IUserRepository;
import com.example.inventory_management_system.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BorrowService implements IBorrowService{

    private static final Logger logger = LogManager.getLogger(BorrowService.class);

    @Autowired
    private IBorrowRepository repository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAssetRepository assetRepository;

    @Override
    public ResponseEntity<?> addBorrowDetails(Long userID, Long assetID, Borrow data) {
        try{
            Optional<User> user = this.userRepository.findById(userID);
            if(user.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Optional<Asset> asset = this.assetRepository.findById(assetID);
            if(asset.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            data.setAsset(asset.get());
            data.setUser(user.get());
            this.repository.save(data);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllBorrowDetails() {
        try{
            List<Borrow> borrowList = this.repository.findAll();
            return new ResponseEntity<>(borrowList,HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteBorrowDetailsByID(Long id) {
        try{
            Optional<Borrow> borrow = this.repository.findById(id);
            if(borrow.isEmpty()){
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
    public ResponseEntity<?> getBorrowDetailsForID(Long id) {
        try{
            Optional<Borrow> borrow = this.repository.findById(id);
            if(borrow.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(borrow,HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> editBorrowDetails(Long id, Map<String, ?> data) {
        try{
            Optional<Borrow> borrow = this.repository.findById(id);
            if(borrow.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!data.containsKey("quantityBorrowed") && !data.containsKey("borrowingDate") && !data.containsKey("returnDate") && !data.containsKey("assetID") && !data.containsKey("userID")){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if(data.containsKey("quantityBorrowed")){
                borrow.get().setQuantityBorrowed(Long.valueOf(data.get("quantityBorrowed").toString()));
            }
            if(data.containsKey("borrowingDate")){
                LocalDate date = LocalDate.parse(data.get("borrowingDate").toString());
                borrow.get().setBorrowingDate(date);
            }
            if(data.containsKey("returnDate")){
                LocalDate date = LocalDate.parse(data.get("returnDate").toString());
                borrow.get().setReturnDate(date);
            }
            if(data.containsKey("assetID")){
                Long assetID = Long.valueOf(data.get("assetID").toString());
                Optional<Asset> asset = this.assetRepository.findById(assetID);
                if(asset.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                borrow.get().setAsset(asset.get());
            }
            if(data.containsKey("userID")){
                Long userID = Long.valueOf(data.get("userID").toString());
                Optional<User> user = this.userRepository.findById(userID);
                if(user.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                borrow.get().setUser(user.get());
            }
            this.repository.save(borrow.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
