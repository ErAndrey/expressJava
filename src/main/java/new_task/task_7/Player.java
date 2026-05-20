package new_task.task_7;

public class Player {
    private final String name;
    private double balance;

    private String email;
    private boolean isConfirmedAccount;
    private int limitChangeBalance; // toDo разбить на пополнение и вывод

    private int totalGames;
    private int totalWins;
    private int totalLose;
    private int currentWins;
    private int maxWins;

    private double maxWinAmount;

    private double allIncome;
    private double allOutcome;
    private double totalWinAmount;
    private double totalLoseAmount;

    public Player(String name) {
        this.name = name;
        this.limitChangeBalance = 100_000;
    }

    public String getName() {return this.name;}
    public double getBalance() {return this.balance;}

    public String getEmail(){
        return this.email == null ? "Отсутствует" : this.email;
    }
    public void setEmail(String email) {this.email = email;}
    public boolean isConfirmedAccount() {return this.isConfirmedAccount;}
    public void setConfirmedAccount() {
        this.isConfirmedAccount = true;
        this.limitChangeBalance = 500_000;
    }
    public int getLimitChangeBalance() {return this.limitChangeBalance;}

    public double getWinRate() {
        double winRate = (this.totalWins * 100.0) / this.totalGames;
        return Math.round(winRate * 100.0) / 100.0;
    }
    public int getTotalGames() {return this.totalGames;}
    public int getTotalWins() {return this.totalWins;}
    public int getTotalLose() {return this.totalLose;}
    public int getCurrentWins() {return this.currentWins;}
    public int getMaxWins() {return this.maxWins;}

    public double getMaxWinAmount() {return this.maxWinAmount;}

    public double getAllIncome() {return this.allIncome;}
    public double getAllOutcome() {return this.allOutcome;}
    public double getTotalWinAmount() {return this.totalWinAmount;}
    public double getTotalLoseAmount() {return this.totalLoseAmount;}

    public void updateTotalWins() {this.totalWins++; this.totalGames++;}
    public void updateTotalLose() {this.totalLose++; this.totalGames++;}
    public void updateCurrentWins() {this.currentWins++;}
    public void resetCurrentWins() {this.currentWins = 0;}
    public void updateMaxWins(int count) {this.maxWins = count;}

    public void updateMaxWinAmount(double value) {this.maxWinAmount = value;}

    public void updateAllIncome(double value) {this.allIncome += value;}
    public void updateAllOutcome(double value) {this.allOutcome += value;}
    public void updateTotalWinAmount(double value) {this.totalWinAmount += value;}
    public void updateTotalLoseAmount(double value) {this.totalLoseAmount += value;}

    private String getMessageForLimitChangeBalance() {
        String text = Utils.toError("System: ") + "Текущий лимит " + Utils.formatCurrency(limitChangeBalance);
        return isConfirmedAccount ? text : text + ", вы можете повысить лимит в вашем профиле!";
    }

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