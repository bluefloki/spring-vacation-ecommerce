package com.example.demo.services;
import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private CustomerRepository customerRepository;
    private CartRepository cartRepository;


    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // get data from dto
        Cart cart = purchase.getCart();
        Customer customer = purchase.getCustomer();
        Set<CartItem> cartItems = purchase.getCartItems();

        try {
            // Check if customer is null
            if (customer == null) {
                throw new IllegalArgumentException("Customer cannot be null");
            }

            // Check if cartItems is empty
            if (cartItems == null || cartItems.isEmpty()) {
                throw new IllegalArgumentException("Cart items cannot be empty");
            }

            // set order tracking number
            String orderTrackingNumber = generateOrderTrackingNumber();
            cart.setOrderTrackingNumber(orderTrackingNumber);

            // populate cart with cart items and customer details
            cartItems.forEach(item -> cart.add(item));
            cart.setCustomer(customer);
            cart.setStatus(StatusType.ordered);  // Use StatusType.ORDERED from the imported enum

            // populate customer with cart
            customer.add(cart);
            cartRepository.save(cart);
            customerRepository.save(customer);

            return new PurchaseResponse(orderTrackingNumber);
        } catch (Exception e) {
            return new PurchaseResponse("Error placing order: " + e.getMessage());
        }
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
