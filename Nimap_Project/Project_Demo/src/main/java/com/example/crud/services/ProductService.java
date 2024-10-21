package com.example.crud.services;

import com.example.crud.entities.Product;
import com.example.crud.repositories.ProductRepository;
import com.example.crud.repositories.CategoryRepository;
import com.example.crud.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct, Long categoryId) {
        Product product = getProductById(id);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
