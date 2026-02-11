package practice_2;

public class Circle {
    int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    public void setRadius(int newRadius) {
        this.radius = newRadius;
    }

    public int getRadius() {
        return this.radius;
    }

    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    public double calculateCircumference() {
        return Math.PI * 2 * radius;
    }
}
