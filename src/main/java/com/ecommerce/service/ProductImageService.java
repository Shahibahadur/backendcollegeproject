package com.ecommerce.service;

import com.ecommerce.model.ProductImage;
import java.util.List;

public interface ProductImageService {
    List<ProductImage> getImagesByCategory(String category);
    ProductImage saveImage(ProductImage image);
    void deleteImage(Long id);
    List<ProductImage> getAllImages();
} 