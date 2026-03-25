package test.task_3;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, null, 25, null, 30, 5, 15, null, 20);
        System.out.println(averageOfSquaresAboveTen(numbers));
    }
    public static double averageOfSquaresAboveTen(List<Integer> numbers) {
        return numbers
                .stream()
                .filter(Objects::nonNull)
                .filter(number -> number > 10)
                .mapToInt(n -> n * n)
                .average()
                .stream()
                .map(avg -> Math.round(avg * 100) / 100.0)
                .findFirst()
                .orElse(0.0);
    }
}
