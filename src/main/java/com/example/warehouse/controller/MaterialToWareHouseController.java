package com.example.warehouse.controller;

import com.example.warehouse.entity.request.AddMaterialToWarehouseRequest;
import com.example.warehouse.entity.request.MoveMaterialToWarehouseRequest;
import com.example.warehouse.entity.request.UseMaterialRequest;
import com.example.warehouse.service.MaterialToWarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/materials")
public class MaterialToWareHouseController {

    private final MaterialToWarehouseServiceImpl materialToWarehouseService;

    public MaterialToWareHouseController(MaterialToWarehouseServiceImpl materialToWarehouseService) {
        this.materialToWarehouseService = materialToWarehouseService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMaterialToWarehouse(@RequestBody AddMaterialToWarehouseRequest request) {
        try {
            materialToWarehouseService.addMaterialToWarehouse(request);
            return ResponseEntity.ok("Material added to warehouse successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/move-material")
    public ResponseEntity<String> moveMaterialToWarehouse(@RequestBody MoveMaterialToWarehouseRequest request) {
        try {
            materialToWarehouseService.moveMaterialToWarehouse(request);
            return new ResponseEntity<>("Material moved successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/use")
    public ResponseEntity<String> useMaterial(@RequestBody UseMaterialRequest request) {
        try {
            materialToWarehouseService.useMaterial(request);
            return ResponseEntity.ok("You have successfully used the material " + request.getRequestedQuantity() );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
