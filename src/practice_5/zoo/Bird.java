package practice_5.zoo;

public class Bird extends Animal {
    @Override
    public void move() {
        System.out.println("Я лечу");
    }

    @Override
    public void voice() {
        System.out.println("Я чирикаю");
    }
}
