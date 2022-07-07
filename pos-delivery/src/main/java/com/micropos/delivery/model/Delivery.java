package com.micropos.delivery.model;

public class Delivery {
    
    private int deliveryId;

    private Order order;

    private Status status = Status.DELIVERING;

    public Delivery(int id, Order order) {
        this.deliveryId = id;
        this.order = order;
    }

    public int getId() {
        return this.deliveryId;
    }

    public Order getOrder() {
        return this.order;
    }

    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
}