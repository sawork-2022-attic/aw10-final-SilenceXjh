package com.micropos.carts.mapper;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper
public interface CartMapper {
    default List<CartDto> toCartDtos(List<Cart> carts) {
        if(carts == null || carts.isEmpty()) {
            return null;
        }
        List<CartDto> list = new ArrayList<>();
        for(Cart cart : carts) {
            list.add(toCartDto(cart));
        }
        return list;
    }

    default List<Cart> toCarts(List<CartDto> cartDtos) {
        if(cartDtos == null || cartDtos.isEmpty()) {
            return null;
        }
        List<Cart> list = new ArrayList<>();
        for(CartDto c : cartDtos) {
            list.add(toCart(c));
        }
        return list;
    }

    default Cart toCart(CartDto cartDto) {
        Cart cart = new Cart(cartDto.getId());
        cart.setItems(toItems(cartDto.getItems()));
        return cart;
    }

    default CartDto toCartDto(Cart cart) {
        return new CartDto().id(cart.getCartId())
                            .items(toItemDtos(cart.getItems()));
    }

    default List<CartItemDto> toItemDtos(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        List<CartItemDto> list = new ArrayList<>();
        for (Item item : items) {
            list.add(toItemDto(item));
        }

        return list;
    }

    default List<Item> toItems(List<CartItemDto> itemDtos) {
        if (itemDtos == null || itemDtos.isEmpty()) {
            return null;
        }
        List<Item> list = new ArrayList<>(itemDtos.size());
        for (CartItemDto itemDto : itemDtos) {
            list.add(toItem(itemDto));
        }

        return list;
    }

    default CartItemDto toItemDto(Item item) {

        return new CartItemDto().quantity(item.getQuantity())
                .product(getProductDto(item));
    }

    default Item toItem(CartItemDto itemDto) {
        return new Item(itemDto.getProduct().getId(), itemDto.getProduct().getName(), 
        itemDto.getProduct().getPrice(), itemDto.getQuantity());
    }

    default ProductDto getProductDto(Item item) {
        return new ProductDto().id(item.getProductId())
                .name(item.getName())
                .price(item.getPrice());
    }

}
