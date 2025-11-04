package org.delivery.product.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.delivery.product.domain.entity.ProductStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegisterDto {

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    private String description;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    @Min(value = 0, message = "재고는 0이상이여야 합니다.")
    //재고
    private Long stock;

}
