package com.spark.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spark.lms.model.Book;
import com.spark.lms.model.Category;
import com.spark.lms.service.BookService;
import com.spark.lms.service.CategoryService;

@Controller
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute(name = "categories")
	public List<Category> categories() {
		return categoryService.getAllBySort();
	}
	
	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String showBooksPage(Model model) {
		model.addAttribute("books", bookService.getAll());
		return "/book/list";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBookPage(Model model) {
		model.addAttribute("book", new Book());
		return "/book/form";
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editBookPage(@PathVariable(name = "id") Long id,  Model model) {
		Book book = bookService.get(id);
		if( book != null ) {
			model.addAttribute("book", book);
			return "/book/form";
		} else {
			return "redirect:/book/add";
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBook(@Valid Book book, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
		if( bindingResult.hasErrors() ) {
			return "/book/form";
		}
		
		if( book.getId() == null ) {
			if( bookService.getByTag(book.getTag()) != null ) {
				bindingResult.rejectValue("tag", "tag", "Tag already exists");
				return "/book/form";
			} else {
				bookService.addNew(book);
				redirectAttributes.addFlashAttribute("successMsg", "'" + book.getTitle() + "' is added as a new Book.");
				return "redirect:/book/add";
			}
		} else {
			Book updatedBook = bookService.save(book);
			redirectAttributes.addFlashAttribute("successMsg", "Changes for '" + book.getTitle() + "' are saved successfully. ");
			return "redirect:/book/edit/"+updatedBook.getId();
		}
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removeBook(@PathVariable(name = "id") Long id, Model model) {
		Book book = bookService.get( id );
		if( book != null ) {
			if( bookService.hasUsage(book) ) {
				model.addAttribute("bookInUse", true);
				return showBooksPage(model);
			} else {
				bookService.delete(id);
			}
		}
		return "redirect:/book/list";
	}
}
