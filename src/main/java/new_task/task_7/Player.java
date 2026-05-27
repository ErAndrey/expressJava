package new_task.task_7;

import new_task.task_7.bonuses.Bonus;
import new_task.task_7.bonuses.DepositBonus;
import new_task.task_7.bonuses.FreeBetBonus;

import java.util.List;
import java.util.TreeMap;

public class Player {
    private final String name;
    private double balance;

    private String email;
    private boolean isConfirmedAccount;
    private int limitChangeBalance; // toDo разбить на пополнение и вывод

    private int totalGames;
    private int maxWins;
    private int totalWins;
    private int totalLose;
    private int currentWins;
    private int currentLose;

    private double maxWinAmount;
    private double totalWinAmount;
    private double totalLoseAmount;
    private double allIncome;
    private double allOutcome;

    //toDo перейти на availableBonus и activeBonus, чтобы не добавлять лишние поля для бонусов
    // так же метод, который проверяет их срок
    private final TreeMap<Integer, Bonus> availableBonuses;
    private boolean isSeeEmptyBonuses;

    private DepositBonus depositBonus;
    //toDo заменить на treeMap / лист + так же учитывать expired , флаг сносим, в листе будут все активные фрибеты, которые аплплает юзер, будут выбираться в зависимости от игры, кфа и возможности казино выплатить фрибет с учетом кфа и его баланса
    private FreeBetBonus freeBetBonus;
    private boolean isFreeBetBonusActive;

    List<FreeBetBonus> freeBetBonusList;

    public Player(String name) {
        this.name = name;
        this.limitChangeBalance = 100_000;
        this.availableBonuses = new TreeMap<>();
    }

    public String getName() { return this.name; }

    public double getBalance() { return this.balance; }

    public String getEmail() { return this.email == null ? "Отсутствует" : this.email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isConfirmedAccount() { return this.isConfirmedAccount; }
    public void setConfirmedAccount() {
        this.isConfirmedAccount = true;
        this.limitChangeBalance = 500_000;
    }

    public int getLimitChangeBalance() { return this.limitChangeBalance; }
    private String getMessageForLimitChangeBalance() {
        String text = Utils.toError("System: ") + "Текущий лимит " + Utils.formatCurrency(limitChangeBalance);
        return isConfirmedAccount ? text : text + ", вы можете повысить лимит в вашем профиле!";
    }

    public double getWinRate() { return Math.round(((this.totalWins * 100.0) / this.totalGames) * 100.0) / 100.0; }

    public int getTotalGames() { return this.totalGames; }

    public int getTotalWins() { return this.totalWins; }
    public void updateTotalWins() { this.totalWins++; this.totalGames++; }

    public int getTotalLose() { return this.totalLose; }
    public void updateTotalLose() { this.totalLose++; this.totalGames++; }

    public int getCurrentWins() { return this.currentWins; }
    public void updateCurrentWins() { this.currentWins++; }
    public void resetCurrentWins() { this.currentWins = 0; }

    public int getCurrentLose() { return this.currentLose; }
    public void updateCurrentLose() { this.currentLose++; }
    public void resetCurrentLose() { this.currentLose = 0; }

    public int getMaxWins() { return this.maxWins; }
    public void updateMaxWins(int count) { this.maxWins = count; }

    public double getMaxWinAmount() { return this.maxWinAmount; }
    public void updateMaxWinAmount(double value) { this.maxWinAmount = value; }

    public double getAllIncome() { return this.allIncome; }
    public void updateAllIncome(double value) { this.allIncome += value; }

    public double getAllOutcome() { return this.allOutcome; }
    public void updateAllOutcome(double value) { this.allOutcome += value; }

    public double getTotalWinAmount() { return this.totalWinAmount; }
    public void updateTotalWinAmount(double value) { this.totalWinAmount += value; }

    public double getTotalLoseAmount() { return this.totalLoseAmount; }
    public void updateTotalLoseAmount(double value) { this.totalLoseAmount += value; }

    public TreeMap<Integer, Bonus> getAvailableBonuses() {
        removeExpiredBonuses();
        return this.availableBonuses;
    }
    public void addAvailableBonus(Bonus bonus) { this.availableBonuses.put(bonus.getNumber(), bonus); }
    public void removeAvailableBonus(Bonus bonus) { this.availableBonuses.remove(bonus.getNumber()); }
    private void removeExpiredBonuses() { availableBonuses.entrySet().removeIf(entry -> entry.getValue().isExpired()); }

    public boolean isSeeEmptyBonuses() { return this.isSeeEmptyBonuses; }
    public void setSeeEmptyBonuses() { this.isSeeEmptyBonuses = true; }

    public DepositBonus getDepositBonus() { return this.depositBonus; }
    public void addDepositBonus(DepositBonus depositBonus) { this.depositBonus = depositBonus; }
    public void removeDepositBonus() { this.depositBonus = null; }
    private double getAmountDepositBonus(double amount) {
        double depositBonus = amount * (this.depositBonus.getPercent() / 100.0);
        System.out.println(Utils.toInfo("System: ") + "Бонус к депозиту " + this.depositBonus.getPercent() + "% применен! Дополнительно начислили " + Utils.formatCurrency(depositBonus));
        this.removeDepositBonus();
        return depositBonus;
    }
    public boolean isDepositBonus() { return this.depositBonus != null; }

    public FreeBetBonus getFreeBetBonus() { return this.freeBetBonus; }
    public void setFreeBetBonus(FreeBetBonus freeBetBonus) { this.freeBetBonus = freeBetBonus; }
    public boolean isFreeBetBonusActive() { return this.isFreeBetBonusActive; }
    public void setFreeBetBonusActive(boolean isActive) { this.isFreeBetBonusActive = isActive; }



    public boolean deposit(double amount, WhoChangePlayerBalance who) {
        if (amount <= 0) {
            System.out.println(Utils.toError("System: ") + "Сумма должна быть положительной, больше 0!");
            return false;
        }
        if (who == WhoChangePlayerBalance.PLAYER) {
            if (amount > limitChangeBalance) {
                System.out.println(getMessageForLimitChangeBalance());
                return false;
            }
            if (this.isDepositBonus()) amount += getAmountDepositBonus(amount);
        }
        this.balance += amount;
        return true;
    }

    public boolean withdraw(double amount, WhoChangePlayerBalance who) {
        if (amount <= 0) {
            System.out.println(Utils.toError("System: ") + "Сумма должна быть положительной, больше 0!");
            return false;
        }
        if (this.balance < amount) {
            System.out.println(Utils.toError("System: ") + "Недостаточно средств! Баланс: " + Utils.formatCurrency(this.balance));
            return false;
        }
        if (who == WhoChangePlayerBalance.PLAYER && amount > limitChangeBalance) {
            System.out.println(getMessageForLimitChangeBalance());
            return false;
        }
        this.balance -= amount;
        return true;
    }
}