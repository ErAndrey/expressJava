package new_task.task_7;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Player andrew = new Player("Андрей");
        Casino casino = new Casino(andrew, 50_000);

        andrew.deposit(25_000);
        System.out.println("Баланс казино " + casino.getBalance() + "₽");

        casino.play();

        /**
         * toDo
         * класс User , Player -> Зареганный в казино по user.name + password
         *
         * в login():
         * "Добро пожаловать..."
         * 0. Выход -> return
         * 1. Логин : User -> Player -> menu()
         * 2. Регистрация :
         *      есть Player по User -> Ошибка, вернуть в login()
         *      нет Player по User -> Регнуть -> menu()
         *
         * в menu():
         * "Привет, player.name..."
         * 0. Выйти -> login()
         * 1. Баланс -> balance()
         * 2. Играть -> play()
         *
         * в balance():
         * "Ваш баланс: player.balance"
         * 0. Назад -> menu()
         * 1. Пополнение : User -> Player (по player.code)
         * 2. Вывод : Player -> User (по player.code)
         *
         * в play():
         * 0. Назад -> menu()
         * 1-N. Игры
         */
    }
}
