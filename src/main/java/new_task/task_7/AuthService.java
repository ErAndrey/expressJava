package new_task.task_7;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private User currentUser = null;

    public void start() throws InterruptedException {
        while (true) {
            System.out.println("\n     ~       " + "|    <    |       +       |    >    |" + "       ~     ");
            System.out.println(Utils.toInfo("Авторизация  ") + "|  Выход  |  Регистрация  |  Войти  |" + Utils.toInfo("  Авторизация"));
            System.out.println("     ~       " + "|    0    |       1       |    2    |" +"       ~    \n");

            int choice = Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    System.out.println("До свидания! Будем ждать вас снова!");
                    return;
                }
                case 1 -> register();
                case 2 -> login();
            }
        }
    }

    private void login() throws InterruptedException {
        System.out.print("Логин: ");
        String username = Utils.next();
        System.out.print("Пароль: ");
        String password = Utils.next();

        Optional<User> userOpt = findUser(username, password);

        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println(Utils.toSuccess("System: ") + "Успешный вход!");

            if (currentUser.hasPlayer()) {
                System.out.println("\nДобро пожаловать в казино Java, " + Utils.toAccent(currentUser.getPlayer().getName()) + "! Приятного отдыха ;)");
                menu(currentUser.getPlayer());
            } else {
                createPlayerProfile();
            }
        } else {
            System.out.println(Utils.toError("System: ") + "Неверный логин или пароль!");
        }
    }

    private void register() throws InterruptedException {
        System.out.print("Придумайте логин: ");
        String username = Utils.next();

        if (users.containsKey(username)) {
            System.out.println(Utils.toError("System: ") + "Пользователь с таким логином уже существует!");
            return;
        }

        System.out.print("Придумайте пароль: ");
        String password = Utils.next();

        User newUser = new User(username, password);
        users.put(username, newUser);
        currentUser = newUser;

        System.out.println(Utils.toSuccess("System: ") + "Регистрация успешна!\n");
        createPlayerProfile();
    }

    private void createPlayerProfile() throws InterruptedException {
        System.out.print("Введите имя игрока: ");
        String playerName = Utils.next();

        Player newPlayer = new Player(playerName);
        currentUser.setPlayer(newPlayer);

        System.out.println(Utils.toSuccess("System: ") + "Игровой профиль создан!");

        System.out.println("\nДобро пожаловать в казино Java, " + Utils.toAccent(currentUser.getPlayer().getName()) + "! Приятного отдыха ;)");
        menu(newPlayer);
    }

    private void menu(Player player) throws InterruptedException {
        Casino casino = new Casino(player, 100_000_000);

        while (true) {
            System.out.println("\n   ~     " + "|    <    |     ₽     |    >    |" +"     ~    ");
            System.out.println(Utils.toInfo("Главная  ") + "|  Выход  |  Депозит  |  Азарт  |" + Utils.toInfo("  Главная "));
            System.out.println("   ~     " + "|    0    |     1     |    2    |" + "     ~    \n");

            int choice = Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    System.out.println("Возвращаемся к авторизации");
                    Utils.dotAnimation();
                    return;
                }
                case 1 -> balance(player);
                case 2 -> casino.play();
            }
        }
    }

    private void balance(Player player) throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo("\nВаш баланс: ") + Utils.formatCurrency(player.getBalance()));

            System.out.println("\n   ~     " + "|    <    |      ₽      |     >     |" + "     ~   " );
            System.out.println(Utils.toInfo("Депозит  ") + "|  Назад  |  Пополнить  |  Вывести  |" + Utils.toInfo("  Депозит"));
            System.out.println("   ~     " + "|    0    |      1      |     2     |" + "     ~    \n");

            int choice = Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    int amount = Utils.nextInt("Сумма пополнения: ");
                    if (player.deposit(amount)) {
                        System.out.println(Utils.toSuccess("System: ") + "Баланс пополнен!");
                    }
                }
                case 2 -> {
                    int amount = Utils.nextInt("Сумма вывода: ");
                    if (player.getBalance() >= amount) {
                        if (player.withdraw(amount)) System.out.println(Utils.toSuccess("System: ") + "Средства выведены!");
                    } else {
                        System.out.println(Utils.toError("System: ") + "Недостаточно средств!");
                    }
                }
            }
        }
    }

    private Optional<User> findUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}