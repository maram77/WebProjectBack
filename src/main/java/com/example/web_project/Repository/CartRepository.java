package com.example.web_project.Repository;

import com.example.web_project.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // You can add custom query methods here if needed
}
