package new_task.task_7;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Player andrew = new Player("Андрей");
        Roulette roulette = new Roulette(andrew, 50_000);

        System.out.println("Баланс казино " + roulette.getBalance() + "₽");

        andrew.deposit(25_000);

        while (andrew.getBalance() != 0) {
            roulette.playEven();
        }

        System.out.println("Баланс казино " + roulette.getBalance() + "₽");
    }
}
