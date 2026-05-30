package new_task.task_7.bet;

import new_task.task_7.bonuses.FreeBetBonus;

public class Bet {
    private final BetType type;

    private FreeBetBonus freeBetBonus;
    private int amount;

    public Bet(int amount) {
        this.type = BetType.REGULAR;
        this.amount = amount;
    }

    public Bet(FreeBetBonus freeBetBonus) {
        this.type = BetType.FREE_BET;
        this.freeBetBonus = freeBetBonus;
        this.amount = freeBetBonus.getFreeBet();
    }

    public BetType getType() { return this.type; }
    public int getAmount() { return this.amount; }
    public void setAmount(int newAmount) { this.amount = newAmount; }
    public FreeBetBonus getFreeBetBonus() { return this.freeBetBonus; }
}
