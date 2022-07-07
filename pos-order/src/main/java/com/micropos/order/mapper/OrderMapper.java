package com.micropos.order.mapper;

import java.util.ArrayList;
import java.util.List;

import com.micropos.dto.CartItemDto;
import com.micropos.dto.OrderDto;
import com.micropos.dto.ProductDto;
import com.micropos.order.model.Item;
import com.micropos.order.model.Order;

import org.mapstruct.Mapper;

@Mapper
public class OrderMapper {
    
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
}