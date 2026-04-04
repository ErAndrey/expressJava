package practice_7.classes;

public class Box<C> {
    private C c;

    public void setC(C value) {
        this.c = value;
    }

    public C getC() {
        return this.c;
    }
}
