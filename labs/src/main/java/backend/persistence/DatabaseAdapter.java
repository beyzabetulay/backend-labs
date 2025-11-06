package backend.persistence;

import java.util.HashMap;
import java.util.Map;

import backend.domain.BankAccount;
import backend.port.BankAccountRepository;

/**
 * Simple in-memory implementation of BankAccountRepository using HashMap
 */
public class DatabaseAdapter implements BankAccountRepository {
    private final Map<String, BankAccount> accounts;

    public DatabaseAdapter() {
        this.accounts = new HashMap<>();
    }

    @Override
    public BankAccount findAccountById(String accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public void createAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public void updateAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }
}
