package com.micropos.delivery.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.Item;
import com.micropos.delivery.model.Order;
import com.micropos.delivery.model.Status;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.DeliveryDto;
import com.micropos.dto.OrderDto;
import com.micropos.dto.ProductDto;

import org.mapstruct.Mapper;

@Mapper
public class DeliveryMapper {
    
    public Order toOrder(OrderDto orderDto) {
        return new Order(orderDto.getOrderId(), toItems(orderDto.getItems()));
    }

    public OrderDto toOrderDto(Order order) {
        return new OrderDto().orderId(order.getOrderId()).items(toItemDtos(order.getItems()));
    }

    public List<CartItemDto> toItemDtos(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        List<CartItemDto> list = new ArrayList<>();
        for (Item item : items) {
            list.add(toItemDto(item));
        }

        return list;
    }

    public List<Item> toItems(List<CartItemDto> itemDtos) {
        if (itemDtos == null || itemDtos.isEmpty()) {
            return null;
        }
        List<Item> list = new ArrayList<>(itemDtos.size());
        for (CartItemDto itemDto : itemDtos) {
            list.add(toItem(itemDto));
        }

        return list;
    }

    public CartItemDto toItemDto(Item item) {

        return new CartItemDto().quantity(item.getQuantity())
                .product(getProductDto(item));
    }

    public Item toItem(CartItemDto itemDto) {
        return new Item(itemDto.getProduct().getId(), itemDto.getProduct().getName(), 
        itemDto.getProduct().getPrice(), itemDto.getQuantity());
    }

    public ProductDto getProductDto(Item item) {
        return new ProductDto().id(item.getProductId())
                .name(item.getName())
                .price(item.getPrice());
    }

    public DeliveryDto toDeliveryDto(Delivery delivery) {
        DeliveryDto res = new DeliveryDto().deliveryId(delivery.getId()).order(toOrderDto(delivery.getOrder()));
        if(delivery.getStatus() == Status.DELIVERING) {
            res.setStatus("DELIVERING");
        }
        else {
            res.setStatus("REACHED");
        }
        return res;
    }

    public Delivery toDelivery(DeliveryDto deliverydto) {
        Delivery res = new Delivery(deliverydto.getDeliveryId(), toOrder(deliverydto.getOrder()));
        if(deliverydto.getStatus().equals("DELIVERING")) {
            res.setStatus(Status.DELIVERING);
        }
        else {
            res.setStatus(Status.REACHED);
        }
        return res;
    }

    public List<Delivery> toDeliveries(List<DeliveryDto> dtos) {
        List<Delivery> deliveries = new ArrayList<>();
        for(DeliveryDto dto : dtos) {
            deliveries.add(toDelivery(dto));
        }
        return deliveries;
    }

    public List<DeliveryDto> toDeliveryDtos(List<Delivery> deliveries) {
        List<DeliveryDto> dtos = new ArrayList<>();
        for(Delivery delivery : deliveries) {
            dtos.add(toDeliveryDto(delivery));
        }
        return dtos;
    }
}