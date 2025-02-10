package dimas.spring_mvc.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dimas.spring_mvc.entities.BankAccount;
import dimas.spring_mvc.entities.User;
import dimas.spring_mvc.services.BankAccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;
	
	@GetMapping("/rekening")
	public String showBukaRekeningPage(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if (Objects.isNull(currentUser)) {
			redirectAttributes.addFlashAttribute("message", "You are not logged in. Please re-login to access");
			return "redirect:/login";
		}
		
		model.addAttribute("bankAccount", new BankAccount());
		return "buka-rekening";
	}
	
	@PostMapping("/rekening")
	public String openBankAccount(@RequestParam("nik") String nik, @Valid @ModelAttribute BankAccount bankAccount, 
			BindingResult result, RedirectAttributes redirectAttributes, HttpSession session) {
		
		if (result.hasErrors()) {
			return "buka-rekening";
		}
		
		if (bankAccountService.checkIfNikExists(nik)) {
			redirectAttributes.addFlashAttribute("message", "The NIK is already registered!");
			return "redirect:/rekening";
		}
		
		bankAccountService.createBankAccount(bankAccount);
		redirectAttributes.addFlashAttribute("message", "Successfully opened a bank account!");
		return "redirect:/dashboard";
	}
	
	@GetMapping("/tutup-rekening")
	public String showTutupRekeningPage(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if (Objects.isNull(currentUser)) {
			redirectAttributes.addFlashAttribute("message", "You are not logged in. Please re-login to access");
			return "redirect:/login";
		}
		
		if (!model.containsAttribute("bankAccount")) {
			model.addAttribute("bankAccount", null);
		}

		return "tutup-rekening";
	}
	
	@PostMapping("/tutup-rekening")
	public String searchAccount(@RequestParam("accountNumber") String accountNumber, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		BankAccount bankAccount = bankAccountService.findByAccountNumber(accountNumber);
		
		if (Objects.isNull(currentUser)) {
			redirectAttributes.addFlashAttribute("message", "You are not logged in. Please re-login to access");
			return "redirect:/login";
		}
		
		if (Objects.isNull(bankAccount)) {
			redirectAttributes.addFlashAttribute("message", "Bank account not found!");
			return "redirect:/tutup-rekening";
		} else if (!bankAccount.isActive()) {
			redirectAttributes.addFlashAttribute("message", "Bank account is not active!");
			return "redirect:/tutup-rekening";
		}
		
		model.addAttribute("bankAccount", bankAccount);
		return "tutup-rekening";
	}
	
	@PostMapping("/proses-tutup-rekening")
	public String closeBankAccount(@RequestParam("accountNumber") String accountNumber, RedirectAttributes redirectAttributes) {
		BankAccount bankAccount = bankAccountService.findByAccountNumber(accountNumber);
		
		bankAccountService.closeBankAccount(bankAccount);
		
		redirectAttributes.addFlashAttribute("message", "Successfully closed the bank account!");
		return "redirect:/tutup-rekening";
	}
}
