package dimas.spring_mvc.services;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;

import dimas.spring_mvc.entities.BankAccount;
import dimas.spring_mvc.entities.Transaction;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface TransactionService {

	Transaction deposit(BankAccount bankAccount, Transaction transaction);
	Transaction withdraw(BankAccount bankAccount, Transaction transaction);
	List<Transaction> getTransactionsByBankAccountAccountNumber(String accountNumber);
	void downloadMutasi(HttpServletResponse response, HttpSession session) throws DocumentException, IOException;
	
}
