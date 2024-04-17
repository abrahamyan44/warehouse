package com.example.warehouse.repository;

import com.example.warehouse.entity.MaterialToWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialToWarehouseRepository extends JpaRepository<MaterialToWarehouse, Long> {
    Optional<MaterialToWarehouse> findByMaterialIdAndWarehouseId(Long materialId, Long warehouseId);
}
