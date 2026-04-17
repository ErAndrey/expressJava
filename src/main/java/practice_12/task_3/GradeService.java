package practice_12.task_3;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class GradeService <T extends Number> {
    private final List<StudentGrade<T>> studentGrades;

    public GradeService() {
        this.studentGrades = new ArrayList<>();
    }

    public List<StudentGrade<T>> getStudentGrades() {
        return List.copyOf(this.studentGrades);
    }

    public synchronized void addGrade(StudentGrade<T> studentGrade) {
        if (studentGrade.mark() == null || !(studentGrade.mark().doubleValue() >= 0.0)) {
            throw new InvalidGradeException("Оценка невалидна");
        }
        this.studentGrades.add(studentGrade);
    }

    public double getAverageMarkForSubject(String subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Subject null");
        }
        List<StudentGrade<T>> filteredListBySubject = this.studentGrades
                .stream()
                .filter(grade -> grade.subject().equals(subject))
                .toList();
        if (filteredListBySubject.isEmpty()) {
            throw new NoSuchElementException("Оценок по предмету \"" + subject + "\" нет");
        }
        return Math.round(filteredListBySubject
                .stream()
                .collect(Collectors
                        .averagingDouble(grade -> grade.mark().doubleValue())
                ) * 100.0) / 100.0;
    }
}
