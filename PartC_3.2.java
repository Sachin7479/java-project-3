package com.example;

import javax.persistence.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Entity
@Table(name = "accounts")
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_number")
    private String accountNumber;
    
    @Column(name = "balance")
    private Double balance;
    
    public Account() {}
    
    public Account(String accountNumber, Double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public Double getBalance() {
        return balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}

@Entity
@Table(name = "transactions")
class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "from_account")
    private String fromAccount;
    
    @Column(name = "to_account")
    private String toAccount;
    
    @Column(name = "amount")
    private Double amount;
    
    public BankTransaction() {}
    
    public BankTransaction(String fromAccount, String toAccount, Double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFromAccount() {
        return fromAccount;
    }
    
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }
    
    public String getToAccount() {
        return toAccount;
    }
    
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

class TransactionService {
    private SessionFactory sessionFactory;
    
    public TransactionService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public void transferMoney(String fromAccountNum, String toAccountNum, Double amount) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        try {
            List<Account> fromAccounts = session.createQuery("FROM Account WHERE accountNumber = :accNum", Account.class)
                .setParameter("accNum", fromAccountNum)
                .getResultList();
            
            List<Account> toAccounts = session.createQuery("FROM Account WHERE accountNumber = :accNum", Account.class)
                .setParameter("accNum", toAccountNum)
                .getResultList();
            
            if (!fromAccounts.isEmpty() && !toAccounts.isEmpty()) {
                Account fromAccount = fromAccounts.get(0);
                Account toAccount = toAccounts.get(0);
                
                if (fromAccount.getBalance() >= amount) {
                    fromAccount.setBalance(fromAccount.getBalance() - amount);
                    toAccount.setBalance(toAccount.getBalance() + amount);
                    
                    session.update(fromAccount);
                    session.update(toAccount);
                    
                    BankTransaction transaction = new BankTransaction(fromAccountNum, toAccountNum, amount);
                    session.save(transaction);
                    
                    session.getTransaction().commit();
                    System.out.println("Transfer successful: " + amount + " from " + fromAccountNum + " to " + toAccountNum);
                } else {
                    session.getTransaction().rollback();
                    System.out.println("Insufficient balance in account " + fromAccountNum);
                }
            } else {
                session.getTransaction().rollback();
                System.out.println("One or both accounts not found");
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Transaction failed and rolled back: " + e.getMessage());
        }
    }
}

public class PartC {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Account.class);
        config.addAnnotatedClass(BankTransaction.class);
        
        SessionFactory sessionFactory = config.buildSessionFactory();
        
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Account account1 = new Account("ACC001", 5000.0);
        Account account2 = new Account("ACC002", 3000.0);
        
        session.save(account1);
        session.save(account2);
        
        session.getTransaction().commit();
        session.close();
        
        TransactionService service = new TransactionService(sessionFactory);
        service.transferMoney("ACC001", "ACC002", 1000.0);
        service.transferMoney("ACC001", "ACC002", 6000.0);
        
        sessionFactory.close();
    }
}
