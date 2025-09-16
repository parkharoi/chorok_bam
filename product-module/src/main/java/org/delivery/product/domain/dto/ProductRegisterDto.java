package org.delivery.product.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
}
