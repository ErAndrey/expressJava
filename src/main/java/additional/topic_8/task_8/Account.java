package additional.topic_8.task_8;

public final class Account {
    private double balance;

    public void deposit(double amount) {
        if (amount <= 0) throw new InvalidChangeBalanceException("Incorrect deposit value");
        this.balance += amount;
        System.out.println("Пополнение: " + amount + "₽");
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > this.balance) throw new InvalidChangeBalanceException("Incorrect withdraw value");
        this.balance -= amount;
        System.out.println("Списание: " + amount + "₽");
    }
}
