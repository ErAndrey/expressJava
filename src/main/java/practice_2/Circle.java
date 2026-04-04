package practice_2;

public class Circle {
    double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public void setRadius(double newRadius) {
        this.radius = newRadius;
    }

    public double getRadius() {
        return this.radius;
    }

    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    public double calculateCircumference() {
        return Math.PI * 2 * radius;
    }
}
