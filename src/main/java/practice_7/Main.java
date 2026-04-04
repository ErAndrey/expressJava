package practice_7;

import practice_7.classes.Box;
import practice_7.classes.NumberBox;
import practice_7.classes.Pair;
import practice_7.exceptions.IllegalUserAgeException;
import practice_7.exceptions.InvalidEmailException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //1. Обработка проверяемого исключения

        try {
            FileReader fileReader = new FileReader("data.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        //2. Обработка непроверяемого исключения

        divide(1, 2);
        //divide(1, 0);

        //3. Создание и использование собственного проверяемого исключения

        try {
            validateUserAge(1);
            validateUserAge(-1);
        } catch (IllegalUserAgeException e) {
            System.out.println(e.getMessage());
        }

        //4. Создание и использование собственного непроверяемого исключения

        validateEmail("test.andrey@mail.ru");
        validateEmail("andrey@list.r");
        //validateEmail("=.r");

        //1. Задача на дженерик класс

        Box<Integer> integerBox = new Box<>();
        Box<Character> characterBox = new Box<>();
        Box<String> stringBox = new Box<>();

        integerBox.setC(112);
        characterBox.setC('%');
        stringBox.setC("Name");

        System.out.println(integerBox.getC());
        System.out.println(characterBox.getC());
        System.out.println(stringBox.getC());

        //2. Задача на дженерик метод

        String[] strings = {"1", "2", "4"};
        Character[] characters = {'!', '%', '"'};

        printArray(strings);
        printArray(characters);

        //3. Задача на дженерик с двумя типами данных

        Pair<Integer, Double> pair = new Pair<>();

        System.out.println(pair.getX() + " , " + pair.getY());
        pair.setX(123);
        pair.setY(20.43);
        System.out.println(pair.getX() + " , " + pair.getY());

        //Создайте класс NumberBox<T extends Number>, который хранит только числа и возвращает их сумму.

        NumberBox<Integer> integerNumberBox = new NumberBox<>();
        NumberBox<Double> doubleNumberBox = new NumberBox<>();
        NumberBox<Float> floatNumberBox = new NumberBox<>();

        integerNumberBox.addNumberToBox(1);
        integerNumberBox.addNumberToBox(3);
        integerNumberBox.addNumberToBox(6);
        System.out.println(integerNumberBox.getSumOfBox());

        System.out.println(doubleNumberBox.getSumOfBox());
        doubleNumberBox.addNumberToBox(29.0);
        doubleNumberBox.addNumberToBox(-1134.099993);
        System.out.println(doubleNumberBox.getSumOfBox());

        floatNumberBox.addNumberToBox(0.9443f);
        System.out.println(floatNumberBox.getSumOfBox());

        //Напишите обобщённый метод, который принимает List<T extends Number> и вычисляет сумму элементов.

        List<Integer> integerList = new ArrayList<>(List.of(111, 4, 5));
        System.out.println(calculateSumOfNumbers(integerList));

        //

        List<Double> doubles = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();

        addIntNumberToList(integers, 1);
        System.out.println(integers);

    }

    public static void addIntNumberToList(List<? super Integer> list, int value) {
        list.add(value);
    }

    public static <T extends Number> double calculateSumOfNumbers(List<T> list) {
        double res = 0.0;
        for (T t : list) {
            res += t.doubleValue();
        }
        return res;
    }

    public static <T> void printArray(T[] array) {
        for (T t : array) {
            System.out.println(t);
        }
    }

    public static void validateEmail(String email) {
        if (email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            System.out.println("Email \"" + email + "\" - валиден");
            return;
        }
        throw new InvalidEmailException("Email \"" + email + "\" - не валиден");
    }

    public static void validateUserAge(int age) throws IllegalUserAgeException {
        if (age > 150 || age < 0) {
            throw new IllegalUserAgeException("Возраст невалиден, должен быть в пределах: [0, 150]");
        }
        System.out.println("Возраст валиден");
    }

    public static void divide(int x, int y) {
        if (y == 0) {
            throw new IllegalArgumentException("На ноль делить нельзя!");
        }
        System.out.println(x + " / " + y + " = " + (double) x / y);
    }
}
