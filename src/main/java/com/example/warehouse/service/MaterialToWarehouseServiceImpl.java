package com.example.warehouse.service;

import com.example.warehouse.entity.Material;
import com.example.warehouse.entity.MaterialToWarehouse;
import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.entity.request.AddMaterialToWarehouseRequest;
import com.example.warehouse.entity.request.MoveMaterialToWarehouseRequest;
import com.example.warehouse.entity.request.UseMaterialRequest;
import com.example.warehouse.repository.MaterialRepository;
import com.example.warehouse.repository.MaterialToWarehouseRepository;
import com.example.warehouse.repository.WarehouseRepository;
import com.example.warehouse.service.impl.MaterialToWarehouseService;
import org.springframework.stereotype.Service;

@Service
public class MaterialToWarehouseServiceImpl implements MaterialToWarehouseService {

    private final MaterialToWarehouseRepository materialToWarehouseRepository;
    private final WarehouseRepository warehouseRepository;
    private final MaterialRepository materialRepository;

    public MaterialToWarehouseServiceImpl(MaterialToWarehouseRepository materialToWarehouseRepository,
                                          WarehouseRepository warehouseRepository,
                                          MaterialRepository materialRepository) {
        this.materialToWarehouseRepository = materialToWarehouseRepository;
        this.warehouseRepository = warehouseRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public void addMaterialToWarehouse(AddMaterialToWarehouseRequest request) {
        MaterialToWarehouse materialToWarehouse = materialToWarehouseRepository
                .findByMaterialIdAndWarehouseId(request.getMaterialId(), request.getWarehouseId())
                .orElse(new MaterialToWarehouse());

        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));
        materialToWarehouse.setMaterial(material);

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
        materialToWarehouse.setWarehouse(warehouse);


        int maxCount = material.getMaxCount();
        int newQuantity = materialToWarehouse.getQuantity() + request.getQuantity();

        if (newQuantity < maxCount) {
            materialToWarehouse.setQuantity(newQuantity);
            materialToWarehouseRepository.save(materialToWarehouse);
        } else {
            throw new IllegalArgumentException("Adding material exceeds warehouse capacity");
        }

    }

    @Override
    public void moveMaterialToWarehouse(MoveMaterialToWarehouseRequest request) {

        MaterialToWarehouse sourceWarehouse = materialToWarehouseRepository
                .findByMaterialIdAndWarehouseId(request.getMaterialId(), request.getSourceWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Material not found in source warehouse"));

        MaterialToWarehouse destinationWarehouse = materialToWarehouseRepository
                .findByMaterialIdAndWarehouseId(request.getMaterialId(), request.getDestinationWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Material not found in destination warehouse"));

        int sourceQuantity = sourceWarehouse.getQuantity();
        int destinationCapacity = destinationWarehouse.getQuantity();
        int requestedQuantity = request.getQuantity();


        if (requestedQuantity <= 0) {
            throw new IllegalArgumentException("Requested quantity exceeds available capacity in destination warehouse");
        }

        if (sourceQuantity >= requestedQuantity) {
            sourceWarehouse.setQuantity(sourceQuantity - requestedQuantity);
            destinationWarehouse.setQuantity(destinationWarehouse.getQuantity() + requestedQuantity);
            if (sourceWarehouse.getQuantity() < 0) {
                throw new IllegalArgumentException("Source quantity cannot be negative");
            }

            if (destinationCapacity > destinationWarehouse.getQuantity()) {
                throw new IllegalArgumentException("Destination capacity cannot be negative");
            }
            materialToWarehouseRepository.save(sourceWarehouse);
            materialToWarehouseRepository.save(destinationWarehouse);
        } else {
            throw new IllegalArgumentException("Requested quantity exceeds available quantity in the source warehouse");
        }
    }

    @Override
    public void useMaterial(UseMaterialRequest request) {
        MaterialToWarehouse materialToWarehouse = materialToWarehouseRepository
                .findByMaterialIdAndWarehouseId(request.getMaterialId(), request.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Material not found in source warehouse"));

        int availableQuantity = materialToWarehouse.getQuantity();
        int requestedQuantity = request.getRequestedQuantity();

        if (availableQuantity >= requestedQuantity && requestedQuantity >= 0) {
            materialToWarehouse.setQuantity(availableQuantity - requestedQuantity);
            materialToWarehouseRepository.save(materialToWarehouse);
        } else {
            throw new IllegalArgumentException("Requested quantity exceeds available quantity or is negative in the source warehouse");
        }

    }
}
