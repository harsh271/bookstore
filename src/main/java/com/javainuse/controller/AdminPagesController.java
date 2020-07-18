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

import com.javainuse.db.PageRepository;
import com.javainuse.model.Page;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {
	@Autowired
	private PageRepository pageRepo;

	/*
	 * public AdminPagesController(PageRepository pageRepo) {
	 * this.pageRepo=pageRepo; }
	 */

	@GetMapping
	public String index(Model model) {
		List<Page> pages = pageRepo.findAll();
		model.addAttribute("pages", pages);
		return "admin/pages/index";
	}

	@GetMapping("/add")
	public String add(@ModelAttribute Page page) {
		//model.addAttribute("page", new Page());
		return "admin/pages/add";
	}
	@PostMapping("/add")
	public String add(@Valid Page page, BindingResult bindginresult, RedirectAttributes redirectattributes,
			Model model) {
		if (bindginresult.hasErrors()) {
			return "admin/pages/add";
		}
		redirectattributes.addFlashAttribute("message", "Page added");
		redirectattributes.addFlashAttribute("alertClass", "alert-success");
		String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-")
				: page.getSlug().toLowerCase().replace(" ", "-");
		Page slugExists = pageRepo.findBySlug(slug);
		
		if (slugExists != null) {
			redirectattributes.addFlashAttribute("message", "Slug exists,choose another");
			redirectattributes.addFlashAttribute("alertClass", "alert-danger");
			redirectattributes.addFlashAttribute("page", page);
		} else {
			page.setSlug(slug);
			page.setSorting(100);
			pageRepo.save(page);
		}
		return "redirect:/admin/pages/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id,Model model) {
		Page page=pageRepo.getOne(id);
		model.addAttribute("page",page);
		return "admin/pages/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Page page, BindingResult bindginresult, RedirectAttributes redirectattributes,
			Model model) {
		Page pageCurrent =  pageRepo.getOne(page.getId());
		if (bindginresult.hasErrors()) {
			model.addAttribute("pageTitle", pageCurrent.getTitle());
			return "admin/pages/edit";
		}
		redirectattributes.addFlashAttribute("message", "Page ediited");
		redirectattributes.addFlashAttribute("alertClass", "alert-success");
		String slug = page.getSlug()== "" ? page.getTitle().toLowerCase().replace("", "-")
				: page.getSlug().toLowerCase().replace("", "-");
		Page slugExists = pageRepo.findBySlugAndIdNot(slug,page.getId());
		//Page slugExists = pageRepo.findBySlug(page.getId(),slug);
		
		if (slugExists != null) {
			redirectattributes.addFlashAttribute("message", "Slug exists,choose another");
			redirectattributes.addFlashAttribute("alertClass", "alert-danger");
			redirectattributes.addFlashAttribute("page", page);
		} else {
			page.setSlug(slug);
			pageRepo.save(page);
		}
		return "redirect:/admin/pages/edit/"+page.getId();
	}
	@GetMapping("/delete/{id}")
	public String edit(@PathVariable int id,RedirectAttributes redirectattributes) {
		pageRepo.deleteById(id);
		redirectattributes.addFlashAttribute("message", "Page Deleted");
		redirectattributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/pages";
	}
	
}
