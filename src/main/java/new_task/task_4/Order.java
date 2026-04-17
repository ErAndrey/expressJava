package new_task.task_4;

import java.util.Objects;

public final class Order {
    private final int id;
    private OrderStatus status;

    Order(int id) {
        this.id = id;
        this.status = OrderStatus.CREATED;
    }

    public int getId() {
        return this.id;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    String mapStatusToString() {
        return switch (this.status) {
            case CREATED -> "Создан";
            case PAID -> "Оплачен";
            case SKIPPED -> "Отменен";
            case DELIVERED -> "Доставка";
        };
    }

    String changeOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
        return "Заказ №" + this.id + " - " + this.mapStatusToString();
    }

    @Override
    public String toString() {
        return "{Заказ №" + this.id + ", " + this.mapStatusToString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return order.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
