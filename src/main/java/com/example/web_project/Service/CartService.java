package com.example.web_project.Service;

import com.example.web_project.Entity.Cart;
import com.example.web_project.Entity.OrderItem;
import com.example.web_project.Entity.Product;
import com.example.web_project.Repository.CartRepository;
import com.example.web_project.Repository.OrderItemRepository;
import com.example.web_project.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // Add more methods as needed

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void addItemToCart(Long cartId, OrderItem orderItem) {
        // Retrieve the cart from the database
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Add the order item to the cart
        cart.getOrderItems().add(orderItem);

        // Set the cart for the order item (bi-directional relationship)
        orderItem.setCart(cart);

        // Update the cart in the database
        cartRepository.save(cart);
    }
}
