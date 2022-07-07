package com.micropos.products.service;

import com.micropos.products.model.Product;
import com.micropos.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import net.sf.ehcache.CacheManager;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    //@Autowired
    //private CacheManager cacheManager;

    public ProductServiceImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "productCache", key = "'allproducts'")
    public Flux<Product> products() {
        //System.out.println(cacheManager.getClass());
        return productRepository.allProducts();
    }

    @Override
    @Cacheable(value = "productCache", key = "#id")
    public Mono<Product> getProduct(String id) {
        return productRepository.findProduct(id);
    }

}
