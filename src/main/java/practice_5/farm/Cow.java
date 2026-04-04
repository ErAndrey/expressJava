package practice_5.farm;

public class Cow extends Animal {
    @Override
    public void getResource() {
        System.out.println("Я дам вам молоко");
    }

    @Override
    public void eat() {
        System.out.println("Я нуждаюсь в выпасе");
    }
}
