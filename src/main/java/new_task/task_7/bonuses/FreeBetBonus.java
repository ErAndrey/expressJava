package new_task.task_7.bonuses;

import new_task.task_7.Utils;

import java.time.LocalDateTime;

public class FreeBetBonus extends Bonus {
    private final int freeBetAmount;
    private final double maxMultiplier;

    public FreeBetBonus(int freeBetAmount, double maxMultiplier, LocalDateTime fromDate, int hoursAvailable, int minutesAvailable) {
        super(fromDate, hoursAvailable, minutesAvailable);
        this.freeBetAmount = freeBetAmount;
        this.maxMultiplier = maxMultiplier;
    }

    public int getFreeBetAmount() { return this.freeBetAmount; }
    public double getMaxMultiplier() { return this.maxMultiplier; }

    @Override
    public String toString() {
        return super.toString() + "фрибет " + Utils.toSuccess(Utils.formatCurrency(this.freeBetAmount)) + " на следующую ставку c Кф до " + Utils.toAccent("x" + maxMultiplier);
    }
}
