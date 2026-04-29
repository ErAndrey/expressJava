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

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println(Utils.toError("System: ") + "Сумма пополнения должна быть положительной!");
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println(Utils.toError("System: ") + "Сумма вывода должна быть положительной!");
            return false;
        }

        if (this.balance < amount) {
            System.out.println(Utils.toError("System: ") + "Недостаточно средств! Баланс: " + Utils.formatCurrency(this.balance));
            return false;
        }

        this.balance -= amount;
        return true;
    }
}
