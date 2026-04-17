package practice_12.task_2;

public final class UserValidator {
    private final static String regexForName;
    private final static String regexForEmail;

    private boolean validationEnabled;

    static {
        regexForName = "^[A-ZА-ЯЁ][a-zA-Zа-яёЁ]*$";
        regexForEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    }

    public UserValidator() {
        this.validationEnabled = false;
    }

    public boolean getIsValidationEnabled() {
        return this.validationEnabled;
    }

    public void updateValidationEnabled(boolean validationEnabled) {
        this.validationEnabled = validationEnabled;
    }

    public boolean checkName(User user) {
        if (this.validationEnabled) {
            String userName = user.getName();
            if (userName == null) {
                throw new IllegalArgumentException("Не можем проверить null");
            }
            if (!userName.isEmpty() && userName.matches(regexForName)) {
                return true;
            }
            throw new InvalidUserException("Имя \"" + userName + "\" - некорректно");
        }
        return false;
    }

    public boolean checkAge(User user) {
        if (this.validationEnabled) {
            int userAge = user.getAge();
            if (userAge >= 18 && userAge <= 100) {
                return true;
            }
            throw new InvalidUserException("Возраст \"" + userAge + "\" - некорректен");
        }
        return false;
    }

    public boolean checkEmail(User user) {
        if (this.validationEnabled) {
            String userEmail = user.getEmail();
            if (userEmail == null) {
                throw new IllegalArgumentException("Не можем проверить null");
            }
            if (userEmail.matches(regexForEmail)) {
                return true;
            }
            throw new InvalidUserException("Почта \"" + userEmail + "\" - некорректна");
        }
        return false;
    }
}
