package dimas.spring_mvc.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Pattern(regexp = "^[0-9]+$", message = "Must contain only digits")
	@Size(min = 10, max = 10, message = "Account number must contain 10 digits")
	private String accountNumber;
	
	@Min(10000)
	private Double amount;
	private String news;
	private String type;
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name = "bank_account_id", nullable = false)
	private BankAccount bankAccount;
	
	public Transaction() {
		this.timestamp = LocalDateTime.now();
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getAccountNumber() { return accountNumber; }
	public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
	public Double getAmount() { return amount; }
	public void setAmount(Double amount) { this.amount = amount; }
	public String getNews() { return news; }
	public void setNews(String news) { this.news = news; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public LocalDateTime getTimestamp() { return timestamp; }
	public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
	public BankAccount getBankAccount() { return bankAccount;	}
	public void setBankAccount(BankAccount bankAccount) { this.bankAccount = bankAccount; }
	
}
