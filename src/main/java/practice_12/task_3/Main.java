package practice_12.task_3;

import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        StudentGrade<Integer> andrew = new StudentGrade<>("andrew", "x", 1);
        StudentGrade<Integer> lera = new StudentGrade<>("lera", "y", 14);
        StudentGrade<Integer> danil = new StudentGrade<>("danil", "x", 23);
        StudentGrade<Integer> nikita = new StudentGrade<>("nikita", "y", 17);
        StudentGrade<Integer> vera = new StudentGrade<>("vera", "x", 14);

        GradeService<Integer> gradeService = new GradeService<>();


        gradeService.addGrade(andrew);
        gradeService.addGrade(lera);
        gradeService.addGrade(danil);
        gradeService.addGrade(nikita);
        gradeService.addGrade(vera);

        try {
            System.out.println(gradeService.getAverageMarkForSubject("y"));
        } catch (NoSuchElementException e) {
            System.out.println("Отловили " + e.getMessage());
        }
    }
}
