package com.ecommerce.library.repository;

import com.ecommerce.library.model.ClothingProduct;
import com.ecommerce.library.model.ElectronicProduct;
import com.ecommerce.library.model.IProduct;
import com.ecommerce.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClothingProductRepository extends JpaRepository<ClothingProduct, Long> {
}
