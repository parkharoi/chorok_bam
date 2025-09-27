package org.delivery.api.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.product.domain.Product;
import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> register(
            @RequestPart @Validated ProductRegisterDto productRegisterDto,
            @RequestPart("thumbnailImage")MultipartFile thumbnailImage,
            @RequestPart("detailImages")List<MultipartFile> detailImages
            ) {

        if (detailImages.size() >= 20) {
            throw new IllegalArgumentException("상세 이미지는 최대 20장까지 가능합니다.");
        }
        Product newProduct = productService.registerWithImages(productRegisterDto, thumbnailImage, detailImages);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id, @RequestBody ProductRegisterDto productRegisterDto ) {
        Product updateProduct = productService.update(id, productRegisterDto);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}