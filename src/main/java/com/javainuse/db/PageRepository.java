package com.javainuse.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javainuse.model.Page;

public interface PageRepository
extends JpaRepository<Page,Integer>{
	
	/*
	 * @Query("SELECT p fom Page p WHERE p.id !=:id and p.slug = :slug") Page
	 * findBySlug(int id,String slug);
	 */
	 

	Page findBySlug(String slug);
	Page findBySlugAndIdNot(String slug,int id); 
}
