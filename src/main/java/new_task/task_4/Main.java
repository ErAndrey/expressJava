package new_task.task_4;

public class Main {
    public static void main(String[] args) {
        OrderSystem system = new OrderSystem();

        System.out.println("Можно: Создать, Отменить, Оплатить, Доставить");

        System.out.println("\nСоздание\n");

        System.out.println("Log getOrders: " + system.getOrders());

        Order o1 = system.createOrder(1);
        Order o2 = system.createOrder(2);
        Order o3 = system.createOrder(3);
        Order o4 = system.createOrder(4);
        Order o5 = system.createOrder(5);
        Order o6 = system.createOrder(6);

        System.out.println("\nОперации после создания\n");

        System.out.println("Log getOrders: " + system.getOrders());

        try {
            Order o = system.createOrder(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Log Exception: " + e.getMessage());
        }

        try {
            system.deliverOrder(o1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Log Exception: " + e.getMessage());
        }

        system.payOrder(o2);
        system.payOrder(o3);
        system.payOrder(o4);
        system.payOrder(o5);

        system.rejectOrder(o6);

        System.out.println("\nОперации после оплаты\n");

        System.out.println("Log getOrders: " + system.getOrders());

        try {
            system.payOrder(o2);
        } catch (UnsupportedOperationException e) {
            System.out.println("Log Exception: " + e.getMessage());
        }

        system.deliverOrder(o3);
        system.deliverOrder(o4);

        system.rejectOrder(o5);

        System.out.println("\nОперации после доставки\n");

        System.out.println("Log getOrders: " + system.getOrders());

        try {
            system.payOrder(o3);
        } catch (UnsupportedOperationException e) {
            System.out.println("Log Exception: " + e.getMessage());
        }

        try {
            system.rejectOrder(o4);
        } catch (UnsupportedOperationException e) {
            System.out.println("Log Exception: " + e.getMessage());
        }

        try {
            system.deliverOrder(o3);
        } catch (UnsupportedOperationException e) {
            System.out.println("Log Exception: " + e.getMessage());
        }

        System.out.println();

        System.out.println("Log getOrders: " + system.getOrders());
    }
}
