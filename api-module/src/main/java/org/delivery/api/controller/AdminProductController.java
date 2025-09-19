package org.delivery.api.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.product.domain.Product;
import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<Product> register(@RequestBody @Validated ProductRegisterDto productRegisterDto) {
        Product newProduct = productService.register(productRegisterDto);
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