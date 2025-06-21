package com.ecommerce.controller;




import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/images")
public class ImageController {
    // private final ImageService imageService; // Temporarily removed to fix startup error

    // @PostMapping("/upload")
    // public ResponseEntity<ProductImage> uploadImage(
    //         @RequestParam("file") MultipartFile file,
    //         @RequestParam("category") String category,
    //         @RequestParam(value = "productId", required = false) Long productId) {
    //     ProductImage image = imageService.uploadImage(file, category, productId);
    //     return ResponseEntity.ok(image);
    // }

    // @GetMapping("/category/{category}")
    // public ResponseEntity<List<ProductImage>> getImagesByCategory(@PathVariable String category) {
    //     List<ProductImage> images = imageService.getImagesByCategory(category);
    //     return ResponseEntity.ok(images);
    // }

    // @GetMapping("/product/{productId}")
    // public ResponseEntity<List<ProductImage>> getImagesByProductId(@PathVariable Long productId) {
    //     List<ProductImage> images = imageService.getImagesByProductId(productId);
    //     return ResponseEntity.ok(images);
    // }

    // @DeleteMapping("/{imageId}")
    // public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
    //     imageService.deleteImage(imageId);
    //     return ResponseEntity.noContent().build();
    // }

    // @GetMapping("/{category}/{filename:.+}")
    // public ResponseEntity<Resource> serveFile(@PathVariable String category, @PathVariable String filename) {
    //     try {
    //         Path file = Paths.get("uploads/images").resolve(category).resolve(filename);
    //         Resource resource = new UrlResource(file.toUri());
    //         if (resource.exists() || resource.isReadable()) {
    //             return ResponseEntity.ok()
    //                     .contentType(MediaType.IMAGE_JPEG)
    //                     .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
    //                     .body(resource);
    //         } else {
    //             return ResponseEntity.notFound().build();
    //         }
    //     } catch (Exception e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}