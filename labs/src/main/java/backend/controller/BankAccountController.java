package backend.controller;

import backend.domain.MoneyTransferService;

public class BankAccountController {
    private final MoneyTransferService transferService;

    public BankAccountController(MoneyTransferService transferService) {
        this.transferService = transferService;
    }


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
