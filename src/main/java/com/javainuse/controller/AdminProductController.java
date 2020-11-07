package com.javainuse.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javainuse.db.CategoryRepository;
import com.javainuse.db.ProductRepository;
import com.javainuse.model.Category;
import com.javainuse.model.Product;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@GetMapping
	public String index(Model model) {
		List<Product> products=productRepository.findAll();
		model.addAttribute("products", products);
		return "admin/products/index";
	}
	@GetMapping("/add")
	public String add(Product product,Model model) {
		List<Category> categories=categoryRepository.findAll();
		model.addAttribute("categories",categories);
		return "admin/products/add";
	}
	
	@PostMapping("/add")
	public String add(@Valid Product product, BindingResult bindginresult,MultipartFile file, RedirectAttributes redirectattributes,
			Model model) throws IOException {
		
		  if (bindginresult.hasErrors()) { return "admin/categories/add"; }
		 
		boolean fileOK=false;
		byte[] bytes=file.getBytes();
		String filename=file.getName();
	java.nio.file.Path path=Paths.get("src/main/resources/static/media/"+filename);
	if(filename.endsWith("jpg") || filename.endsWith("png")) {
		fileOK=true;
	}	
	redirectattributes.addFlashAttribute("message", "Product added");
		redirectattributes.addFlashAttribute("alertClass", "alert-success");
		String slug = product.getName().toLowerCase().replace(" ", "-");
		Product productExists = productRepository.findBySlug(slug);
		if(!fileOK) {
			redirectattributes.addFlashAttribute("message", "Image must be a jpg or a png");
			redirectattributes.addFlashAttribute("alertClass", "alert-danger");
		}
		else if (productExists != null) {
			redirectattributes.addFlashAttribute("message", "Product exists,choose another");
			redirectattributes.addFlashAttribute("alertClass", "alert-danger");
		} else {
			product.setSlug(slug);
			product.setImage(filename);
			productRepository.save(product);
			Files.write(path, bytes);
		}
		return "redirect:/admin/products/add";
	}
}
