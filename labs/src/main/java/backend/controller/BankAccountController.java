package backend.controller;

import backend.domain.MoneyTransferService;

/**
 * Controller layer (user interface). Does not reuse the domain BankAccount class directly for input.
 */
public class BankAccountController {
    private final MoneyTransferService transferService;

    public BankAccountController(MoneyTransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Initiates a money transfer between accounts.
     * Returns a simple status message.
     */
    public String moneyTransfer(String fromAccountNumber, String toAccountNumber, double amount) {
        try {
            transferService.transfer(fromAccountNumber, toAccountNumber, amount);
            return "transfer successful";
        } catch (IllegalArgumentException e) {
            return "transfer failed: " + e.getMessage();
        } catch (Exception e) {
            return "transfer failed: unexpected error";
        }
    }
}
