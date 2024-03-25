package com.example.web_project.Controller;

import com.example.web_project.Entity.Cart;
import com.example.web_project.Entity.OrderItem;
import com.example.web_project.Entity.Product;
import com.example.web_project.Entity.PurchaseOrder;
import com.example.web_project.Repository.PurchaseOrderRepository;
import com.example.web_project.Service.CartService;
import com.example.web_project.Service.OrderItemService;
import com.example.web_project.Service.ProductService;
import com.example.web_project.Service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
  @Autowired
    PurchaseOrderRepository purchaseOrderRepository;


    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }


    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        cart.setIdCart(id);
        return cartService.saveCart(cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }

    /*********************************************************/

   // adds product id and cart id but no order id
    /*
    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<String> addItemToCart(@PathVariable Long cartId, @RequestBody Map<String, Object> requestBody) {
        try {
            String productId = (String) requestBody.get("product_id");
            int quantity = (int) requestBody.get("quantity");

            // Create a new OrderItem entity
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(quantity);

            // Set the Cart ID
            Cart cart = new Cart();
            cart.setIdCart(cartId);
            orderItem.setCart(cart);

            // Set the Product ID
            Product product = productService.getProductByReference(productId);
            if (product == null) {
                return ResponseEntity.badRequest().body("Product with ID " + productId + " not found");
            }
            orderItem.setProduct(product);

            // Save the OrderItem
            cartService.addItemToCart(cartId, orderItem);

            return ResponseEntity.ok("Item added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item to cart");
        }
    } */

    @PostMapping("/{cartId}/add-item")
    public ResponseEntity<String> addItemToCart(@PathVariable Long cartId,
                                                @RequestParam Long order_id,
                                                @RequestBody Map<String, Object> requestBody) {
        try {
            String productId = (String) requestBody.get("product_id");
            int quantity = (int) requestBody.get("quantity");

            // Fetch the existing Order by its ID using the repository
            PurchaseOrder order = purchaseOrderRepository.findById(order_id).orElse(null);
            if (order == null) {
                return ResponseEntity.badRequest().body("Order with ID " + order_id + " not found");
            }

            // Create a new OrderItem entity
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(quantity);

            // Set the Cart ID
            Cart cart = new Cart();
            cart.setIdCart(cartId);
            orderItem.setCart(cart);

            // Set the Product ID
            Product product = productService.getProductByReference(productId);
            if (product == null) {
                return ResponseEntity.badRequest().body("Product with ID " + productId + " not found");
            }
            orderItem.setProduct(product);

            // Set the Order
            orderItem.setOrder(order);

            // Save the OrderItem
            cartService.addItemToCart(cartId, orderItem);

            return ResponseEntity.ok("Item added to cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item to cart");
        }
    }
}
