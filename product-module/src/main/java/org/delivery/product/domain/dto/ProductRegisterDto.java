package org.delivery.product.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.delivery.product.domain.Product;
import org.delivery.product.domain.ProductStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRegisterDto {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    private String description;

    @NotNull
    private Long stock;

    private ProductStatus status;
}
