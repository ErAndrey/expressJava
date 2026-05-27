package new_task.task_7.bonuses;

import new_task.task_7.Utils;

import java.time.LocalDateTime;

public class CashBonus extends Bonus {
    private final double amount;

    public CashBonus(double amount, LocalDateTime fromDate, int daysAvailable, int hoursAvailable) {
        super(fromDate, daysAvailable, hoursAvailable);
        this.amount = amount;
    }

    public double getAmount() { return this.amount; }

    @Override
    public String toString() {
        return super.toString() + "получи " + Utils.toSuccess(Utils.formatCurrency(this.amount)) + " на свой счет!";
    }
}
