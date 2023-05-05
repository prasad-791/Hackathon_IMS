package com.example.inventory_management_system.repository;

import com.example.inventory_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user where username=?1",nativeQuery = true)
    public Optional<User> findByUsername(String username);
}
