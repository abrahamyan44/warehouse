package com.example.warehouse.service;

import com.example.warehouse.entity.Material;
import com.example.warehouse.repository.MaterialRepository;
import com.example.warehouse.service.impl.MaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl  implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public void create(Material material) {
        try {
            Material materialToSave = new Material();
            materialToSave.setName(material.getName());
            materialToSave.setDescription(material.getDescription());
            materialToSave.setIcon(material.getIcon());
            materialToSave.setMaxCount(material.getMaxCount());
            materialRepository.save(materialToSave);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create material: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Material material) {
        try {
            Material materialToUpdate = materialRepository.findById(material.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Material not found"));

            materialToUpdate.setName(material.getName());
            materialToUpdate.setDescription(material.getDescription());
            materialToUpdate.setIcon(material.getIcon());
            materialToUpdate.setMaxCount(material.getMaxCount());
            materialRepository.save(material);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update material: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        materialRepository.deleteById(id);
    }

    @Override
    public Material getById(Long id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }
}
