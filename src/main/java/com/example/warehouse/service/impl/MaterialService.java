package com.example.warehouse.service.impl;

import com.example.warehouse.entity.Material;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MaterialService {

    @Transactional
    void create(Material material);
    @Transactional
    void update(Material material);
    @Transactional
    void delete(Long id);
    Material getById(Long id);
    List<Material> findAll();
}
