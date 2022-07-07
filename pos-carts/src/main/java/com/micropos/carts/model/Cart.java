package com.micropos.carts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private int cartId;
    
    private List<Item> curItems = new ArrayList<>();

    public Cart(int cartId) {
        this.cartId = cartId;
    }

    public int getCartId() {
        return cartId;
    }

    public List<Item> getItems() {
        return curItems;
    }

    public void setItems(List<Item> items) {
        curItems.clear();
        for(Item item : items) {
            curItems.add(item);
        }
    }

    public boolean addItem(Item item) {
        return curItems.add(item);
    }

    public double getTotal() {
        double total = 0.0;
        for(Item item : curItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clear() {
        curItems.clear();
    }
}