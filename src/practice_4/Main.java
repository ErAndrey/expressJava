package practice_4;

import java.util.Scanner;

import static practice_4.Mark.F;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Задачи на if-else
        //1. Определение знака числа
        int n = scanner.nextInt();

        if (n > 0)
            System.out.println("Число больше 0");
        else if (n < 0)
            System.out.println("Число меньше 0");
        else
            System.out.println("Число равно 0");


        //2. Поиск наибольшего из двух чисел
        int x = scanner.nextInt(), y = scanner.nextInt();

        int max = x >= y ? x : y;
        System.out.println(max);


        //3. Вывод оценки по шкале 1–5
        System.out.println(getMark(scanner.nextInt()));


        //4. Проверка на чётность
        System.out.println(isEven(scanner.nextInt()));


        //5. Определение размера скидки по возрасту
        System.out.println(getDiscountForYears(17));
        System.out.println(getDiscountForYears(54));
        System.out.println(getDiscountForYears(69));


        //6. Оценка результата теста по баллам
        System.out.println(getMarkFrom(90));
        System.out.println(getMarkFrom(75));
        System.out.println(getMarkFrom(60));
        System.out.println(getMarkFrom(3));


        //1. Вывод дня недели по номеру
        System.out.println(scanner.nextInt());


        //2. Стоимость билета по дню недели
        int z = scanner.nextInt();
        System.out.println(getPriceOfTicketForADay(z));


        //3. Перевод числовых оценок в буквенные (A–F)
        getMarkForNumber(scanner.nextInt());


        //4. Обработка текстовых команд
        readCommand(scanner.next());


        //5. Простой калькулятор с использованием switch
        calculate(scanner.nextInt(), scanner.next(), scanner.nextInt());


        //1. Вывод чисел от 1 до 100, делящихся на 3
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0) {
                System.out.println(i);
            }
        }


        //2. Сумма чисел от 1 до n

        int sum = 0;
        int c = scanner.nextInt();
        if (c >= 0) {
            for (int i = 1; i <= c; i++) {
                sum += i;
            }
        } else {
            for (int i = 1; i >= c; i--) {
                sum += i;
            }
        }
        System.out.println(sum);


        //3. Таблица умножения для числа
        int o = scanner.nextInt();
        for (int i = 1; i <= 10; i++) {
            System.out.println(n + " * " + i + " = " + i * o);
        }


        //4. Проверка на простое число
        System.out.println(isPrime(101));


        //5. Вывод чисел от 1 до 10
        int u = 1;
        for (int i = u; i <= 10; i++) {
            System.out.println(i);
        }


        //1. Вычисление факториала с помощью while
        int b = scanner.nextInt();
        int res = 1;
        int i = 2;
        while (i <= b) {
            res *= i;
            i++;
        }
        System.out.println(res);


        //2. Вывод всех чётных чисел до заданного
        printEvenNumbers(scanner.nextInt());


        //3. Обратный отсчёт от введённого числа до 1
        int p = scanner.nextInt();
        while (p >= 1) {
            System.out.println(p);
            p--;
        }


        //1. Запрос положительного числа
        int l;
        do {
            l = scanner.nextInt();
        } while (l < 0);


        //2. Проверка пароля
        String password = scanner.next();
        String enter;
        do {
            enter = scanner.next();
        } while (!enter.equals(password));


        //3. Вывод чисел от 1 до 10 с использованием do-while
        int k = 1;
        do {
            System.out.println(k);
            k++;
        } while (k <= 10);


        //4. Завершение программы по команде "exit"
        String str;
        do {
            str = scanner.next();
        } while (!str.equals("exit"));


        //5. Подсчёт количества цифр в числе
        int j = scanner.nextInt();
        int count = 0;
        do {
            j /= 10;
            count++;
        } while (j > 0);
        System.out.println(count);


        //1. Сумма чисел до первого отрицательного (использовать break)
        int sums = 0;
        int h;
        do {
            h = scanner.nextInt();
            if (h < 0) {
                break;
            }
            sums += h;
        } while (true);
        System.out.println(sums);


        //2. Пропуск чисел, делящихся на 3 (использовать continue)
        for (int g = 1; g <= 20; g++) {
            if (g % 3 == 0) {
                continue;
            }
            System.out.println(g);
        }


        //3. Вывод только положительных чисел (использовать continue)
        int t = 0;
        int r;
        while (t < 5) {
            r = scanner.nextInt();
            if (r >= 0) {
                System.out.println(r);
            }
            t++;
        }


        //4. Ввод строк до команды "stop" (использовать break)
        String st;
        while (true) {
            st = scanner.next();
            if (st.equals("stop")) {
                break;
            }
        }

    }

    public static String getMark(int mark) {
        String s = "";
        switch (mark) {
            case 5:
                s = "Отлично";
                break;
            case 4:
                s = "Хорошо";
                break;
            case 3:
                s = "Удовлетворительно";
                break;
            case 2, 1:
                s = "Неудовлетворительно";
                break;
            default:
                s = "Такой оценки нет";
        }
        return s;
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static String getDiscountForYears(int years) {
        String res = "без скидки";
        if (years < 18 && years >= 0) {
            res = "25%";
        } else if (years >= 65 && years <= 100) {
            res = "30%";
        }
        return res;
    }

    public static String getMarkFrom(int mark) {
        String res = "Неудовлетворительно";
        if (mark >= 90) {
            res = "Отлично";
        } else if (mark >= 75) {
            res = "Хорошо";
        } else if (mark >= 60) {
            res = "Удовлетворительно";
        }
        return res;
    }

    public static void getStringDayFromNumber(int day) {
        String res = "Такого дня нет";
        switch (day) {
            case 1 -> res = "Четверг";
            case 2 -> res = "Пятница";
            case 3 -> res = "Суббота";
            case 4 -> res = "Воскресенье";
            case 5 -> res = "Понедельник";
            case 6 -> res = "Вторник";
            case 7 -> res = "Среда";
        }

        System.out.println(res);
    }

    public static int getPriceOfTicketForADay(int day) {
        int price = 0;
        switch (day) {
            case 1, 2, 3, 4, 5 -> price = 300;
            case 6, 7 -> price = 450;
        }
        return price;
    }

    public static void getMarkForNumber(int mark) {
        if (mark < 0 || mark > 100) {
            return;
        }
        Mark mark1 = F;
        switch (mark / 10) {
            case 9, 10 -> mark1 = Mark.A;
            case 8 -> mark1 = Mark.B;
            case 7 -> mark1 = Mark.C;
            case 6 -> mark1 = Mark.D;
        }
        System.out.println(mark1);
    }

    public static void readCommand(String command) {
        switch (command) {
            case "start" -> System.out.println("start");
            case "stop" -> System.out.println("stop");
            case "restart" -> System.out.println("restart");
            case "status" -> System.out.println("status");
        }

    }

    public static void calculate(int x, String operation, int y) {
        if (operation.equals("/") && y == 0) {
            System.out.println("Делить на 0 низя");
            return;
        }
        switch (operation) {
            case "+" -> System.out.println(x + y);
            case "-" -> System.out.println(x - y);
            case "*" -> System.out.println(x * y);
            case "/" -> System.out.println((double) x / y);
            case "%" -> System.out.println(x % y);
            default -> System.out.println("Нет такой операции");
        }
    }

    public static boolean isPrime(int n) {
        n = Math.abs(n);

        boolean isPrime = true;

        int x = n % 2 == 0 ? 1 : 0;

        for (int i = 4; i <= n; i++) {
            if (n % i == 0)
                x++;
        }

        if (x >= 2) {
            isPrime = false;
        }

        return isPrime;
    }

    public static void printEvenNumbers(int last) {
        int i = 1;
        if (last >= 0) {
            while (i <= last) {
                if (isEven(i)) {
                    System.out.println(i);
                }
                i++;
            }
        } else {
            while (i >= last) {
                if (isEven(i)) {
                    System.out.println(i);
                }
                i--;
            }
        }
    }
}
