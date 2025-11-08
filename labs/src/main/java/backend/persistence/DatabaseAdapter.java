package backend.persistence;

import backend.domain.BankAccount;
import backend.persistence.entity.AccountEntity;
import backend.port.BankAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseAdapter implements BankAccountRepository, AutoCloseable {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public DatabaseAdapter() {
        this.emf = Persistence.createEntityManagerFactory("bankPU");
        this.em = emf.createEntityManager();
    }

    @Override
    public BankAccount findAccountById(String accountNumber) {
        AccountEntity entity = em.find(AccountEntity.class, accountNumber);
        if (entity == null) return null;
        return new BankAccount(entity.getAccountNumber(), entity.getAccountHolder(), entity.getBalance());
    }

    @Override
    public void createAccount(BankAccount account) {
        em.getTransaction().begin();
        em.persist(new AccountEntity(account.getAccountNumber(), account.getAccountHolder(), account.getBalance()));
        em.getTransaction().commit();
    }

    @Override
    public void updateAccount(BankAccount account) {
        em.getTransaction().begin();
        AccountEntity entity = em.find(AccountEntity.class, account.getAccountNumber());
        if (entity != null) {
            entity.setAccountHolder(account.getAccountHolder());
            entity.setBalance(account.getBalance());
            em.merge(entity);
        } else {
            em.persist(new AccountEntity(account.getAccountNumber(), account.getAccountHolder(), account.getBalance()));
        }
        em.getTransaction().commit();
    }

    @Override
    public void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}
