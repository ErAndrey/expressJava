package test.task_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>(List.of("[]"));
        for (String s : strings) {
            System.out.println("\"" + s + "\" - v1: " + isCorrect(s) + ", v2: " + isCorrectV2(s));
        }
    }

    public static boolean isCorrect(String string) {
        char[] chars = string.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : chars) {
            if (isOpen(c)) {
                stack.push(c);
            }
            if (isClosed(c)) {
                if (stack.isEmpty() || stack.peek() != getOpenForClosed(c)) return false;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    private static boolean isOpen(char symbol) {
        return symbol == '{' || symbol == '[' || symbol == '(';
    }

    private static boolean isClosed(char symbol) {
        return symbol == '}' || symbol == ']' || symbol == ')';
    }

    private static char getOpenForClosed(char symbol) {
        if (symbol == '}') return '{';
        if (symbol == ']') return '[';
        return '(';
    }

    public static boolean isCorrectV2(String string) {
        if (string == null || string.length() % 2 != 0) return false;
        Stack<Character> stack = new Stack<>();
        for (char c : string.toCharArray())
            if ("({[".indexOf(c) >= 0) stack.push(c);
            else if (stack.isEmpty() || ")}]".indexOf(c) != "({[".indexOf(stack.pop())) return false;
        return stack.isEmpty();
    }

}
