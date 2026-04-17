package new_task.task_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public final class OrderSystem {
    private final CopyOnWriteArrayList<Order> orders;

    public OrderSystem() {
        this.orders = new CopyOnWriteArrayList<>();
    }

    public Order createOrder(int id) {
        Order order = new Order(id);
        if (!this.orders.contains(order)) {
            this.orders.add(order);
            System.out.println("Заказ №" + id + " создан");
            return order;
        }
        throw new IllegalArgumentException("Заказ №" + id + " уже существует");
    }

    public Map<OrderStatus, List<Order>> getOrders() {
        return this.orders
                .stream()
                .collect(Collectors.groupingBy(
                        Order::getStatus, Collectors.toList()
                ));
    }

    private List<String> whatCanDoWithOrder(Order order) {
        List<String> canDoWithOrder = new ArrayList<>();
        switch (order.getStatus()) {
            case CREATED -> {
                canDoWithOrder.add("Оплатить");
                canDoWithOrder.add("Отменить");
            }
            case PAID -> {
                canDoWithOrder.add("Отправить на доставку");
                canDoWithOrder.add("Отменить");
            }
            case SKIPPED -> canDoWithOrder.add("Отправить на доставку");
            case DELIVERED -> canDoWithOrder.add("Ожидайте доставку :)");
        }
        return canDoWithOrder;
    }

    private void throwUnsupportedOperationException(Order order, String preMessage) {
        throw new UnsupportedOperationException("Заказ №" + order.getId() + " в статусе \"" + order.mapStatusToString() + "\", " + preMessage + ", вы можете: " + this.whatCanDoWithOrder(order));
    }

    private boolean containsOrder(Order order) {
        if (this.orders.contains(order)) return true;

        throw new NoSuchElementException("Заказа №" + order.getId() + " нет в списке заказов");
    }

    public void payOrder(Order order) {
        if (containsOrder(order)) {
            if (!(order.getStatus() == OrderStatus.CREATED)) this.throwUnsupportedOperationException(order, "нельзя оплатить");
            System.out.println(order.changeOrderStatus(OrderStatus.PAID));
        }
    }

    public void rejectOrder(Order order) {
        if (containsOrder(order)) {
            if (!(order.getStatus() == OrderStatus.CREATED || order.getStatus() == OrderStatus.PAID)) this.throwUnsupportedOperationException(order, "нельзя отменить");
            System.out.println(order.changeOrderStatus(OrderStatus.SKIPPED));
        }

    }

    public void deliverOrder(Order order) {
        if (containsOrder(order)) {
            if (!(order.getStatus() == OrderStatus.PAID || order.getStatus() == OrderStatus.SKIPPED)) this.throwUnsupportedOperationException(order, "нельзя отправить");
            System.out.println(order.changeOrderStatus(OrderStatus.DELIVERED));
        }
    }
}
