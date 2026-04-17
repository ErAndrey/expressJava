package practice_12.task_2;

public class Main {
    public static void main(String[] args) {
        User andrew = new User("A", 19, "eroshkin.andrey@bk.ru");

        UserValidator userValidator = new UserValidator();

        userValidator.updateValidationEnabled(true);

        System.out.println(userValidator.checkName(andrew));
        System.out.println(userValidator.checkAge(andrew));
        System.out.println(userValidator.checkEmail(andrew));
    }
}
