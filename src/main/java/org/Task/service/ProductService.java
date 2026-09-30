package org.Task.service;

import org.Task.exception.ProductNotFoundException;
import org.Task.model.Product;
import org.Task.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(long productId) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
        return product;
    }


    public Product addProduct(Product product) {
        if (productRepository.findBySku(product.getSku()) != null) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " already exists.");
        }
        return productRepository.save(product);
    }

    public void restock(long productId,int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }
    @Transactional
    public void changePrice(long productId, double newPrice) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
        product.setPrice(newPrice);
        productRepository.save(product);
    }

}
