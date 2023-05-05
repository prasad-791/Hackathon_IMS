package com.example.inventory_management_system.services;

import com.example.inventory_management_system.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public interface ICategoryService {
    public ResponseEntity<?> addCategory(Category category);
    public ResponseEntity<?> getAllCategories();
    public ResponseEntity<?> deleteCategory(Long id);
    public ResponseEntity<?> editCategoryDetails(Long id,Map<String,?> data);
    public ResponseEntity<?> getCategoryByID(Long id);
}
