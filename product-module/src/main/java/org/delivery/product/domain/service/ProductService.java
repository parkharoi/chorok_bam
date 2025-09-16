package org.delivery.product.domain.service;

import lombok.RequiredArgsConstructor;
import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.Product;
import org.delivery.product.domain.ProductStatus;
import org.delivery.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product register(ProductRegisterDto productRegisterDto) {
        Product newProduct = Product.builder()
                .name(productRegisterDto.getName())
                .price(productRegisterDto.getPrice())
                .description(productRegisterDto.getDescription())
                .stock(productRegisterDto.getStock())
                .status(ProductStatus.ACTIVE)
                .build();

        return productRepository.save(newProduct);
    }

}
