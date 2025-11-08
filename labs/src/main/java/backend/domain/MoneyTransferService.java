package backend.domain;

import backend.port.BankAccountRepository;


public class MoneyTransferService {
    private final BankAccountRepository repository;

    public MoneyTransferService(BankAccountRepository repository) {
        this.repository = repository;
    }
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

        if (from.getBalance() < amount) throw new IllegalArgumentException("insufficient funds");

        from.withdraw(amount);
        to.deposit(amount);

        repository.updateAccount(from);
        repository.updateAccount(to);
    }
}
