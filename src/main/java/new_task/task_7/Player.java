package new_task.task_7;

import new_task.task_7.bet.Bet;
import new_task.task_7.bet.BetHistory;
import new_task.task_7.bonuses.Bonus;
import new_task.task_7.bonuses.DepositBonus;
import new_task.task_7.bonuses.FreeBetBonus;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class Player {
    private final String name;
    private double balance;

    private String email;
    private boolean isConfirmedAccount;
    private int limitChangeBalance; // toDo разбить на пополнение и вывод

    private int totalGames;

    private int totalWins;
    private int currentWins;
    private int maxWins;
    private double maxWinAmount;
    private double totalWinAmount;

    private int totalLose;
    private int currentLose;
    private int maxLose;
    private double maxLoseAmount;
    private double totalLoseAmount;

    private int totalReturns;
    private double totalReturnAmount;

    private double allIncome;
    private double allOutcome;

    private final TreeMap<Integer, Bonus> availableBonuses;
    private boolean isSeeEmptyBonuses;

    private final TreeMap<Integer, FreeBetBonus> freeBetBonuses;
    private final TreeMap<Integer, DepositBonus> depositBonuses;

    private final BetHistory<LocalDateTime, Bet> betHistory;

    public Player(String name) {
        this.name = name;
        this.limitChangeBalance = 100_000;
        this.availableBonuses = new TreeMap<>();
        this.freeBetBonuses = new TreeMap<>();
        this.depositBonuses = new TreeMap<>();
        this.betHistory = new BetHistory<>(25);
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
    public int getCurrentWins() { return this.currentWins; }
    public void updateCurrentWins() { this.currentWins++; }
    public void resetCurrentWins() { this.currentWins = 0; }
    public int getMaxWins() { return this.maxWins; }
    public void updateMaxWins(int count) { this.maxWins = count; }
    public double getMaxWinAmount() { return this.maxWinAmount; }
    public void updateMaxWinAmount(double value) { this.maxWinAmount = value; }
    public double getTotalWinAmount() { return this.totalWinAmount; }
    public void updateTotalWinAmount(double value) { this.totalWinAmount += value; }

    public int getTotalLose() { return this.totalLose; }
    public void updateTotalLose() { this.totalLose++; this.totalGames++; }
    public int getCurrentLose() { return this.currentLose; }
    public void updateCurrentLose() { this.currentLose++; }
    public void resetCurrentLose() { this.currentLose = 0; }
    public int getMaxLose() { return this.maxLose; }
    public void updateMaxLose(int count) { this.maxLose = count; }
    public double getMaxLoseAmount() { return this.maxLoseAmount; }
    public void updateMaxLoseAmount(double value) { this.maxLoseAmount = value; }
    public double getTotalLoseAmount() { return this.totalLoseAmount; }
    public void updateTotalLoseAmount(double value) { this.totalLoseAmount += value; }

    public int getTotalReturns() { return this.totalReturns; }
    public void updateTotalReturns() { this.totalReturns++; this.totalGames++; }
    public double getTotalReturnAmount() { return this.totalReturnAmount; }
    public void updateTotalReturnAmount(double value) { this.totalReturnAmount += value; }

    public double getAllIncome() { return this.allIncome; }
    public void updateAllIncome(double value) { this.allIncome += value; }
    public double getAllOutcome() { return this.allOutcome; }
    public void updateAllOutcome(double value) { this.allOutcome += value; }

    public TreeMap<Integer, Bonus> getAvailableBonuses() {
        this.availableBonuses.entrySet().removeIf(entry -> entry.getValue().isExpired());
        return this.availableBonuses;
    }
    public void addAvailableBonus(Bonus bonus) { this.availableBonuses.put(bonus.getNumber(), bonus); }
    public void removeAvailableBonus(Bonus bonus) { this.availableBonuses.remove(bonus.getNumber()); }

    public boolean isSeeEmptyBonuses() { return this.isSeeEmptyBonuses; }
    public void setSeeEmptyBonuses() { this.isSeeEmptyBonuses = true; }

    public TreeMap<Integer, FreeBetBonus> getFreeBetBonuses() {
        this.freeBetBonuses.entrySet().removeIf(entry -> entry.getValue().isExpired());
        return this.freeBetBonuses;
    }
    public void addFreeBetBonus(FreeBetBonus bonus) { this.freeBetBonuses.put(bonus.getNumber(), bonus); }
    public void removeFreeBetBonus(FreeBetBonus bonus) { this.freeBetBonuses.remove(bonus.getNumber()); }

    public TreeMap<Integer, DepositBonus> getDepositBonuses() {
        this.depositBonuses.entrySet().removeIf(entry -> entry.getValue().isExpired());
        return this.depositBonuses;
    }
    public void addDepositBonus(DepositBonus bonus) { this.depositBonuses.put(bonus.getNumber(), bonus); }
    public void removeDepositBonus(DepositBonus bonus) { this.depositBonuses.remove(bonus.getNumber()); }

    public BetHistory<LocalDateTime, Bet> getBetHistory () { return this.betHistory; }
    public void addBetToHistory(Bet bet) { this.betHistory.put(bet.getTime(), bet); }

    public boolean deposit(double amount, WhoChangePlayerBalance who) {
        if (amount <= 0) {
            System.out.println(Utils.toError("System: ") + "Сумма должна быть положительной, больше 0!");
            return false;
        }
        if (who == WhoChangePlayerBalance.PLAYER && amount > limitChangeBalance) {
            System.out.println(getMessageForLimitChangeBalance());
            return false;
        }
        this.balance += amount;
        return true;
    }

    public boolean deposit(double amount, DepositBonus depositBonus) {
        if (amount <= 0) {
            System.out.println(Utils.toError("System: ") + "Сумма должна быть положительной, больше 0!");
            return false;
        }
        if (amount > limitChangeBalance) {
            System.out.println(getMessageForLimitChangeBalance());
            return false;
        }
        this.balance += (amount + depositBonus.getDepositBonusAmount(amount));
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