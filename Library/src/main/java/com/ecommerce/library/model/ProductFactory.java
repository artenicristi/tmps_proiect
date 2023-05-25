package com.ecommerce.library.model;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.repository.ClothingProductRepository;
import com.ecommerce.library.repository.ElectronicProductRepository;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class ProductFactory {
    private final ImageUpload imageUpload;
    private final ElectronicProductRepository electronicProductRepository;
    private final ClothingProductRepository clothingProductRepository;
    private static volatile ProductFactory instance;

    private ProductFactory(ImageUpload imageUpload,
                           ElectronicProductRepository electronicProductRepository,
                           ClothingProductRepository clothingProductRepository) {
        this.imageUpload = imageUpload;
        this.electronicProductRepository = electronicProductRepository;
        this.clothingProductRepository = clothingProductRepository;
    }

    public static ProductFactory getInstance(ImageUpload imageUpload,
                                                          ElectronicProductRepository electronicProductRepository,
                                                          ClothingProductRepository clothingProductRepository) {
        if (instance == null) {
            synchronized (ProductFactory.class) {
                if (instance == null) {
                    instance = new ProductFactory(imageUpload, electronicProductRepository, clothingProductRepository);
                }
            }
        }
        return instance;
    }

    public IProduct createProduct(MultipartFile imageProduct, ProductDto productDto) {
        IProduct product = null;
        if (productDto.getCategory().getName().equals("Electronic")) {
            product = new ElectronicProduct();
        } else if (productDto.getCategory().getName().equals("Clothing")) {
            product = new ClothingProduct();
        }
        try {
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCostPrice(productDto.getCostPrice());
            product.setSalePrice(productDto.getSalePrice());
            product.setCategory(productDto.getCategory());
            product.setDeleted(false);
            product.setActivated(true);
            if (productDto.getCategory().getName().equals("Electronic")) {
                electronicProductRepository.save((ElectronicProduct) product);
            } else if (productDto.getCategory().getName().equals("Clothing")) {
                clothingProductRepository.save((ClothingProduct) product);
            }
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
