package additional.n5_oop_principles.task_1;

public interface Transaction {
    boolean deposit(double amount);

    void withdraw(double amount);
}
