package practice_8;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        MathOperation add = (x, y) -> x + y;
        MathOperation sub = (x, y) -> x - y;
        MathOperation mul = (x, y) -> x * y;
        MathOperation div = (x, y) -> (double) Math.round((double) x / y * 100) / 100.0;

        int x = 4, y = 7;
        System.out.println("Для чисел: " + x + ", " + y);
        System.out.println("add = " + add.calculate(x, y));
        System.out.println("sub = " + sub.calculate(x, y));
        System.out.println("mul = " + mul.calculate(x, y));
        System.out.println("div = " + div.calculate(x, y));

        //

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous class!");
            }
        };

        runnable.run();

        //

        Predicate<Integer> predicate = i -> i % 2 == 0;

        System.out.println(x + " четное - " + predicate.test(x));
        System.out.println(y + " четное - " + predicate.test(y));

        //

        Function<String, Integer> function = string -> string.length();

        System.out.println(function.apply("1234"));
        System.out.println(function.apply(""));

        //

        Consumer<String> consumer = string -> System.out.println(string);

        consumer.accept("Test");

        //

        List<String> strings = new ArrayList<>(List.of("Напишите программу, которая принимает список строк и удаляет из него все строки длиной 5 символов и менее, используя Stream API."
                .replace(",", "").replace(".", "").split("\\s")));

        System.out.println(strings);
        System.out.println(getFilteredStringsWhoLengthMoreThan5(strings));

        //

        List<Integer> integers = new ArrayList<>(Arrays.asList(-1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 10));

        System.out.println(integers);
        System.out.println(getFilteredIntegersWhenDivOn5(integers));

        //

        System.out.println(mapStringListToIntList(strings));

        //

        integers.add(Integer.MAX_VALUE);
        System.out.println(mapIntegersToSquareIntegers(integers));

        //

        System.out.println(getNewListWithoutDuplicates(mapIntegersToSquareIntegers(integers)));
        System.out.println(getNewListWithoutDuplicates(strings));

        //

        System.out.println(getMaxNumberFromList(integers));
        System.out.println(getMaxNumberFromList(new ArrayList<>()));

        //

        System.out.println(getMinNumberFromList(integers));
        System.out.println(getMinNumberFromList(new ArrayList<>()));

        //

        System.out.println(getSumAllNumbersFromList(integers));

        //

        strings.add("буратино");
        System.out.println(getFirstStringWhoStartsWithB(strings));
        strings.add("Буратино");
        System.out.println(getFirstStringWhoStartsWithB(strings));

        //

        Predicate<Integer> noEven = i -> i % 2 != 0;
        Predicate<String> lenMoreThan10 = s -> s.length() > 8;

        System.out.println(checkListOnMyPredicate(integers, noEven));
        System.out.println(checkListOnMyPredicate(strings, lenMoreThan10));

        //

        System.out.println(strings);
        strings.add(null);
        System.out.println(getGroupedStringForFirstSymbol(strings));

        //

        System.out.println(getGroupedIntegersForEven(integers));

        //

        System.out.println(getAverageForListNumbers(integers));

    }

    public static List<String> getFilteredStringsWhoLengthMoreThan5(List<String> strings) {
        return strings.stream()
                .filter(string -> string.length() > 5)
                .toList();
    }

    public static List<Integer> getFilteredIntegersWhenDivOn5(List<Integer> integers) {
        return integers.stream()
                .filter(integer -> integer % 5 == 0)
                .collect(Collectors.toList());
    }

    public static List<Integer> mapStringListToIntList(List<String> strings) {
        return strings.stream()
                .map(String::length)
                .toList();
    }

    public static List<Integer> mapIntegersToSquareIntegers(List<Integer> integers) {
        return integers.stream()
                .map(integer -> integer * integer)
                .collect(Collectors.toList());
    }

    public static <T> List<T> getNewListWithoutDuplicates(List<T> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static int getMaxNumberFromList(List<Integer> numbers) {
        return numbers.stream()
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    public static int getMinNumberFromList(List<Integer> numbers) {
        return numbers.stream()
                .min(Comparator.naturalOrder())
                .orElse(0);
    }

    public static int getSumAllNumbersFromList(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(n -> n)
                .sum();
    }

    public static String getFirstStringWhoStartsWithB(List<String> strings) {
        return strings.stream()
                .filter(string -> string.startsWith("Б"))
                .findFirst()
                .orElse("Строки, начинающейся с \"Б\" не найдено");
    }

    public static <T> boolean checkListOnMyPredicate(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .anyMatch(predicate);
    }

    public static Map<Character, List<String>> getGroupedStringForFirstSymbol(List<String> strings) {
        return strings.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(string -> string.charAt(0), Collectors.toList()));
    }

    public static Map<String, List<Integer>> getGroupedIntegersForEven(List<Integer> integers) {
        return integers.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(integer -> integer % 2 == 0 ? "Четные" : "Нечетные"));
    }

    public static Double getAverageForListNumbers(List<Integer> integers) {
        return integers.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.averagingInt(Integer::intValue));
    }
}
