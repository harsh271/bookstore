package com.javainuse.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javainuse.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

	Product findBySlug(String slug);

}
