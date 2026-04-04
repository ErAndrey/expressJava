package practice_2;

public class BankAccount {
    String owner;
    double balance;

    public BankAccount(String owner) {
        this.owner = owner;
    }

    public void setOwner(String newOwner) {
        this.owner = newOwner;
    }

    public String getOwner() {
        return this.owner;
    }

    public double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Ошибка: Укажите сумму больше 0");
            return;
        }
        this.balance += amount;
        System.out.println("Начислено на счет: " + amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Ошибка: Укажите сумму больше 0");
            return;
        }
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Списано со счета: " + amount);
        } else {
            System.out.println("Ошибка: Недостаточно средств");
        }
    }

    public void printBalance() {
        System.out.println("Ваш баланс : " + getBalance());
    }
}
