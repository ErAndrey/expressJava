package new_task.task_7.bonuses;

import new_task.task_7.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Bonus {
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private static int count;

    private final int number;
    private final LocalDateTime toDate;

    public Bonus(LocalDateTime fromDate, int daysAvailable, int hoursAvailable) {
        this.number = ++count;
        this.toDate = fromDate.plusDays(daysAvailable).plusHours(hoursAvailable);
    }

    public final int getNumber() { return this.number; }
    public final LocalDateTime getToDate() { return this.toDate; }
    public final boolean isExpired() { return LocalDateTime.now().isAfter(this.toDate); }

    @Override
    public String toString() {
        return "Бонус " + Utils.toAccent("#" + this.number) + ", доступен до " + Utils.toInfo(Bonus.FORMATTER.format(this.getToDate())) + ", ";
    }
}
