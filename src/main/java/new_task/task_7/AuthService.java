package new_task.task_7;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private User currentUser = null;

    private static final String YELLOW = "\u001B[93m";
    private static final String PURPLE = "\u001B[35m";
    private static final String GREEN = "\u001B[92m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public void start() throws InterruptedException {
        while (true) {

            System.out.println(       "\n     ~       "         + "|    <    |       +       |    >    |" +          "       ~     "         );
            System.out.println(YELLOW + "Авторизация  " + RESET + "|  Выход  |  Регистрация  |  Войти  |" + YELLOW + "  Авторизация" + RESET);
            System.out.println(         "     ~       "         + "|    0    |       1       |    2    |" +          "       ~    \n"         );

            int choice = Casino.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    System.out.println("До свидания! Ждем вас снова!");
                    return;
                }
                case 1 -> register();
                case 2 -> login();
            }
        }
    }

    private void login() throws InterruptedException {
        System.out.print("Логин: ");
        String username = Casino.getScanner().next();
        System.out.print("Пароль: ");
        String password = Casino.getScanner().next();

        Optional<User> userOpt = findUser(username, password);

        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println(GREEN + "Успешный вход!" + RESET);

            if (currentUser.hasPlayer()) {
                System.out.println("\nДобро пожаловать в казино Java, " + PURPLE + currentUser.getPlayer().getName() + RESET + "! Приятного отдыха ;)");
                menu(currentUser.getPlayer());
            } else {
                createPlayerProfile();
            }
        } else {
            System.out.println(RED + "Неверный логин или пароль!" + RESET);
        }
    }

    private void register() throws InterruptedException {
        System.out.print("Придумайте логин: ");
        String username = Casino.getScanner().next();

        if (users.containsKey(username)) {
            System.out.println(RED + "Пользователь с таким логином уже существует!" + RESET);
            return;
        }

        System.out.print("Придумайте пароль: ");
        String password = Casino.getScanner().next();

        User newUser = new User(username, password);
        users.put(username, newUser);
        currentUser = newUser;

        System.out.println(GREEN + "Регистрация успешна!" + RESET);
        createPlayerProfile();
    }

    private void createPlayerProfile() throws InterruptedException {
        System.out.print("Введите имя игрока: ");
        String playerName = Casino.getScanner().next();

        Player newPlayer = new Player(playerName);
        currentUser.setPlayer(newPlayer);

        System.out.println(GREEN + "Игровой профиль создан!" + RESET);

        System.out.println("\nДобро пожаловать в казино Java, " + PURPLE + currentUser.getPlayer().getName() + RESET + "! Приятного отдыха ;)");
        menu(newPlayer);
    }

    private void menu(Player player) throws InterruptedException {
        Casino casino = new Casino(player, 100_000_000);

        while (true) {
            System.out.println(       "\n   ~     "         + "|    <    |     ₽     |    >    |" +          "     ~    "        );
            System.out.println(YELLOW + "Главная  " + RESET + "|  Выход  |  Депозит  |  Азарт  |" + YELLOW + "  Главная " + RESET);
            System.out.println(         "   ~     "         + "|    0    |     1     |    2    |" +          "     ~    \n"      );

            int choice = Casino.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    System.out.println("Возврат к авторизации..");
                    return;
                }
                case 1 -> balanceMenu(player);
                case 2 -> casino.play();
            }
        }
    }

    private void balanceMenu(Player player) throws InterruptedException {
        while (true) {
            System.out.println(YELLOW + "\nВаш баланс: " + RESET + player.getBalance() + "₽");

            System.out.println(       "\n   ~     "         + "|    <    |      ₽      |     >     |" +          "     ~   "        );
            System.out.println(YELLOW + "Депозит  " + RESET + "|  Назад  |  Пополнить  |  Вывести  |" + YELLOW + "  Депозит" + RESET);
            System.out.println(         "   ~     "         + "|    0    |      1      |     2     |" +          "     ~    \n"     );

            int choice = Casino.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    System.out.print("Сумма пополнения: ");
                    double amount = Casino.getScanner().nextDouble();
                    player.deposit(amount);
                    System.out.println(GREEN + "Баланс пополнен!" + RESET);
                }
                case 2 -> {
                    System.out.print("Сумма вывода: ");
                    double amount = Casino.getScanner().nextDouble();
                    if (player.getBalance() >= amount) {
                        player.withdraw(amount);
                        System.out.println(GREEN + "Средства выведены!" + RESET);
                    } else {
                        System.out.println(RED + "Недостаточно средств!" + RESET);
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