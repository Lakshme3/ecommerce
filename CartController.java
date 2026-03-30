package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    // ADD ITEM TO CART
    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {

        // Find or create cart for user
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart = cartRepository.save(cart);
        }

        // Add item to cart
        CartItem item = new CartItem();
        item.setCartId(cart.getId());
        item.setProductId(productId);
        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return "Product added to cart successfully!";
    }

    // VIEW CART
    @GetMapping("/{userId}")
    public List<CartItem> viewCart(@PathVariable Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return List.of();
        }
        return cartItemRepository.findByCartId(cart.getId());
    }

    // REMOVE ITEM FROM CART
    @DeleteMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return "Item removed from cart!";
    }
}