package backend.domain;

import backend.port.BankAccountRepository;

/**
 * Domain service that contains the money transfer business logic.
 * It depends only on the port interface and does not access persistence directly.
 */
public class MoneyTransferService {
    private final BankAccountRepository repository;

    public MoneyTransferService(BankAccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Transfer amount from one account to another.
     * Throws IllegalArgumentException for validation errors.
     */
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        if (fromAccountNumber == null || toAccountNumber == null) {
            throw new IllegalArgumentException("account numbers must be provided");
        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("cannot transfer to same account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("transfer amount must be positive");
        }

        BankAccount from = repository.findAccountById(fromAccountNumber);
        BankAccount to = repository.findAccountById(toAccountNumber);

        if (from == null) throw new IllegalArgumentException("sender account not found");
        if (to == null) throw new IllegalArgumentException("recipient account not found");

        // Business rule: check sufficient funds
        if (from.getBalance() < amount) throw new IllegalArgumentException("insufficient funds");

        // Perform transfer on domain objects
        from.withdraw(amount);
        to.deposit(amount);

        // Persist changes via the port
        repository.updateAccount(from);
        repository.updateAccount(to);
    }
}
