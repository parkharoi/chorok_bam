package org.delivery.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.delivery.product.domain.entity.Product;
import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> register(
        @RequestPart("productData") String productDataJson,
        @RequestPart("thumbnailImg") MultipartFile thumbnailImage,
        @RequestPart("detailImg") List<MultipartFile> detailImages
    ) {
        System.out.println("🔥 컨트롤러 진입");

        ProductRegisterDto dto;

        try {
            dto = objectMapper.readValue(productDataJson, ProductRegisterDto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("상품 데이터 JSON 형식이 올바르지 않습니다.");
        }

        Product newProduct = productService.registerWithImages(dto, thumbnailImage, detailImages);

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