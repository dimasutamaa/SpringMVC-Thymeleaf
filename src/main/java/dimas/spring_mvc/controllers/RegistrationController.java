package dimas.spring_mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dimas.spring_mvc.entities.User;
import dimas.spring_mvc.services.UserService;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result,  RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "register";
		}
		
		userService.save(user);
		redirectAttributes.addFlashAttribute("message", "Registration success!");
		return "redirect:/login";
	}
	
}
