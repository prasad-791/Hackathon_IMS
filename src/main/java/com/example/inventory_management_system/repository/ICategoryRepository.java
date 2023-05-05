package com.example.inventory_management_system.repository;

import com.example.inventory_management_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
