package org.delivery.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product-images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private Product product;

    private ImageType type;

    public enum ImageType {
        THUMBNAIL, DETAIL
    }
}
