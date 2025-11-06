package backend.domain;

import java.util.Objects;

/**
 * Domain model for a bank account. Independent of persistence.
 */
public class BankAccount {
    private final String accountNumber;
    private final String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = Objects.requireNonNull(accountNumber);
        this.accountHolder = Objects.requireNonNull(accountHolder);
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount < 0) throw new IllegalArgumentException("deposit amount must be >= 0");
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount < 0) throw new IllegalArgumentException("withdraw amount must be >= 0");
        if (amount > balance) throw new IllegalArgumentException("insufficient funds");
        this.balance -= amount;
    }
}
