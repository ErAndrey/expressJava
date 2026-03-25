package test;

import test.task_1.User;

import java.util.*;

public class Main {

    static HashSet<User> users = new HashSet<>();

    public static void main(String[] args) {

        User u1 = new User(1, "Andrew");
        User u2 = new User(2, "Danila");
        User u3 = new User(3, "Lera");
        User u4 = new User(4, "Mila");
        User u5 = new User(5, "Danil");

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);

        System.out.println(users);

        System.out.println(checkUserForId(6));
        System.out.println(checkUserForId(5));

        System.out.println(getUserNameForId(6));
        System.out.println(getUserNameForId(3));

        List<User> users1 = new ArrayList<>(Arrays.asList(u1, u2, u3, u4, u5));
        List<User> filteredUsers = users1.stream().
                filter(user -> user.getName().startsWith("A")).
                toList();
        System.out.println(filteredUsers);
    }

    public static boolean checkUserForId(int id) {
        return users.contains(new User(id));
    }

    public static String getUserNameForId(int id) {
        String name = "Не найдено";
        for (User user : users) {
            if (user.getId() == id) {
                name = user.getName();
            }
        }
        return name;
    }


}