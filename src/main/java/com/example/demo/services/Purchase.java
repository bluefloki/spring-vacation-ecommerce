package com.example.demo.services;

import com.example.demo.entities.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;
}
