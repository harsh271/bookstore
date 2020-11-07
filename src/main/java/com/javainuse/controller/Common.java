package com.javainuse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.javainuse.db.CategoryRepository;
import com.javainuse.db.PageRepository;
import com.javainuse.model.Category;
import com.javainuse.model.Page;

@ControllerAdvice
public class Common {
	@Autowired
	private PageRepository  pageRepo;
	@Autowired
	private CategoryRepository categoryRepo;
	@ModelAttribute
	public void sharedData(Model model) {
		List<Page> pages=pageRepo.findAllByOrderBySortingAsc();
		List<Category> categories=categoryRepo.findAll();
		model.addAttribute("cpages", pages);
		model.addAttribute("ccategories",categories);
	}
}
