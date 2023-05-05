package com.example.inventory_management_system.services;

import com.example.inventory_management_system.entity.Category;
import com.example.inventory_management_system.repository.ICategoryRepository;
import com.example.inventory_management_system.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService{

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    @Autowired
    private ICategoryRepository repository;

    @Override
    public ResponseEntity<?> addCategory(Category category) {
        try{
            this.repository.save(category);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        try{
            List<Category> categoryList = this.repository.findAll();
            return new ResponseEntity<>(categoryList,HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        try{
            Optional<Category> category = this.repository.findById(id);
            if(category.isEmpty()){
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
    public ResponseEntity<?> editCategoryDetails(Long id, Map<String, ?> data) {
        try{
            Optional<Category> category = this.repository.findById(id);
            if(category.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!data.containsKey("name")){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            category.get().setName(data.get("name").toString());
            this.repository.save(category.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getCategoryByID(Long id) {
        try{
            Optional<Category> category = this.repository.findById(id);
            if(category.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(category.get(),HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
