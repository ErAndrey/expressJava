package new_task.task_7;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Player andrew = new Player("Андрей");
        Casino casino = new Casino(andrew, 50_000);

        andrew.deposit(25_000);
        System.out.println("Баланс казино " + casino.getBalance() + "₽");

        casino.play();
    }
}
