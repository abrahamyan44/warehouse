package com.example.warehouse.service;

import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.repository.WarehouseRepository;
import com.example.warehouse.service.impl.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void create() {
        try {
            Warehouse warehouse = new Warehouse();
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create warehouse: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }

    @Override
    public Warehouse findById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }
}
