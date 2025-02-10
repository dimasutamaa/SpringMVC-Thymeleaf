package dimas.spring_mvc.services;

import dimas.spring_mvc.entities.BankAccount;

public interface BankAccountService {

	BankAccount createBankAccount(BankAccount bankAccount);
	boolean checkIfNikExists(String nik);
	BankAccount closeBankAccount(BankAccount bankAccount);
	BankAccount findByAccountNumber(String accountNumber);
}
