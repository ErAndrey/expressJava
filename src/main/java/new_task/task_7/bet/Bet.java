package new_task.task_7.bet;

public class Bet {
    private final BetType type;

    //toDo добавить фри бет, сетить в selectBet, ретернить фрибет в resolveBet
    private int amount;

    public Bet(BetType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public BetType getType() { return this.type; }
    public int getAmount() { return this.amount; }
    public void setAmount(int newAmount) { this.amount = newAmount; }
}
