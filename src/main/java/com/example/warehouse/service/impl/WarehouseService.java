package com.example.warehouse.service.impl;

import com.example.warehouse.entity.Warehouse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface WarehouseService {

    @Transactional
    void create();
    @Transactional
    void delete(Long id);
    Warehouse findById(Long id);
    List<Warehouse> findAll();
}
