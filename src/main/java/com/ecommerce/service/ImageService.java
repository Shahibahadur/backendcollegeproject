package com.ecommerce.service;

import com.ecommerce.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ImageService {
    ProductImage uploadImage(MultipartFile file, String category, Long productId);
    List<ProductImage> getImagesByCategory(String category);
    List<ProductImage> getImagesByProductId(Long productId);
    void deleteImage(Long imageId);
    String getImageUrl(Long imageId);
} 