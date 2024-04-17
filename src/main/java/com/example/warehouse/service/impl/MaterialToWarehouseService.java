package com.example.warehouse.service.impl;

import com.example.warehouse.entity.request.AddMaterialToWarehouseRequest;
import com.example.warehouse.entity.request.MoveMaterialToWarehouseRequest;
import com.example.warehouse.entity.request.UseMaterialRequest;
import jakarta.transaction.Transactional;

public interface MaterialToWarehouseService {

    @Transactional
    void addMaterialToWarehouse(AddMaterialToWarehouseRequest request);

    @Transactional
    void moveMaterialToWarehouse(MoveMaterialToWarehouseRequest request);

    @Transactional
    void useMaterial(UseMaterialRequest request);
}
