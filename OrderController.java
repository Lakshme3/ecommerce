package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    // PLACE ORDER
    @PostMapping("/place/{userId}")
    public String placeOrder(@PathVariable Long userId) {

        // Get user's cart
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return "Cart is empty!";
        }

        // Get cart items
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            return "Cart is empty!";
        }

        // Calculate total price
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            totalPrice += product.getPrice() * item.getQuantity();
        }

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);

        // Create order items
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItemRepository.save(orderItem);
        }

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "Order placed successfully! Order ID: " + order.getId() +
                " Total: Rs." + totalPrice;
    }

    // VIEW ALL ORDERS OF A USER
    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }
}