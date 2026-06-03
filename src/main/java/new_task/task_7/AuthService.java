package new_task.task_7;

import new_task.task_7.bet.Bet;
import new_task.task_7.bet.BetHistory;
import new_task.task_7.bonuses.*;
import new_task.task_7.action_panel.ActionPanel;
import new_task.task_7.action_panel.ActionPanels;

import java.time.LocalDateTime;
import java.util.*;

public class AuthService {
    private final Map<String, User> users = new HashMap<>();
    private User currentUser = null;

    public void start() throws InterruptedException {
        while (true) {
            System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.START));

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

    private Optional<User> findUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
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

    private void menu(Player player) throws InterruptedException {
        Casino casino = new Casino(player, 2_000_000_000);

        while (true) {
            //System.out.println(Utils.toAccent("\nSystem: ") + "Баланс казино " + Utils.formatCurrency(casino.getBalance()));

            System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.MENU));

            int choice = Utils.whatToDoNext(4);

            switch (choice) {
                case 0 -> {
                    Utils.dotAnimation("Возвращаемся к авторизации");
                    return;
                }
                case 1 -> profile(player);
                case 2 -> balance(player, casino);
                case 3 -> bonuses(player);
                case 4 -> casino.play();
            }
        }
    }

    //toDo Удалить профиль
    private void profile(Player player) {
        while (true) {
            boolean isConfirmedAccount = player.isConfirmedAccount();
            String isConfirmedText = isConfirmedAccount ? Utils.toSuccess("Да") : Utils.toError("Нет");

            if (isConfirmedAccount) {
                System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.CONFIRMED_PROFILE));
            } else {
                System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.UNCONFIRMED_PROFILE));
                System.out.println(Utils.toError("System: ") + "Лимиты на пополнение и снятие снижены! Подтвердите аккаунт для их увеличения\n");
            }

            System.out.println("Имя: " + player.getName());
            System.out.println("Почта: " + player.getEmail());
            System.out.println("Подтвержден: " + isConfirmedText);
            System.out.println("Лимит на депозит: " + Utils.formatCurrency(player.getLimitChangeBalance()) + "\n");

            int choice = isConfirmedAccount ? Utils.whatToDoNext(2) : Utils.whatToDoNext(3);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> seeStatistics(player);
                case 2 -> seeBetHistory(player);
                case 3 -> confirmPlayerAccount(player);
            }
        }
    }

    private void seeStatistics(Player player) {
        while (true) {
            System.out.println(Utils.toInfo("\nSystem: ") + "Статистика аккаунта:");
            if (player.getTotalGames() != 0) {
                System.out.println("\nПроцент побед          " + player.getWinRate() + "%");
                System.out.println("Количество игр         " + player.getTotalGames());

                System.out.println("\nВсего выигрышей        " + Utils.toSuccess("" + player.getTotalWins()));
                System.out.println("Текущая серия          " + player.getCurrentWins());
                System.out.println("Наибольшая серия       " + player.getMaxWins());
                System.out.println("Максимальный выигрыш   " + Utils.formatCurrency(player.getMaxWinAmount()));
                System.out.println("Сумма выигрышей        " + Utils.formatCurrency(player.getTotalWinAmount()));

                System.out.println("\nВсего проигрышей       " + Utils.toError("" + player.getTotalLose()));
                System.out.println("Текущая серия          " + player.getCurrentLose());
                System.out.println("Наибольшая серия       " + player.getMaxLose());
                System.out.println("Максимальный проигрыш  " + Utils.formatCurrency(player.getMaxLoseAmount()));
                System.out.println("Сумма проигрышей       " + Utils.formatCurrency(player.getTotalLoseAmount()));

                System.out.println("\nВсего возвратов        " + Utils.toInfo("" + player.getTotalReturns()));
                System.out.println("Сумма возвратов        " + Utils.formatCurrency(player.getTotalReturnAmount()));
            }

            System.out.println("\nСумма пополнений       " + Utils.formatCurrency(player.getAllIncome()));
            System.out.println("Сумма выводов          " + Utils.formatCurrency(player.getAllOutcome()));
            System.out.println("Текущий баланс         " + Utils.formatCurrency(player.getBalance()) + "\n");

            System.out.println(Utils.toInfo("System: ") + "Чтобы вернуться назад выберите 0\n");

            if (Utils.whatToDoNext(0) == 0) return;
        }
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

    private void seeBetHistory(Player player) {
        while (true) {
            BetHistory<LocalDateTime, Bet> betHistory = player.getBetHistory();

            if (betHistory.isEmpty()) {
                System.out.println(Utils.toInfo("\nSystem: ") + "История игр пуста");
            } else {
                System.out.println(Utils.toInfo("\nSystem: ") + "Последние " + betHistory.getHistorySize() + " игр:\n");
                betHistory.values().forEach(System.out::println);
            }

            System.out.println(Utils.toInfo("\nSystem: ") + "Чтобы вернуться назад выберите 0\n");

            if (Utils.whatToDoNext(0) == 0) return;
        }
    }

    private void balance(Player player, Casino casino) {
        while (true) {
            System.out.println(Utils.toInfo("\nSystem: ") + "Ваш баланс " + Utils.formatCurrency(player.getBalance()));

            TreeMap<Integer, DepositBonus> availableDepositBonuses = player.getDepositBonuses();
            boolean haveDepositBonuses = !availableDepositBonuses.isEmpty();
            boolean isOnlyOneDepositBonus = availableDepositBonuses.size() == 1;

            if (haveDepositBonuses) {
                if (isOnlyOneDepositBonus) {
                    DepositBonus depositBonus = availableDepositBonuses.firstEntry().getValue();
                    System.out.println(Utils.toInfo("System: ") + "Доступен бонус на дополнительные " + Utils.toSuccess(depositBonus.getPercent() + "%") + " при пополнении баланса!");
                } else {
                    System.out.println(Utils.toInfo("System: ") + "У вас есть несколько доступных бонусов для пополнения баланса!");
                }
            }

            System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.BALANCE));

            int choice = Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> {
                    boolean isTakeDepositBonus = false;
                    if (haveDepositBonuses) {
                        if (isOnlyOneDepositBonus) {
                            System.out.println("\n" +
                                    """
                                    0. Пополнить без бонуса
                                    1. Использовать бонус
                                    """
                            );
                        } else {
                            System.out.println("\n" +
                                    """
                                    0. Пополнить без бонуса
                                    1. Выбрать бонус
                                    """
                            );
                        }
                        isTakeDepositBonus = Utils.whatToDoNext(1) == 1;
                    }

                    int amount;

                    if (isTakeDepositBonus) {
                        if (isOnlyOneDepositBonus) {
                            DepositBonus depositBonus = availableDepositBonuses.firstEntry().getValue();
                            amount = Utils.nextInt("Сумма пополнения: ");

                            if (player.deposit(amount, depositBonus)) {
                                player.updateAllIncome(amount);
                                System.out.println(Utils.toSuccess("System: ") + "Пополнение " + Utils.formatCurrency(amount + depositBonus.getDepositBonusAmount(amount)) + " (" + Utils.formatCurrency(amount) + " + " + Utils.formatCurrency(depositBonus.getDepositBonusAmount(amount)) + ")");
                                player.removeDepositBonus(depositBonus);
                            }
                        } else {
                            System.out.println("\nВыберите один из бонусов:");
                            availableDepositBonuses.values().forEach(System.out::println);
                            System.out.println();

                            DepositBonus selectedDepositBonus;
                            int selectedBonusNumber;

                            while (true) {
                                selectedBonusNumber = Utils.nextInt("Номер бонуса: ");
                                if (availableDepositBonuses.containsKey(selectedBonusNumber)) {
                                    selectedDepositBonus = availableDepositBonuses.get(selectedBonusNumber);
                                    break;
                                }
                                System.out.println(Utils.toError("System: ") + "Выберите доступный бонус");
                            }

                            amount = Utils.nextInt("Сумма пополнения: ");

                            if (player.deposit(amount, selectedDepositBonus)) {
                                player.updateAllIncome(amount);
                                System.out.println(Utils.toSuccess("System: ") + "Пополнение " + Utils.formatCurrency(amount + selectedDepositBonus.getDepositBonusAmount(amount)) + " (" + Utils.formatCurrency(amount) + " + " + Utils.formatCurrency(selectedDepositBonus.getDepositBonusAmount(amount)) + ")");
                                player.removeDepositBonus(selectedDepositBonus);
                            }
                        }
                    } else {
                        amount = Utils.nextInt("Сумма пополнения: ");
                        if (player.deposit(amount, WhoChangePlayerBalance.PLAYER)){
                            player.updateAllIncome(amount);
                            System.out.println(Utils.toSuccess("System: ") + "Пополнение " + Utils.formatCurrency(amount));
                        }
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
                            System.out.println(Utils.toSuccess("System: ") + "Списание " + Utils.formatCurrency(withdraw));
                        }
                    } else {
                        System.out.println(Utils.toError("System: ") + "Недостаточно средств!");
                    }
                }
            }
        }
    }

    private void bonuses(Player player) {
        while (true) {
            TreeMap<Integer, Bonus> availableBonuses = player.getAvailableBonuses();
            boolean isHaveAvailableBonuses = !availableBonuses.isEmpty();

            TreeMap<Integer, FreeBetBonus> freeBetBonuses = player.getFreeBetBonuses();
            TreeMap<Integer, DepositBonus> depositBonuses = player.getDepositBonuses();

            boolean isHaveActiveBonuses = (freeBetBonuses.size() + depositBonuses.size()) > 0;

            if (isHaveAvailableBonuses) {
                System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.HAVE_BONUSES));
                System.out.println(Utils.toInfo("Доступные бонусы: ") + "Успейте применить и воспользоваться, пока предложение активно!\n");
                availableBonuses.values().forEach(System.out::println);
                System.out.println();
            } else {
                System.out.println(ActionPanels.ACTION_PANELS.get(ActionPanel.HAVE_NOT_BONUSES));
                if (!isHaveActiveBonuses) {
                    if (player.isSeeEmptyBonuses()) {
                        System.out.println(Utils.toInfo("System: ") + "Новых бонусов пока-что нет\n");
                    } else {
                        System.out.println(Utils.toInfo("System: ") + "У вас еще нет бонусов, узнайте, как их получить ;)\n");
                        player.setSeeEmptyBonuses();
                    }
                }
            }

            if (isHaveActiveBonuses) {
                System.out.println(Utils.toInfo("Активные бонусы: ") + "Воспользуйтесь, пока бонусы не сгорели!\n");
                freeBetBonuses.values().forEach(System.out::println);
                depositBonuses.values().forEach(System.out::println);
                System.out.println();
            }

            int choice = isHaveAvailableBonuses ? Utils.whatToDoNext(3) : Utils.whatToDoNext(2);

            switch (choice) {
                case 0 -> { return; }
                case 1 -> howToGetBonuses();
                case 2 -> viewBonusTypes();
                case 3 -> selectBonus(player);
            }
        }
    }

    private void howToGetBonuses() {
        while (true) {
            System.out.println(Utils.toInfo("\nЗа что можно получить бонусы:"));

            System.out.println(
                    """
                    1. Через каждые 16 игр.
                    2. Через каждые 33 победы.
                    3. Через каждые 33 поражения.
                    4. За серии побед.
                    5. За серии поражений.
                    """
            );

            System.out.println(Utils.toInfo("System: ") + "Чтобы вернуться назад выберите 0\n");

            if (Utils.whatToDoNext(0) == 0) return;
        }
    }

    private void viewBonusTypes() {
        while (true) {
            System.out.println(Utils.toInfo("\nКакие бонусы бывают:"));

            System.out.println(
                    "1. " + Utils.toAccent("Бонус на счет") + " - предоставляется в виде суммы, при применении пополняет ваш депозит на сумму бонуса.\n" +
                            "2. " + Utils.toAccent("Бонус на депозит") + " - начисляется как процент, увеличивая сумму следующего пополнения баланса.\n" +
                            "3. " + Utils.toAccent("Фрибет") + " - бесплатная ставка, при выигрыше вы получаете только чистую прибыль без возврата суммы ставки.\n"
            );

            System.out.println(Utils.toInfo("System: ") + "Чтобы вернуться назад выберите 0\n");

            if (Utils.whatToDoNext(0) == 0) return;
        }
    }

    private void selectBonus(Player player) {
        System.out.println(Utils.toInfo("System: ") + "Чтобы применить бонус, укажите его номер");
        while (true) {
            int choice = Utils.nextInt("Номер бонуса: ");
            if (player.getAvailableBonuses().containsKey(choice)) {
                Object bonus = player.getAvailableBonuses().get(choice);
                switch (bonus) {
                    case CashBonus cashBonus -> applyCashBonus(player, cashBonus);
                    case DepositBonus depositBonus -> applyDepositBonus(player, depositBonus);
                    case FreeBetBonus freeBetBonus -> applyFreeBetBonus(player, freeBetBonus);
                    default -> System.out.println(Utils.toError("System: ") + "Ошибка выбора бонуса!");
                }
                return;
            }
            System.out.println(Utils.toError("System: ") + "Выберите доступный бонус!");
        }
    }

    private void applyCashBonus(Player player, CashBonus bonus) {
        player.deposit(bonus.getAmount(), WhoChangePlayerBalance.CASINO);
        player.removeAvailableBonus(bonus);
        System.out.println(Utils.toSuccess("System: ") + "Бонус " + Utils.toAccent("#" + bonus.getNumber()) + " активирован, ваш баланс увеличен на " + Utils.formatCurrency(bonus.getAmount()));
    }

    private void applyDepositBonus(Player player, DepositBonus bonus) {
        player.removeAvailableBonus(bonus);
        player.addDepositBonus(bonus);
        bonus.activate();
        System.out.println(Utils.toSuccess("System: ") + "Бонус " + Utils.toAccent("#" + bonus.getNumber()) + " активирован, применив бонус при следующем пополнении вы получите дополнительные " + bonus.getPercent() + "%");
    }

    private void applyFreeBetBonus(Player player, FreeBetBonus bonus) {
        player.removeAvailableBonus(bonus);
        player.addFreeBetBonus(bonus);
        bonus.activate();
        System.out.println(Utils.toSuccess("System: ") + "Бонус " + Utils.toAccent("#" + bonus.getNumber()) + " - фрибет на " + Utils.formatCurrency(bonus.getFreeBetAmount()) + " активирован, можете воспользоваться им в следующей игре с Кф до " + Utils.toAccent("x" + bonus.getMaxMultiplier()));
    }
}
