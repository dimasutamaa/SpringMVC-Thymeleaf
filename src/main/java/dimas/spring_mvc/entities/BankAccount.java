package dimas.spring_mvc.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="bankaccounts")
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String accountNumber;
	
	@Pattern(regexp = "^[0-9]+$", message = "Invalid NIK format")
	private String nik;
	
	@Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
	private String name;
	
	@NotBlank(message = "Please provide your address")
	private String address;
	
	@Pattern(regexp = "^[0-9]+$", message = "Must contain only number")
	private String phone;
	
	@Min(100000)
	private Double balance;
	
	private String status;
	
	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public BankAccount() {
		this.accountNumber = generateUniqueAccountNumber();
		this.status = "active";
	}
	
	private String generateUniqueAccountNumber() {
		Random rand = new Random();
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 10; i++) {
			sb.append(rand.nextInt(10));
		}
		
		return sb.toString();
	}
	
	public Boolean isActive() {
		return this.status.equals("active") ? true : false;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getAccountNumber() { return accountNumber;}
	public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
	public String getNik() { return nik; }
	public void setNik(String nik) { this.nik = nik; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public Double getBalance() { return balance; }
	public void setBalance(Double balance) { this.balance = balance; }
	public List<Transaction> getTransactions() { return transactions; }
	public void setTransactions(List<Transaction> transactions) { this.transactions = transactions;}
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }

}
