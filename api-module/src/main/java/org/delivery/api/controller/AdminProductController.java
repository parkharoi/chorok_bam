package org.delivery.api.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.product.domain.Product;
import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Product> register(@RequestBody @Validated ProductRegisterDto productRegisterDto) {
        Product newProduct = productService.register(productRegisterDto);
        return ResponseEntity.ok(newProduct);
    }
}