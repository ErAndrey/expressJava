package additional.n5_oop_principles.task_1;

import java.util.Objects;

public final class Account implements Transaction {
    private final static char CURRENCY = '₽';

    private final String name;
    private String password;

    private double balance;
    private double debt;
    private double monthsSpending;

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
        this.balance = 0.0;
        this.debt = 0.0;
        this.monthsSpending = 0.0;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public double getMonthsSpending() {
        return this.monthsSpending;
    }

    public void updatePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("Пароль успешно изменен");
            return;
        }
        System.out.println("Вы неверно указали старый пароль");
    }

    public void resetMonthsSpending() {
        this.monthsSpending = 0.0;
    }

    public void addDebt(double amount) {
        this.debt += amount;
    }

    @Override
    public boolean deposit(double amount) {
        if (amount >= 1.0) {
            double amountWithDebt = amount - this.debt;
            if (amountWithDebt >= 0) {
                this.balance += amountWithDebt;
            } else {
                this.debt -= amount;
            }
            System.out.println("Начислено: " + amount + CURRENCY);
            return true;
        }
        System.out.println("Минимальная сумма зачисления 1" + CURRENCY);
        return false;
    }

    @Override
    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Списано: " + amount + CURRENCY);
            this.monthsSpending += amount;
        }
        System.out.println("На счете недостаточно средств");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
