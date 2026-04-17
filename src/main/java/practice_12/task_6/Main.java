package practice_12.task_6;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Task<Integer> task1 = new Task<>(1, Priority.HIGH, LocalDate.of(2026, 4, 15));
        Task<Integer> task2 = new Task<>(2, Priority.LOW, LocalDate.of(2026, 3, 12));
        Task<Integer> task3 = new Task<>(3, Priority.LOW, LocalDate.of(2026, 4, 5));
        Task<Integer> task4 = new Task<>(4, Priority.BLOCKER, LocalDate.of(2026, 1, 27));
        Task<Integer> task5 = new Task<>(5, Priority.MEDIUM, LocalDate.of(2026, 3, 30));
        Task<Integer> task6 = new Task<>(1, Priority.HIGH, LocalDate.of(2026, 2, 11));

        TaskService<Integer> integerTaskService = new TaskService<>();

        integerTaskService.addTaskToList(task1);
        integerTaskService.addTaskToList(task2);
        integerTaskService.addTaskToList(task3);
        integerTaskService.addTaskToList(task4);
        integerTaskService.addTaskToList(task5);
        integerTaskService.addTaskToList(task6);

        System.out.println(integerTaskService.size());

        System.out.println("filterTasksByStatus: " + integerTaskService.filterTasksByStatus());
        System.out.println("filterTaskListByPriority LOW: " + integerTaskService.filterTaskListByPriority(Priority.LOW));
        System.out.println("filterTasksByPriority: " + integerTaskService.filterTasksByPriority());
        System.out.println("sortTaskByDate: " + integerTaskService.sortTaskByDate());

        System.out.println(integerTaskService.removeTaskFromList(5));
        System.out.println(integerTaskService.size());

        System.out.println(integerTaskService.removeTaskFromList(5));
        System.out.println(integerTaskService.size());

        System.out.println(integerTaskService.removeTaskFromList(1));
        System.out.println(integerTaskService.size());

        System.out.println(integerTaskService.removeTaskFromList(1));
        System.out.println(integerTaskService.size());
    }
}
