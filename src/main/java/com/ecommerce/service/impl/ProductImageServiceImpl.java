package com.ecommerce.service.impl;

import com.ecommerce.model.ProductImage;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<ProductImage> getImagesByCategory(String category) {
        return productImageRepository.findByCategory(category);
    }

    @Override
    public ProductImage saveImage(ProductImage image) {
        return productImageRepository.save(image);
    }

    @Override
    public void deleteImage(Long id) {
        productImageRepository.deleteById(id);
    }

    @Override
    public List<ProductImage> getAllImages() {
        return productImageRepository.findAll();
    }
} 