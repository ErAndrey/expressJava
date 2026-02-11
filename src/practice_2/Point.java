package practice_2;

public class Point {
    //Реализуйте конструктор, геттеры, сеттер только для x, и метод print(), выводящий координаты
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}

    public void setX(int newX) {
        this.x = newX;
    }
    public void print() {
        System.out.println("[" + x + "," + y + "]");
    }
}
