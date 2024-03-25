package com.example.web_project.Repository;

import com.example.web_project.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByProductReference(String productReference);
    void deleteByProductReference(String productReference);


}