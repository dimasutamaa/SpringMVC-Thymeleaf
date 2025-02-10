package dimas.spring_mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dimas.spring_mvc.entities.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{
	boolean existsByNik(String nik);
	BankAccount findByAccountNumber(String accountNumber);
}
