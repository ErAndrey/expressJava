package new_task.task_7.bonuses;

import new_task.task_7.Utils;

import java.time.LocalDateTime;

public abstract class Bonus {
    private static int count;

    private final int number;
    private final LocalDateTime toDate;
    private boolean isActivated;

    public Bonus(LocalDateTime fromDate, int hoursAvailable, int minutesAvailable) {
        this.number = ++count;
        this.toDate = fromDate.plusHours(hoursAvailable).plusMinutes(minutesAvailable);
    }

    public final int getNumber() { return this.number; }
    public final LocalDateTime getToDate() { return this.toDate; }
    public final boolean isExpired() { return LocalDateTime.now().isAfter(this.toDate); }
    public final void activate() { this.isActivated = true; }

    private String isActivatedText() { return this.isActivated ? "используйте" : "доступен"; }

    @Override
    public String toString() {
        return "Бонус " + Utils.toAccent("#" + this.number) + ", " + this.isActivatedText() + " до " + Utils.toInfo(Utils.formatDateTime(this.getToDate())) + ", ";
    }
}
