package backend.port;

import backend.domain.BankAccount;

/**
 * Port interface between domain logic and persistence/adapters.
 */
public interface BankAccountRepository {
    /**
     * Find account by id (account number). Returns null if not found.
     */
    BankAccount findAccountById(String accountNumber);

    /**
     * Persist a new account.
     */
    void createAccount(BankAccount account);

    /**
     * Update an existing account.
     */
    void updateAccount(BankAccount account);
}
