package backend.service;

import backend.domain.BankAccount;
import backend.exception.AccountNotFoundException;
import backend.exception.InsufficientFundsException;
import backend.port.BankAccountRepository;

public class BankingService {
    private final BankAccountRepository bankAccountRepository;

    public BankingService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createAccount(String accountNumber, String owner, double initialBalance) {
        BankAccount account = new BankAccount(accountNumber, owner, initialBalance);
        bankAccountRepository.createAccount(account);
        return account;
    }

    public BankAccount getAccount(String accountNumber) {
        BankAccount account = bankAccountRepository.findAccountById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        BankAccount sender = getAccount(fromAccountNumber);
        BankAccount recipient = getAccount(toAccountNumber);

        if (sender.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in account: " + fromAccountNumber);
        }

        sender.withdraw(amount);
        recipient.deposit(amount);

        bankAccountRepository.updateAccount(sender);
        bankAccountRepository.updateAccount(recipient);
    }
}