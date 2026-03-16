package practice_7.classes;

public class Pair<X, Y> {
    private X x;
    private Y y;

    public void setX(X value) {
        this.x = value;
    }

    public void setY(Y value) {
        this.y = value;
    }

    public X getX() {
        return this.x;
    }

    public Y getY() {
        return this.y;
    }
}
