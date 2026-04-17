package new_task.task_5;

import java.util.*;

public class Unique {
    public static void main (String[] args) {
        String text = "Лягушата на пруду разыгрались в чехарду Ква, ква, ква, ква. Ква, ква, ква, ква. Ква, ква, ква, ква. И нырнули в глубь пруда!!!";
        String clearText = text.replaceAll("[,.!]", " ").toLowerCase().replaceAll("\\s+", " ");

        System.out.println(clearText);

        String[] slova = clearText.split(" ");

        System.out.println(Arrays.toString(slova));

        TreeSet<String> words = new TreeSet<>(Arrays.asList(slova));
        List<String> list = new ArrayList<>(Arrays.asList(slova));

        System.out.println("Было слов: " + list.size());
        System.out.println(words);
        System.out.println("Уникальных слов: " + words.size());
    }
}
