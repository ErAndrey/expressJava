package practice_5.zoo;

public class Elephant extends Animal {
    @Override
    public void move() {
        System.out.println("Я хожу");
    }

    @Override
    public void voice() {
        System.out.println("Я трублю");
    }
}
