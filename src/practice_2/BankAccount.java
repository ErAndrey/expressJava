package practice_2;

public class BankAccount {
    String owner;
    int balance;

    public BankAccount(String owner) {
        this.owner = owner;
    }

    public void setOwner(String newOwner) {
        this.owner = newOwner;
    }

    public String getOwner() { return this.owner; }
    public int getBalance() { return this.balance; }

    public void deposit (int amount) {
        if (amount > 0 ) {
            this.balance += amount;
            System.out.println("Начислено на счет: " + amount);
        }
    }

    public void withdraw (int amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Списано со счета: " + amount);
        }
    }

    public void printBalance() {
        System.out.println("Ваш баланс : " + getBalance());
    }

}
