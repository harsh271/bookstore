package com.javainuse.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javainuse.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
	
	Category findByName(String name);

}
