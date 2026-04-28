package new_task.task_6;

import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        System.out.println(findMaxNumberFromStringV1("asd342dcm382d3"));

    }
    private static int findMaxNumberFromStringV1(String string) {
        if (string == null || string.isEmpty()) throw new IllegalArgumentException();
        int max = -1;
        for (char c : string.toCharArray()) {
            int index = "0123456789".indexOf(c);
            if (index == 9) return 9;
            if (index > max) max = index;
        }
        if (max == -1) throw new NoSuchElementException("String has not contains number");
        return max;
    }
}
