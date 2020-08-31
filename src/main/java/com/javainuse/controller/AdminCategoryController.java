package com.javainuse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javainuse.db.CategoryRepository;
import com.javainuse.model.Category;
import com.javainuse.model.Page;

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
	@ModelAttribute("category")
	public Category getCategory() {
		return new Category();
	}
	
	@GetMapping("/add")
	public String add() {
		return "admin/categories/add";
	}
	@PostMapping("/add")
	public String add(@Valid Category category, BindingResult bindginresult, RedirectAttributes redirectattributes,
			Model model) {
		if (bindginresult.hasErrors()) {
			return "admin/categories/add";
		}
		/*
		 * redirectattributes.addFlashAttribute("message", "Category added");
		 * redirectattributes.addFlashAttribute("alertClass", "alert-success");
		 */
		 String slug = category.getName().toLowerCase().replace(" ", "-");
		Category categoryExists=ceRepository.findByName(slug);
		
		if (categoryExists != null) {
			redirectattributes.addFlashAttribute("message", "Category exists,choose another");
			redirectattributes.addFlashAttribute("alertClass", "alert-danger");
			redirectattributes.addFlashAttribute("categoryInfo", category);
		} else {
		category.setSlug(slug);
		category.setSorting(100);
			ceRepository.save(category);
		}
		return "redirect:/admin/categories/add";
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id,Model model) {
		Category category=ceRepository.getOne(id);
		model.addAttribute("category",category);
		return "admin/categories/edit";
	}
	@PostMapping("/edit")
	public String edit(@Valid Category category, BindingResult bindginresult, RedirectAttributes redirectattributes,
			Model model) {
		Category categoyCurrent =  ceRepository.getOne(category.getId());
		if (bindginresult.hasErrors()) {
			model.addAttribute("categoryName", categoyCurrent.getName());
			return "admin/categories/edit";
		}
		redirectattributes.addFlashAttribute("message", "Category ediited");
		redirectattributes.addFlashAttribute("alertClass", "alert-success");
		String slug = category.getName().toLowerCase().replace("", "-");
		Category categoryExists = ceRepository.findByName(category.getName());
		//Page slugExists = pageRepo.findBySlug(page.getId(),slug);
		
		if (categoryExists != null) {
			redirectattributes.addFlashAttribute("message", "Category exists,choose another");
			redirectattributes.addFlashAttribute("alertClass", "alert-danger");
		} else {
			category.setSlug(slug);
			ceRepository.save(category);
		}
		return "redirect:/admin/categories/edit/"+category.getId();
	}
	@GetMapping("/delete/{id}")
	public String edit(@PathVariable int id,RedirectAttributes redirectattributes) {
		ceRepository.deleteById(id);
		redirectattributes.addFlashAttribute("message", "Category Deleted");
		redirectattributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/categories";
	}

}
