package additional.n5_oop_principles.task_1;

public class Main {
    public static void main(String[] args) {
        Bank amka = new Bank("Амка");
        amka.registerForm();
        Account danila = amka.login();

        System.out.println(amka.getAccounts());

        danila.deposit(1.5);
        danila.deposit(1000);
        danila.withdraw(500);

        System.out.println(danila.getBalance());
        System.out.println(danila.getMonthsSpending());

        System.out.println("///");

        amka.withdraw(150);

        //amka.deposit(15);

        System.out.println(danila.getBalance());
        System.out.println(danila.getMonthsSpending());

    }
}
