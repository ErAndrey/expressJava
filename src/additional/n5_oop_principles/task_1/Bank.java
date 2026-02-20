package additional.n5_oop_principles.task_1;

import java.util.*;

public final class Bank implements Transaction {
    private final static char CURRENCY = '₽';

    private final Scanner scanner = new Scanner(System.in);

    private final String name;
    private final Set<Account> accounts;

    private double balance;

    public Bank(String name) {
        this.name = name;
        this.balance = 0.0;
        this.accounts = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void investment(double amount) {
        this.balance += amount;
    }

    public void registerForm() {
        System.out.println("Регистрация в " + this.name + ":");
        String accountName;

        do {
            System.out.print("Придумайте имя: ");
            accountName = scanner.next();
        } while (isNoUniqueAccountName(accountName));

        System.out.print("Укажите пароль: ");
        String accountPassword = scanner.next();

        if (registerAccount(accountName, accountPassword)) {
            System.out.println("Аккаунт \"" + accountName + "\" создан!");
        } else {
            System.out.println("Что-то пошло не так, попробуйте позже");
        }
    }

    private boolean isNoUniqueAccountName(String accountName) {
        if (this.accounts.contains(new Account(accountName, null))) {
            System.out.println("Такое имя аккаунта уже используется...");
            return true;
        }
        return false;
    }

    private boolean registerAccount(String accountName, String accountPassword) {
        return this.accounts.add(new Account(accountName, accountPassword));
    }

    public Account login(String name, String password) {
        Account account = new Account(name, password);
        if (this.accounts.contains(account)) {
            System.out.println("Авторизация успешна!");
            return account;
        }
        System.out.println("Такого аккаунта нет, попробуйте позже");
        return null; //toDo exception
    }

    @Override
    public boolean deposit(double amount) {
        double loseAmount = 0.0;
        int countAccount = 0;
        System.out.println("Начисление " + amount + "% кеш-бека");

        for (Account account : this.accounts) {
            double cashBackAmount = account.getMonthsSpending() * (1 - amount / 100);
            if (account.deposit(cashBackAmount)) {
                loseAmount += cashBackAmount;
                countAccount++;
            }
            account.resetMonthsSpending();
        }

        if (countAccount > 0) {
            System.out.println("Количество счетов с активными тратами: " + countAccount + " / " + this.accounts.size());
            System.out.println("Банк выплатил: " + loseAmount + CURRENCY + ". (В среднем " + loseAmount / countAccount + CURRENCY + "/счет)");
        } else {
            System.out.println("Банк не выплатил кеш-бека");
        }

        this.balance -= loseAmount;
        return true;

    }

    @Override
    public void withdraw(double amount) {
        double fullIncomeAmount = 0.0, partlyIncomeAmount = 0.0, debtIncomeAmount = 0.0;
        int countFullIncomeAccount = 0, countPartlyIncomeAccount = 0;
        System.out.println("Снятие " + amount + CURRENCY + " за обслуживание");

        for (Account account : this.accounts) {
            double accountBalance = account.getBalance();
            if (accountBalance >= amount) {
                account.withdraw(amount);
                fullIncomeAmount += amount;
                countFullIncomeAccount++;
            } else {
                double debtAmount = Math.abs(accountBalance - amount);
                account.withdraw(accountBalance);
                partlyIncomeAmount += accountBalance;
                debtIncomeAmount += debtAmount;
                account.addDebt(debtAmount);
                countPartlyIncomeAccount++;
            }
        }

        double totalIncomeAmount = fullIncomeAmount + partlyIncomeAmount;

        if (countFullIncomeAccount + countPartlyIncomeAccount > 0) {
            System.out.println("Получено за обслуживание: " + totalIncomeAmount);
            System.out.println("Полностью оплатившие - " + countFullIncomeAccount + " счетов: " + fullIncomeAmount + CURRENCY);
            System.out.println("Частично оплатившие - " + countPartlyIncomeAccount + " счетов: " + partlyIncomeAmount + CURRENCY + ", начислено долгов: " + debtIncomeAmount);
        } else {
            System.out.println("Банк не списывал за обслуживание");
        }

        this.balance += totalIncomeAmount;
    }
}
