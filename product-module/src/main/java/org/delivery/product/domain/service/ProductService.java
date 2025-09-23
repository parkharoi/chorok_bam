package org.delivery.product.domain.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.file.FileStorageService;
import org.delivery.product.domain.ProductImage;
import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.Product;
import org.delivery.product.domain.ProductStatus;
import org.delivery.product.domain.repository.ProductImageRepository;
import org.delivery.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FileStorageService fileStorageService;


    @Transactional
    public Product registerWithImages(
            ProductRegisterDto dto,
            MultipartFile thumbnailImages,
            List<MultipartFile> detailImages) {
        //상품저장
        Product product = productRepository.save(Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .stock(dto.getStock())
                .status(dto.getStatus())
                .build());

        String thumbnailUrl = fileStorageService.upload(thumbnailImages);
        product.setThumbnailImageUrl(thumbnailUrl);

        for (MultipartFile detailImage : detailImages) {
            String imageUrl = fileStorageService.upload(detailImage);

            ProductImage image = ProductImage.builder()
                    .product(product)
                    .imageUrl(imageUrl)
                    .type(ProductImage.ImageType.DETAIL)
                    .build();

            productImageRepository.save(image);
        }

        return product;
    }


    //단일 상품 조회
    @Transactional(readOnly = true)
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id =" + id));
    }

    //전체 상품 조회
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    @Transactional
    public Product update(Long id, ProductRegisterDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id=" + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());

        return product;
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. id=" + id));

        productRepository.delete(product);
    }

}
