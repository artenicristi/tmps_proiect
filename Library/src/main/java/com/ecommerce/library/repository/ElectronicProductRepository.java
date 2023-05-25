package com.ecommerce.library.repository;

import com.ecommerce.library.model.ElectronicProduct;
import com.ecommerce.library.model.IProduct;
import com.ecommerce.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ElectronicProductRepository extends JpaRepository<ElectronicProduct, Long> {
}
