package com.example.inventory_management_system.repository;

import com.example.inventory_management_system.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAssetRepository extends JpaRepository<Asset, Long> {
    @Query(value = "select * from asset where category=?1",nativeQuery = true)
    public List<Asset> findAllAssetsForCategory(Long categoryID);
    @Modifying
    @Query(value = "delete from asset where category=?1",nativeQuery = true)
    @Transactional
    public void deleteAllAssetsForCategory(Long categoryID);
}
