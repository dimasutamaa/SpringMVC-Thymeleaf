package dimas.spring_mvc.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
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

import com.lowagie.text.DocumentException;

import dimas.spring_mvc.entities.BankAccount;
import dimas.spring_mvc.entities.Transaction;
import dimas.spring_mvc.entities.User;
import dimas.spring_mvc.services.BankAccountService;
import dimas.spring_mvc.services.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@GetMapping("/setor-tunai")
	public String showSetorTunaiPage(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if (Objects.isNull(currentUser)) {
			redirectAttributes.addFlashAttribute("message", "You are not logged in. Please re-login to access");
			return "redirect:/login";
		}
		
		model.addAttribute("transaction", new Transaction());
		
		return "setor-tunai";
	}
	
	@PostMapping("/setor-tunai")
	public String deposit(@RequestParam("accountNumber") String accountNumber, @Valid @ModelAttribute Transaction transaction, 
			BindingResult result, RedirectAttributes redirectAttributes, HttpSession session) {
		
		BankAccount bankAccount = bankAccountService.findByAccountNumber(accountNumber);
		
		if (result.hasErrors()) {
			return "setor-tunai";
		}

		if (Objects.isNull(bankAccount)) {
			redirectAttributes.addFlashAttribute("message", "Bank account is not found!");
			return "redirect:/setor-tunai";
		}
		
		if (!bankAccount.isActive()) {
			redirectAttributes.addFlashAttribute("message", "Bank account is not active!");
			return "redirect:/setor-tunai";
		}
		
		transactionService.deposit(bankAccount, transaction);
		redirectAttributes.addFlashAttribute("message", "Successfully deposited the money!");
		return "redirect:/setor-tunai";
	}
	
	@GetMapping("/tarik-tunai")
	public String showTarikTunaiPage(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if (Objects.isNull(currentUser)) {
			redirectAttributes.addFlashAttribute("message", "You are not logged in. Please re-login to access");
			return "redirect:/login";
		}
		
		model.addAttribute("transaction", new Transaction());
		return "tarik-tunai";
	}
	
	@PostMapping("/tarik-tunai")
	public String withdraw(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") Double amount, 
			@Valid @ModelAttribute Transaction transaction, BindingResult result, 
			RedirectAttributes redirectAttributes, HttpSession session) {
		
		BankAccount bankAccount = bankAccountService.findByAccountNumber(accountNumber);
		
		if (result.hasErrors()) {
			return "tarik-tunai";
		}

		if (Objects.isNull(bankAccount)) {
			redirectAttributes.addFlashAttribute("message", "Bank account not found!");
			return "redirect:/tarik-tunai";
		}
		
		if (!bankAccount.isActive()) {
			redirectAttributes.addFlashAttribute("message", "Bank account is not active!");
			return "redirect:/tarik-tunai";
		}
		
		transactionService.withdraw(bankAccount, transaction);
		redirectAttributes.addFlashAttribute("message", "Successfully withdraw the money!");
		return "redirect:/tarik-tunai";
	}
	
	@GetMapping("/mutasi")
	public String showMutasiPage(Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if (Objects.isNull(currentUser)) {
			redirectAttributes.addFlashAttribute("message", "You are not logged in. Please re-login to access");
			return "redirect:/login";
		}
		
		if (!model.containsAttribute("transactions")) {
			model.addAttribute("transactions", Collections.emptyList());
		}
		
		return "mutasi";
	}
	
	@PostMapping("/mutasi")
	public String getMutasi(@RequestParam("accountNumber") String accountNumber, Model model, 
			RedirectAttributes redirectAttributes, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		BankAccount bankAccount = bankAccountService.findByAccountNumber(accountNumber);
		
		if (Objects.isNull(currentUser)) {
			redirectAttributes.addFlashAttribute("message", "You are not logged in. Please re-login to access");
			return "redirect:/login";
		}
		
		if (Objects.isNull(bankAccount)) {
			redirectAttributes.addFlashAttribute("message", "Bank account not found!");
			return "redirect:/mutasi";
		}
		
		List<Transaction> transactions = transactionService.getTransactionsByBankAccountAccountNumber(accountNumber);
		model.addAttribute("transactions", transactions);
		session.setAttribute("searchedAccountNumber", accountNumber);
		return "mutasi";
	}
	
	@GetMapping("/download-mutasi")
	public void generatePdfFile(HttpServletResponse response, HttpSession session) throws DocumentException, IOException {
		transactionService.downloadMutasi(response, session);
	}
	
}
