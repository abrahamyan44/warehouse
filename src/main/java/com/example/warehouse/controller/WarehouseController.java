package com.example.warehouse.controller;

import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.service.impl.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWarehouse() {
        try {
            warehouseService.create();
            return ResponseEntity.status(HttpStatus.CREATED).body("Warehouse created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create warehouse: " + e.getMessage());
        }
    }

    @DeleteMapping("/warehouse/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable Long id) {
        try {
            warehouseService.delete(id);
            return ResponseEntity.ok("Warehouse deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete warehouse: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.findById(id);
        if (warehouse != null) {
            return ResponseEntity.ok(warehouse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseService.findAll();
        return ResponseEntity.ok(warehouses);
    }
}
