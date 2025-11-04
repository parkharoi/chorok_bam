package org.delivery.product.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "original_image_name") // 2. @Column 명시 & 필드명 수정
    private String originalImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private ImageType type;

    public enum ImageType {
        THUMBNAIL, DETAIL
    }

    public void updateImageInfo(String imageUrl, String originalImageName) {
        this.imageUrl = imageUrl;
        this.originalImageName = originalImageName;
    }
}
