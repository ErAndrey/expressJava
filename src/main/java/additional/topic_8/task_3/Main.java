package additional.topic_8.task_3;

import java.util.Stack;

public class Main {
    private static <T> void addElementToStack(Stack<T> stack, T element) {
        stack.push(element);
    }

    private static <T> T popElementFromStack(Stack<T> stack) {
        return stack.pop();
    }
}
