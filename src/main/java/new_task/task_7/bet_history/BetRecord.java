package new_task.task_7.bet_history;

import new_task.task_7.Utils;
import new_task.task_7.bet.Bet;
import new_task.task_7.bet.BetType;

import java.time.LocalDateTime;

public class BetRecord {

    private final String gameName;
    private final BetType type;
    private final int betAmount;
    private final BetResult result;
    private final double winAmount;
    private final LocalDateTime time;

    public BetRecord(String gameName, Bet bet, double winAmount) {
        this.gameName = gameName;
        this.betAmount = bet.getAmount();
        this.type = bet.getType();
        this.result = BetResult.WIN;
        this.winAmount = winAmount;
        this.time = LocalDateTime.now();
    }

    public BetRecord(String gameName, Bet bet, BetResult betResult) {
        this.gameName = gameName;
        this.betAmount = bet.getAmount();
        this.type = bet.getType();
        this.result = betResult;
        this.winAmount = 0;
        this.time = LocalDateTime.now();
    }

    public LocalDateTime getTime() { return this.time; }
    private String freeBetText() { return this.type == BetType.FREE_BET ? " [" + Utils.toAccent("Фрибет") + "]": ""; }

    private String resultText() {
        return switch (this.result) {
            case WIN -> Utils.toSuccess(" # ") + gameName + " * " + Utils.formatCurrency(betAmount) + Utils.toSuccess(" -> ") + Utils.formatCurrency(winAmount) + freeBetText();
            case LOSE -> Utils.toError(" # ") + gameName + Utils.toError(" -> ") + Utils.formatCurrency(betAmount) + freeBetText();
            case RETURN -> Utils.toInfo(" # ") + gameName + Utils.toInfo(" -> ") + Utils.formatCurrency(betAmount) + freeBetText();
        };
    }

    @Override
    public String toString() {
        return Utils.formatDateTime(time) + resultText();
    }

}
