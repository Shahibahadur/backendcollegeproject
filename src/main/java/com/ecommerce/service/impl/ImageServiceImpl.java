package com.ecommerce.service.impl;

import com.ecommerce.model.ProductImage;
import com.ecommerce.repository.ProductImageRepository;
import com.ecommerce.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ProductImageRepository imageRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public ProductImage uploadImage(MultipartFile file, String category, Long productId) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir, category);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);

            // Create and save image record
            ProductImage image = new ProductImage();
            image.setImageName(newFilename);
            image.setImagePath(filePath.toString());
            image.setCategory(category);
            // If you want to store more fields, add them to ProductImage and set them here.

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public List<ProductImage> getImagesByCategory(String category) {
        return imageRepository.findByCategory(category);
    }

    @Override
    public List<ProductImage> getImagesByProductId(Long productId) {
        // No findByProductId in repository. You need to add this method or use an alternative.
    // Example: return imageRepository.findByCategory(category); // if you want by category
    throw new UnsupportedOperationException("findByProductId not implemented in repository");
    }

    @Override
    public void deleteImage(Long imageId) {
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        try {
            // Delete file from filesystem
            Files.deleteIfExists(Paths.get(image.getImagePath()));
            // Delete record from database
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    @Override
    public String getImageUrl(Long imageId) {
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        return "/api/images/" + image.getCategory() + "/" + image.getFileName();
    }
} 