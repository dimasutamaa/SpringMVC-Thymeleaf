package dimas.spring_mvc.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;

import dimas.spring_mvc.entities.BankAccount;
import dimas.spring_mvc.entities.Transaction;
import dimas.spring_mvc.repositories.BankAccountRepository;
import dimas.spring_mvc.repositories.TransactionRepository;
import dimas.spring_mvc.utilities.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Override
	public Transaction deposit(BankAccount bankAccount, Transaction transaction) {
		transaction.setBankAccount(bankAccount);
		
		Double amount = transaction.getAmount();
		Double balance = bankAccount.getBalance();
		
		bankAccount.setBalance(balance + amount);
		bankAccountRepository.save(bankAccount);
		
		transaction.setType("deposit");
		
		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction withdraw(BankAccount bankAccount, Transaction transaction) {
		transaction.setBankAccount(bankAccount);
		
		Double amount = transaction.getAmount();
		Double balance = bankAccount.getBalance();
		
		bankAccount.setBalance(balance - amount);
		bankAccountRepository.save(bankAccount);
		
		transaction.setNews("withdraw");
		transaction.setType("withdraw");
		
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> getTransactionsByBankAccountAccountNumber(String accountNumber) {
		return transactionRepository.findByBankAccountAccountNumber(accountNumber);
	}

	@Override
	public void downloadMutasi(HttpServletResponse response, HttpSession session) throws DocumentException, IOException {
		response.setContentType("application/pdf");
	    
	    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
	    
	    String currentDateTime = dateFormat.format(new Date());
	    String headerkey = "Content-Disposition";
	    String headervalue = "attachment; filename=mutasi" + currentDateTime + ".pdf";
	    
	    response.setHeader(headerkey, headervalue);
	    
	    String accountNumber = (String) session.getAttribute("searchedAccountNumber");
	    
	    List<Transaction> transactions = getTransactionsByBankAccountAccountNumber(accountNumber);
	    
	    PdfGenerator generator = new PdfGenerator();
	    generator.generate(transactions, response);
	}
	
}
