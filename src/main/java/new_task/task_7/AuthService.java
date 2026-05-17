package new_task.task_7;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private User currentUser = null;

    public void start() throws InterruptedException {
        while (true) {
            Utils.printActionPanel("start");

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
        String username = Utils.next("Логин: ");
        String password = Utils.next("Пароль: ");

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
        String username = Utils.next("Придумайте логин: ");

        if (users.containsKey(username)) {
            System.out.println(Utils.toError("System: ") + "Пользователь с таким логином уже существует!");
            return;
        }

        String password = Utils.next("Придумайте пароль: ");

        User newUser = new User(username, password);
        users.put(username, newUser);
        currentUser = newUser;

        System.out.print(Utils.toSuccess("System: ") + "Регистрация успешна!\n");
        createPlayerProfile();
    }

    private void createPlayerProfile() throws InterruptedException {
        String playerName = Utils.next("Введите имя игрока: ");

        Player newPlayer = new Player(playerName);
        currentUser.setPlayer(newPlayer);

        System.out.println(Utils.toSuccess("System: ") + "Игровой профиль создан!");

        System.out.println("\nДобро пожаловать в казино Java, " + Utils.toAccent(currentUser.getPlayer().getName()) + "! Приятного отдыха ;)");
        menu(newPlayer);
    }

    private void confirmPlayerAccount(Player player) {
        System.out.println(Utils.toInfo("System: ") + "Чтобы подтвердить аккаунт, укажите ваш Email");
        while (true) {
            String email = Utils.next("Ваша почта: ");
            if (Utils.validateEmail(email)) {
                player.setEmail(email);
                player.setConfirmedAccount();
                System.out.println(Utils.toSuccess("System: ") + "Профиль подтвержден, лимиты увеличены!");
                return;
            }
            System.out.println(Utils.toError("System: ") + "Почта \"" + email + "\" некорректна!");
        }
    }

    private void menu(Player player) throws InterruptedException {
        Casino casino = new Casino(player, 2_000_000_000);

        while (true) {
            //System.out.println(Utils.toAccent("\nSystem: ") + "Баланс казино " + Utils.formatCurrency(casino.getBalance()));

            Utils.printActionPanel("menu");

            int choice = Utils.whatToDoNext(3);

            switch (choice) {
                case 0 -> {
                    Utils.dotAnimation("Возвращаемся к авторизации");
                    return;
                }
                case 1 -> profile(player);
                case 2 -> balance(player, casino);
                case 3 -> casino.play();
            }
        }
    }

    private void profile(Player player) throws InterruptedException {
        while (true) {
            boolean isConfirmedAccount = player.isConfirmedAccount();
            String isConfirmedText = isConfirmedAccount ? "Да" : "Нет";

            if (isConfirmedAccount) {
                Utils.printActionPanel("confirmedProfile");
            } else {
                Utils.printActionPanel("unconfirmedProfile");
                System.out.println(Utils.toError("System: ") + "Лимиты на пополнение и снятие снижены! Подтвердите аккаунт для их увеличения\n");
            }

            System.out.println("Имя: " + player.getName());
            System.out.println("Почта: " + player.getEmail());
            System.out.println("Подтвержден: " + isConfirmedText);
            System.out.println("Лимит на депозит: " + Utils.formatCurrency(player.getLimitChangeBalance()));

            if (player.getTotalGames() != 0) {
                System.out.println("\nПроцент побед         " + player.getWinRate() + "%");
                System.out.println("Количество выигрышей  " + player.getTotalWins());
                System.out.println("Количество проигрышей " + player.getTotalLose());
                System.out.println("Лучшая серия побед    " + player.getMaxWins());
                System.out.println("Текущая серия побед   " + player.getCurrentWins());
                System.out.println("\nМаксимальный выигрыш  " + Utils.formatCurrency(player.getMaxWinAmount()));
                System.out.println("Сумма выигрышей       " + Utils.formatCurrency(player.getTotalWinAmount()));
                System.out.println("Сумма проигрышей      " + Utils.formatCurrency(player.getTotalLoseAmount()));
            }

            System.out.println("\nСумма пополнений      " + Utils.formatCurrency(player.getAllIncome()));
            System.out.println("Сумма выводов         " + Utils.formatCurrency(player.getAllOutcome()));
            System.out.println("Текущий баланс        " + Utils.formatCurrency(player.getBalance()) + "\n");

            int choice = isConfirmedAccount ? Utils.whatToDoNext(0) : Utils.whatToDoNext(1);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> confirmPlayerAccount(player);
            }
        }
    }

    private void balance(Player player, Casino casino) throws InterruptedException {
        while (true) {
            System.out.println(Utils.toInfo("\nSystem: ") + "Ваш баланс " + Utils.formatCurrency(player.getBalance()));

            Utils.printActionPanel("balance");

            int choice = Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    int amount = Utils.nextInt("Сумма пополнения: ");
                    if (player.deposit(amount, WhoChangePlayerBalance.PLAYER)) {
                        player.updateAllIncome(amount);
                        System.out.println(Utils.toSuccess("System: ") + "Пополнение " + Utils.formatCurrency(amount));
                    }
                }
                case 2 -> {
                    double commission = Casino.COMMISSION_FOR_WITHDRAW;
                    System.out.println(Utils.toInfo("System: ") + "Комиссия на вывод - " + commission + "%");
                    int amount = Utils.nextInt("Сумма вывода: ");
                    if (player.getBalance() >= amount) {
                        if (player.withdraw(amount, WhoChangePlayerBalance.PLAYER)) {
                            double withdraw = amount * (1 - commission / 100);
                            casino.changeBalance(amount * commission / 100);
                            player.updateAllOutcome(withdraw);
                            System.out.println(Utils.toSuccess("System: ") + "Вывод " + Utils.formatCurrency(withdraw));
                        }
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