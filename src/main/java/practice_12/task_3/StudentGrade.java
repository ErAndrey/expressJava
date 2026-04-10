package practice_12.task_3;

public record StudentGrade<T extends Number>(String name, String subject, T mark) {}