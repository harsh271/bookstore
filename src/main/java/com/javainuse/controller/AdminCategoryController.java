package com.javainuse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javainuse.db.CategoryRepository;
import com.javainuse.model.Category;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	@Autowired
	private CategoryRepository ceRepository;
	@GetMapping
	public String index(Model model) {
		List<Category> categories =ceRepository.findAll();
		model.addAttribute("categories",categories);
		return "admin/categories/index";
		
	}

}