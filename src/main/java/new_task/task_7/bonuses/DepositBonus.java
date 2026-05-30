package new_task.task_7.bonuses;

import new_task.task_7.Utils;

import java.time.LocalDateTime;

public class DepositBonus extends Bonus {
    private final double percent;

    public DepositBonus(double percent, LocalDateTime fromDate, int daysAvailable, int hoursAvailable) {
        super(fromDate, daysAvailable, hoursAvailable);
        this.percent = percent;
    }

    public double getPercent() { return this.percent; }
    public double getDepositBonusAmount(double depositAmount) {
        double result = (depositAmount * percent) / 100.0;
        return Math.round(result * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return super.toString() + "получи дополнительные " + Utils.toSuccess(this.percent + "%") + " при пополнении!";
    }
}
