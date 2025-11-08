package backend.port;

import backend.domain.BankAccount;


public interface BankAccountRepository {

    BankAccount findAccountById(String accountNumber);

    void createAccount(BankAccount account);

 
    void updateAccount(BankAccount account);
}
