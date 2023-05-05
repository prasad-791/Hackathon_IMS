package com.example.inventory_management_system.repository;

import com.example.inventory_management_system.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBorrowRepository extends JpaRepository<Borrow,Long> {
}
