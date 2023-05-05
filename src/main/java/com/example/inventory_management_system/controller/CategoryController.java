package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.config.JwtService;
import com.example.inventory_management_system.entity.Category;
import com.example.inventory_management_system.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ims/api/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

    private final CategoryService service;
    private final JwtService jwtService;

    @Autowired
    public CategoryController(CategoryService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category body,@RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.service.addCategory(body);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories(){
        return this.service.getAllCategories();
    }

    @GetMapping("")
    public ResponseEntity<?> getCategoryByID(@RequestParam(name = "id")Long id){
        return this.service.getCategoryByID(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategoryByID(@RequestParam(name = "id")Long id,@RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.service.deleteCategory(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestParam(name = "id")Long id, @RequestBody Map<String,?> body,@RequestHeader(name = "Authorization") String token){
        if(!jwtService.isAdmin(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return this.service.editCategoryDetails(id,body);
    }
}
