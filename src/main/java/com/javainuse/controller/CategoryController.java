package com.javainuse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javainuse.db.CategoryRepository;
import com.javainuse.db.ProductRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private ProductRepository prodcutRepo;

}
