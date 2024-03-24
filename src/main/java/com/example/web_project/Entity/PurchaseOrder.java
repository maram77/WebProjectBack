package com.example.web_project.Entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @Column(length = 50)
    private String status ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


    private String address;

    // Newly added attribute
    private BigDecimal total_price;

    // Constructors


    public PurchaseOrder(List<OrderItem> orderItems, String address) {
        this.orderItems = orderItems;
        this.address = address;
        this.total_price = BigDecimal.ZERO;
        this.status = "In process"; // Initialize total_price to 0
    }

    public PurchaseOrder() {

    }

    // Getters and Setters
    public Long getId() {
        return idOrder;
    }
    void setStatus(String status){
        this.status = status ;

    }

    public void setId(Long id) {
        this.idOrder = id;
    }

    public String getStatus(){
        return status ;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalPrice() {
        return total_price;
    }

    public void setTotalPrice(BigDecimal total_price) {
        this.total_price = total_price;
    }

    // Method to calculate total price
    public void calculateTotalPrice() {
        // this.total_price = OrderService.calculateTotalPrice(this);
    }
}
