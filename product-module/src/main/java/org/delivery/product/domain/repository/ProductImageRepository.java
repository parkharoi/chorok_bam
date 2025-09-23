package org.delivery.product.domain.repository;

import org.delivery.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);

    List<ProductImage> findByProductIdAndType(Long productId, ProductImage.ImageType type);
}
