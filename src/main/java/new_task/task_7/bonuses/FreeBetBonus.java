package new_task.task_7.bonuses;

import new_task.task_7.Utils;

import java.time.LocalDateTime;

public class FreeBetBonus extends Bonus {
    private final int freeBet;
    private final double maxMultiplier;

    public FreeBetBonus(int freeBet, double maxMultiplier, LocalDateTime fromDate, int daysAvailable, int hoursAvailable) {
        super(fromDate, daysAvailable, hoursAvailable);
        this.freeBet = freeBet;
        this.maxMultiplier = maxMultiplier;
    }

    public int getFreeBet() { return this.freeBet; }
    public double getMaxMultiplier() { return this.maxMultiplier; }

    @Override
    public String toString() {
        return super.toString() + "фрибет " + Utils.toSuccess(Utils.formatCurrency(this.freeBet)) + " на следующую ставку c Кф до " + Utils.toAccent("x" + maxMultiplier);
    }
}
