package org.delivery.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductStatus {
    ACTIVE,
    INACTIVE;
}
