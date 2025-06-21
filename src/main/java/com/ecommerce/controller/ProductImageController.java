package com.ecommerce.controller;

import com.ecommerce.model.ProductImage;
import com.ecommerce.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @Value("${image.upload.dir:./uploads/images}")
    private String imageUploadDir;

    private Path imageUploadPath;

    @PostConstruct
    public void init() {
        this.imageUploadPath = Paths.get(imageUploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.imageUploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create image upload directory!", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductImage>> getAllProductImages() {
        return ResponseEntity.ok(productImageService.getAllImages());
    }

    @PostMapping
    public ResponseEntity<ProductImage> saveImage(@RequestBody ProductImage image) {
        return ResponseEntity.ok(productImageService.saveImage(image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        productImageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{category}/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String category, @PathVariable String filename) {
        try {
            String subDirectory;
            switch (category.toLowerCase()) {
                case "business":
                case "gaming":
                case "ultrabooks":
                case "budget":
                    subDirectory = "laptops/" + category.toLowerCase();
                    break;
                case "profile":
                    subDirectory = "profile-pictures";
                    break;
                default:
                    subDirectory = category.toLowerCase();
                    break;
            }

            Path file = this.imageUploadPath.resolve(subDirectory).resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                System.err.println("Image not found at path: " + file.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL error for filename: " + filename);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while serving image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 