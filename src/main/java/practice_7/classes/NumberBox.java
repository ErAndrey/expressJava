package practice_7.classes;

import java.util.ArrayList;
import java.util.List;

public class NumberBox <T extends Number> {
    private final List<T> numbers;
    private double sum;

    public NumberBox() {
        this.numbers = new ArrayList<>();
    }

    public void addNumberToBox(T element) {
        if (element == null) {
            throw new  IllegalArgumentException("Не добавляй null!");
        }
        this.numbers.add(element);
        this.sum += element.doubleValue();
    }

    public double getSumOfBox() {
        return this.sum;
    }

}
