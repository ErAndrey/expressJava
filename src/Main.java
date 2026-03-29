import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("one", "two", "three");
        list.stream().peek(System.out::println).map(String::toUpperCase).forEach(System.out::println);
    }
}
