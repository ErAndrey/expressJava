package practice_6;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static int numberOfUserHistory = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //1. Создайте ArrayList из 5 чисел. Добавьте ещё одно число в конец. Выведите весь список.

        List<Integer> list1 = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        list1.addLast(4);
        System.out.println(list1);

        //2. Напишите программу, которая выводит все чётные числа из ArrayList.

        list1.forEach(
                i -> {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
        );

        //3. Создайте ArrayList из строк. Найдите в нём самую длинную строку и выведите её.

        List<String> list3 = new ArrayList<>(List.of("Создайте ArrayList из строк. Найдите в нём самую длинную строку и выведите её.".split(" ")));
        int size = Integer.MIN_VALUE;
        String maxString = "";
        for (String s : list3) {
            if (s.length() > size) {
                size = s.length();
                maxString = s;
            }
        }
        System.out.println(maxString);
        System.out.println(list3);

        //4 Создайте ArrayList из целых чисел. Напишите программу, которая вычисляет и выводит сумму всех чисел в списке.

        List<Integer> list4 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 5, 9, 8, 0, -3));
        int sum = 0;
        for (Integer i : list4) {
            sum += i;
        }
        System.out.println(sum);

        //5 Создайте ArrayList из целых чисел. Напишите программу, которая находит и выводит максимальное число из списка.

        AtomicInteger maxNumber = new AtomicInteger(Integer.MIN_VALUE);
        list4.forEach(i -> {
            if (i > maxNumber.get()) {
                maxNumber.set(i);
            }
        });
        System.out.println(maxNumber);

        //1 Создайте LinkedList и добавьте в него 5 строк. Выведите все элементы списка.

        LinkedList<String> linkedList1 = new LinkedList<>();
        linkedList1.add("1");
        linkedList1.add("-1");
        linkedList1.add("3");
        linkedList1.add("4");
        linkedList1.add("67");
        System.out.println(linkedList1);

        //2 Реализуйте очередь задач с LinkedList. Добавьте 3 задачи и обработайте их в порядке поступления.

        LinkedList<Integer> linkedList2 = new LinkedList<>();
        linkedList2.add(1);
        linkedList2.add(2);
        linkedList2.add(3);
        System.out.println(linkedList2);
        System.out.println(linkedList2.pop());
        System.out.println(linkedList2);

        //3 Создайте LinkedList, содержащий несколько строк. Напишите программу, которая печатает первый и последний элементы списка.

        LinkedList<String> linkedList3 = new LinkedList<>(List.of("3", "4", "0"));
        System.out.println(linkedList3.getFirst());
        System.out.println(linkedList3.getLast());

        //5 Используйте ListIterator для прохода по LinkedList в обоих направлениях.

        ListIterator<String> stringListIterator = linkedList3.listIterator();

        while (stringListIterator.hasNext()) {
            System.out.println(stringListIterator.next());
        }

        System.out.println("/");

        while (stringListIterator.hasPrevious()) {
            System.out.println(stringListIterator.previous());
        }

        //1 Создайте HashSet из 5 чисел и выведите его содержимое.

        Set<Integer> set1 = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            set1.add(i);
        }
        System.out.println(set1);

        //2 Добавьте в HashSet 10 чисел. Проверьте, содержит ли он заданное число.

        Set<Integer> set2 = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set2.add(i);
        }
        System.out.println(set2.contains(scanner.nextInt()));

        //3 Реализуйте метод, который принимает List<String> и возвращает Set<String> без дубликатов.

        List<String> stringList33 = new ArrayList<>(List.of("1", "3", "3", "6", "323", "s", "S", "s"));
        System.out.println(mapListToSet(stringList33));

        //4 Создайте HashSet, содержащий набор имен. Напишите программу, которая проверяет, содержится ли ваше имя в множестве, и выводит соответствующее сообщение.

        Set<String> set4 = new HashSet<>();
        set4.add("Андрей");
        set4.add("Мила");
        set4.add("Саша");
        if (set4.contains(scanner.next())) {
            System.out.println("Имя содержится в Set");
        } else {
            System.out.println("Имя не содержится в Set");
        }

        //1 Создайте LinkedHashSet и добавьте в него 5 строк. Проверьте порядок элементов при выводе. & 2 Напишите метод, который добавляет элемент в LinkedHashSet, но не добавляет дубликаты.

        LinkedHashSet<String> linkedHashSet1 = new LinkedHashSet<>();
        linkedHashSet1.add("a");
        linkedHashSet1.add("b");
        linkedHashSet1.add("c");
        linkedHashSet1.add("z");
        linkedHashSet1.add("y");

        System.out.println(linkedHashSet1);
        addStringToSet(linkedHashSet1, "z");
        addStringToSet(linkedHashSet1, "u");
        System.out.println(linkedHashSet1);

        //1 Создайте TreeSet из 5 чисел и выведите его. Обратите внимание на порядок.

        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(13);
        treeSet.add(3);
        treeSet.add(155);
        treeSet.add(-13);
        treeSet.add(0);

        System.out.println(treeSet);

        //3 Найдите ближайшее большее и меньшее число к заданному в TreeSet.

        int searchNearFor = 5;

        System.out.println("Ближайшее минимальное к " + searchNearFor + " = " + getNearMin(searchNearFor, treeSet));
        System.out.println("Ближайшее максимальное к " + searchNearFor + " = " + getNearMax(searchNearFor, treeSet));

        //1 Создайте HashMap<String, Integer>, добавьте 5 пар (имя – возраст) и выведите все записи.

        HashMap<String, Integer> map1 = new HashMap<>();

        map1.put("Sasha", 17);
        map1.put("Pasha", 20);
        map1.put("Masha", 23);
        map1.put("Kasha", 1);
        map1.put("Lala", 67);

        System.out.println(map1);

        map1.forEach(
                (n, y) -> {
                    System.out.println("Имя: " + n + ", возраст: " + y);
                }
        );

        //2 Проверьте, есть ли определённое имя в HashMap.
        if (map1.containsKey(scanner.next())) {
            System.out.println("Имя есть");
        } else {
            System.out.println("Имя не найдено");
        }

        //3 Реализуйте метод, который печатает из HashMap всех пользователей младше 18 лет.
        printMinorsUser(map1);

        //1 Создайте LinkedHashMap и добавьте в него 5 элементов. Выведите их в порядке добавления.

        LinkedHashMap<String, Double> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("1", 20.0);
        linkedHashMap.put("4", 40.1);
        linkedHashMap.put("5", 25.5);
        linkedHashMap.put("2", 1.88);
        linkedHashMap.put("3", 0.01);

        linkedHashMap.forEach((s, d) -> {
            System.out.println(s + ", " + d);
        });

        //3 Создайте LinkedHashMap, который хранит историю просмотров пользователя (максимум 10 элементов).

        LinkedHashMap<Integer, String> linkedHashMap3 = new LinkedHashMap<>();

        addHistoryToHistoryMap("1", linkedHashMap3);
        addHistoryToHistoryMap("2", linkedHashMap3);
        addHistoryToHistoryMap("3", linkedHashMap3);
        addHistoryToHistoryMap("4", linkedHashMap3);
        addHistoryToHistoryMap("5", linkedHashMap3);
        addHistoryToHistoryMap("6", linkedHashMap3);
        addHistoryToHistoryMap("7", linkedHashMap3);
        addHistoryToHistoryMap("8", linkedHashMap3);
        addHistoryToHistoryMap("9", linkedHashMap3);
        addHistoryToHistoryMap("10", linkedHashMap3);
        addHistoryToHistoryMap("11", linkedHashMap3);
        addHistoryToHistoryMap("12", linkedHashMap3);
        addHistoryToHistoryMap("13", linkedHashMap3);
        addHistoryToHistoryMap("14", linkedHashMap3);
        addHistoryToHistoryMap("15", linkedHashMap3);
        addHistoryToHistoryMap("16", linkedHashMap3);
        addHistoryToHistoryMap("17", linkedHashMap3);
        System.out.println(linkedHashMap3);

        //1 Создайте TreeMap и добавьте 5 ключей (имена) и значений (баллы). Выведите отсортированные данные.

        TreeMap<String, Integer> map5 = new TreeMap<>();

        map5.put("Andrew", 5);
        map5.put("Pasha", 5);
        map5.put("Dasha", 4);
        map5.put("Sasha", 2);
        map5.put("Danila", 3);
        map5.put("Ane", 5);

        System.out.println(map5);

        map5.forEach((n, m) -> {
            System.out.println("Имя: " + n + ", Оценка: " + m);
        });

        // Найдите минимальный и максимальный ключ в TreeMap.
        System.out.println(map5.firstKey());
        System.out.println(map5.lastKey());

        // 1 Создайте PriorityQueue и добавьте 5 чисел. Выведите их в порядке удаления.

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        priorityQueue.offer(1);
        priorityQueue.offer(2);
        priorityQueue.offer(4);
        priorityQueue.offer(5);
        priorityQueue.offer(3);

        System.out.println(priorityQueue);

        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }

        // 2 Используйте ArrayDeque как стек: добавьте элементы и извлеките их в обратном порядке.

        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst(5);
        arrayDeque.addFirst(4);
        arrayDeque.addFirst(3);
        arrayDeque.addFirst(2);
        arrayDeque.addFirst(1);
        System.out.println(arrayDeque);
        System.out.println("/");
        System.out.println(arrayDeque.pollLast());
        System.out.println(arrayDeque.pollLast());
        System.out.println(arrayDeque.pollLast());
        System.out.println(arrayDeque.pollLast());
        System.out.println(arrayDeque.pollLast());

    }

    public static Set<String> mapListToSet(List<String> list) {
        return new HashSet<>(list);
    }

    public static void addStringToSet(Set<String> set, String element) {
        if (set.contains(element)) {
            System.out.println("Сет содержит этот элемент, добавлять не буду");
            return;
        }
        set.add(element);
        System.out.println("Элемент '" + element + "' добавлен");
    }

    public static int getNearMin(int i, Set<Integer> set) {
        int min = Integer.MIN_VALUE;
        for (Integer n : set) {
            if (n > min && i > n) {
                min = n;
            }
        }
        return min;
    }

    public static int getNearMax(int i, Set<Integer> set) {
        int max = Integer.MAX_VALUE;
        for (Integer n : set) {
            if (n < max && i < n) {
                max = n;
            }
        }
        return max;
    }

    public static void printMinorsUser(Map<String, Integer> map) {
        map.forEach((name, year) -> {
            if (year < 18) {
                System.out.println(name + ", " + year);
            }
        });
    }

    public static void addHistoryToHistoryMap(String url, Map<Integer, String> history) {
        numberOfUserHistory++;
        if (history.size() >= 10) {
            Integer oldestKey = history.keySet().iterator().next();
            history.remove(oldestKey);
        }
        history.put(numberOfUserHistory, url);

    }

}
