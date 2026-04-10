package practice_12.task_6;

import java.time.LocalDate;
import java.util.Objects;

public class Task<T> {
    private final T id;
    private boolean isClosed;
    private Priority priority;
    private final LocalDate date;

    public Task(T id, Priority priority, LocalDate date) {
        this.id = id;
        this.priority = priority;
        this.isClosed = false;
        this.date = date;
    }

    public T getId() {
        return id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void closedTask() {
        this.isClosed = true;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "\"" + this.id + " , " + this.date.getDayOfMonth() + "." + this.date.getMonth() + "." + this.date.getYear() + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task<?> task)) return false;
        return Objects.equals(this.id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
