package com.example.web_project.Controller;

import com.example.web_project.Entity.Cart;
import com.example.web_project.Entity.OrderItem;
import com.example.web_project.Entity.Product;
import com.example.web_project.Service.CartService;
import com.example.web_project.Service.OrderItemService;
import com.example.web_project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService, CartService cartService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {

        Long cartId = orderItem.getCart().getIdCart();

        Cart cart = cartService.getCartById(cartId);
        orderItem.setCart(cart);

        Product product = productService.getProductByReference(orderItem.getProduct().getProductReference());

        orderItem.setProduct(product);

        return orderItemService.saveOrderItem(orderItem);

    }

    @PutMapping("/{id}")
    public OrderItem updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        return orderItemService.saveOrderItem(orderItem);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
