package new_task.task_3;

public record Product (String name, String category, double price, boolean inStock){
    @Override
    public String toString() {
        return this.name + " : " + this.price;
    }
}
