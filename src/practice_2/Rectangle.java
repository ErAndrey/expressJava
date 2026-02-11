package practice_2;

public class Rectangle {
    int width;
    int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    public int calculateArea() {
        return this.width * this.height;
    }
}
