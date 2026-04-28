package additional.topic_8.task_4;

public class Pair <K, V> {
    private K k;
    private V v;

    public Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public void setK(K k) {
        this.k = k;
    }

    public void setV(V v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "{K: " + this.k + ", V: " + this.v + "}";
    }
}
