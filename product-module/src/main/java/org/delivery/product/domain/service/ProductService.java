package org.delivery.product.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.file.FileStorageService;
import org.delivery.product.domain.entity.ProductImage;
import org.delivery.product.domain.dto.ProductRegisterDto;
import org.delivery.product.domain.entity.Product;
import org.delivery.product.domain.entity.ProductStatus;
import org.delivery.product.domain.mapper.ProductMapper;
import org.delivery.product.domain.repository.ProductImageRepository;
import org.delivery.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final FileStorageService fileStorageService;
    private final ProductMapper productMapper;


    @Transactional
    public Product registerWithImages(
            ProductRegisterDto dto,
            MultipartFile thumbnailImage,
            List<MultipartFile> detailImages) {

        // dto -> entity 상품저장
        Product product = productMapper.toEntity(dto);
        product.setStatus(ProductStatus.ACTIVE);
        product = productRepository.save(product);

        // 1. 썸네일 이미지
        if(thumbnailImage != null && !thumbnailImage.isEmpty()) {
            saveProductImage(product, thumbnailImage, ProductImage.ImageType.THUMBNAIL);
        }

        //2.상세 이미지
        if (detailImages != null) { // ✨ 이 Null 체크가 핵심입니다.
            for (MultipartFile detailImage : detailImages) {
                if (!detailImage.isEmpty()) {
                    saveProductImage(product, detailImage, ProductImage.ImageType.DETAIL);
                }
            }
        }


        return product;
    }

    private void saveProductImage(Product product, MultipartFile file, ProductImage.ImageType type) {
        try {
            String originalFilename = file.getOriginalFilename();
            String imageUrl = fileStorageService.upload(file);

            ProductImage image = ProductImage.builder()
                    .product(product)
                    .imageUrl(imageUrl)
                    .originalImageName(originalFilename)
                    .type(type)
                    .build();
            productImageRepository.save(image);
        } catch (Exception e) {
            log.error("[saveProductImage] 이미지 저장 실패 - 상품 ID: {}", product.getId(), e);
            throw new RuntimeException("상품 이미지 업로드 중 오류가 발생했습니다.", e);
        }
    }


    //단일 상품 조회
    @Transactional(readOnly = true)
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다. ID: " + id));
    }

    //전체 상품 조회
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    @Transactional
    public Product update(Long id, ProductRegisterDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다. id=" + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());

        return product;
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 존재하지 않습니다. id=" + id));

        List<ProductImage> images = productImageRepository.findByProduct(product);

        for (ProductImage image : images) {
            try {
                fileStorageService.delete(image.getImageUrl());
            }catch (Exception e) {
                log.warn("[delete] 스토리지 파일 삭제 실패 : {}", image.getImageUrl(), e);
            }
        }

        productRepository.delete(product);
    }

}
