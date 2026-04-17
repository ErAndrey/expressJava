package practice_12.task_6;

import java.util.*;
import java.util.stream.Collectors;

public class TaskService <T> {
    private final List<Task<T>> tasks;

    public TaskService() {
        this.tasks = new ArrayList<>();
    }

    public void addTaskToList(Task<T> task) {
        if (task.getId() == null) throw new IllegalArgumentException("Id задачи null");
        if (!this.tasks.contains(task)) {
            this.tasks.add(task);
        }
    }

    public synchronized boolean removeTaskFromList(T taskId) {
        if (taskId == null) throw new IllegalArgumentException("Cant remove for null task id");
        return this.tasks.removeIf(task -> task.getId().equals(taskId));
    }

    public Map<String, List<Task<T>>> filterTasksByStatus() {
        return this.tasks
                .stream()
                .collect(Collectors.groupingBy(k -> k.isClosed() ? "Закрытые" : "Открытые", Collectors.toList()));
    }

    public List<Task<T>> filterTaskListByPriority(Priority priority) {
        if (priority == null) throw new IllegalArgumentException("Cant filter for null");
        return this.tasks
                .stream()
                .filter(task -> task.getPriority().equals(priority))
                .toList();
    }

    public Map<Priority, List<Task<T>>> filterTasksByPriority() {
        return this.tasks
                .stream()
                .collect(Collectors.groupingBy(Task::getPriority, Collectors.toList()));
    }

    public List<Task<T>> sortTaskByDate() {
        return this.tasks
                .stream()
                .sorted(Comparator.comparing(Task::getDate))
                .toList();
    }

    public int size() {
        return this.tasks.size();
    }
}
