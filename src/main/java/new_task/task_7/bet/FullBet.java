package new_task.task_7.bet;

import new_task.task_7.Multipliers;
import new_task.task_7.Utils;
import new_task.task_7.bet_history.BetResult;
import new_task.task_7.bonuses.FreeBetBonus;

import java.time.LocalDateTime;

public final class FullBet {
    private final LocalDateTime time;
    private final String gameName;

    private final BetType type;

    private FreeBetBonus freeBetBonus;
    private int betAmount;

    private double multiplier;
    private double winAmount;
    private BetResult result;

    public FullBet(String gameName, FreeBetBonus freeBetBonus) {
        this.time = LocalDateTime.now();
        this.gameName = gameName;
        this.freeBetBonus = freeBetBonus;
        this.type = BetType.FREE_BET;
    }

    public FullBet(String gameName, int betAmount) {
        this.time = LocalDateTime.now();
        this.gameName = gameName;
        this.betAmount = betAmount;
        this.type = BetType.REGULAR;
    }

    public BetType getType() { return this.type; }
    public FreeBetBonus getFreeBetBonus() { return this.freeBetBonus; }

    public int getAmount() { return this.betAmount; }
    public void setAmount(int newAmount) { this.betAmount = newAmount; }

    //public void setMultiplier(double multiplier) { this.multiplier = Multipliers.getActualMultiplier(this, multiplier); }
    public void setWinAmount(double winAmount) { this.winAmount = winAmount; }
    public void setResult(BetResult result) { this.result = result; }

    public LocalDateTime getTime() { return this.time; }

    private String freeBetText() { return this.type == BetType.FREE_BET ? " [" + Utils.toAccent("Фрибет") + "]": ""; }
    private String resultText() {
        return switch (this.result) {
            case WIN -> Utils.toSuccess(" # ") + gameName + Utils.formatCurrency(betAmount) + " * " + this.multiplier + Utils.toSuccess(" -> ") + Utils.formatCurrency(winAmount) + freeBetText();
            case LOSE -> Utils.toError(" # ") + gameName + Utils.toError(" -> ") + Utils.formatCurrency(betAmount) + freeBetText();
            case RETURN -> Utils.toInfo(" # ") + gameName + Utils.toInfo(" -> ") + Utils.formatCurrency(betAmount) + freeBetText();
        };
    }

    @Override
    public String toString() {
        return Utils.formatDateTime(time) + resultText();
    }
}
