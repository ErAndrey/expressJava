package additional.topic_8.task_1;

public class Box <T> {
    private T t;

    public void put(T t) {
        this.t = t;
    }

    public T getT() {
        return this.t;
    }
}
