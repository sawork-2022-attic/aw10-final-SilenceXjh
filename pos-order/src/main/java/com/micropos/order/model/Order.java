package com.micropos.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    
    private int orderId;

    private List<Item> items;

    public Order(int orderId, List<Item> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Item> getItems() {
        return items;
    }
}