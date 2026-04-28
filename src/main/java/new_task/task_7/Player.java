package new_task.task_7;

public class Player {
    private final String name;
    private double balance;

    public Player(String name) {
        this.name = name;
        this.balance = 0;
    }

    public String getName() {return this.name;}
    public double getBalance() {return this.balance;}

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return;
        }
        System.out.println("Сумма для пополнения невалидна");
    }

    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return;
        }
        System.out.println("Недостаточно денег на счете");
    }
}
