package dimas.spring_mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dimas.spring_mvc.entities.BankAccount;
import dimas.spring_mvc.repositories.BankAccountRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService{

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	public BankAccount createBankAccount(BankAccount bankAccount) {
		return bankAccountRepository.save(bankAccount);
	}

	@Override
	public boolean checkIfNikExists(String nik) {
		return bankAccountRepository.existsByNik(nik);
	}

	@Override
	public BankAccount closeBankAccount(BankAccount bankAccount) {
		bankAccount.setStatus("closed");
		return bankAccountRepository.save(bankAccount);
	}

	@Override
	public BankAccount findByAccountNumber(String accountNumber) {
		return bankAccountRepository.findByAccountNumber(accountNumber);
	}
	
}
