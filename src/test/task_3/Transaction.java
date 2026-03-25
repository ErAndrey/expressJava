package test.task_3;

import java.time.LocalDate;

public record Transaction (String id, String userId, double amount, String category, LocalDate date) {}
