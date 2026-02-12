package org.delivery.product.domain.mapper;


import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRegisterDto dto);

    ProductRegisterDto toDto(Product entity);
}
