package backend;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.domain.BankAccount;
import backend.exception.InsufficientFundsException;
import backend.persistence.DatabaseAdapter;
import backend.service.BankingService;

public class BankingServiceTest {

    private DatabaseAdapter adapter;
    private BankingService service;

    @BeforeEach
    void setUp() {
        adapter = new DatabaseAdapter();
        service = new BankingService(adapter);
    }

    @AfterEach
    void tearDown() {
        adapter.close();
    }

    @Test
    void createAccountStoresInitialBalance() {
        String accNum = "ACC-" + java.util.UUID.randomUUID();
        BankAccount acc = service.createAccount(accNum, "Alice", 500.0);
        assertEquals(500.0, acc.getBalance(), 0.001);
        BankAccount loaded = service.getAccount(accNum);
        assertNotNull(loaded);
        assertEquals("Alice", loaded.getAccountHolder());
    }

    @Test
    void transferMovesMoneyBetweenAccounts() {
        String a1 = "ACC-" + java.util.UUID.randomUUID();
        String a2 = "ACC-" + java.util.UUID.randomUUID();
        service.createAccount(a1, "Alice", 1000.0);
        service.createAccount(a2, "Bob", 200.0);
        service.transfer(a1, a2, 300.0);
        assertEquals(700.0, service.getAccount(a1).getBalance(), 0.001);
        assertEquals(500.0, service.getAccount(a2).getBalance(), 0.001);
    }

    @Test
    void transferFailsWhenInsufficientFunds() {
        String a1 = "ACC-" + java.util.UUID.randomUUID();
        String a2 = "ACC-" + java.util.UUID.randomUUID();
        service.createAccount(a1, "Alice", 100.0);
        service.createAccount(a2, "Bob", 50.0);
        assertThrows(InsufficientFundsException.class, () -> service.transfer(a1, a2, 300.0));
        assertEquals(100.0, service.getAccount(a1).getBalance(), 0.001);
        assertEquals(50.0, service.getAccount(a2).getBalance(), 0.001);
    }
}
