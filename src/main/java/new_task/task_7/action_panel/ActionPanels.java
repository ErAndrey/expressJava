package new_task.task_7.action_panel;

import new_task.task_7.Utils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ActionPanels {

    private static final TreeMap<Integer, List<String>> START = new TreeMap<>();
    private static final TreeMap<Integer, List<String>> MENU = new TreeMap<>();
    private static final TreeMap<Integer, List<String>> CONFIRMED_PROFILE = new TreeMap<>();
    private static final TreeMap<Integer, List<String>> UNCONFIRMED_PROFILE = new TreeMap<>();
    private static final TreeMap<Integer, List<String>> BALANCE = new TreeMap<>();
    private static final TreeMap<Integer, List<String>> HAVE_BONUSES = new TreeMap<>();
    private static final TreeMap<Integer, List<String>> HAVE_NOT_BONUSES = new TreeMap<>();
    private static final TreeMap<Integer, List<String>> PLAY = new TreeMap<>();

    static {
        START.put(1, List.of("~", "<", "+", ">", "~"));
        START.put(2, List.of("Авторизация", "Выход", "Регистрация", "Войти", "Авторизация"));
        START.put(3, List.of("~", "0", "1", "2", "~"));

        MENU.put(1, List.of("~", "<", "#", "₽", "+", ">", "~"));
        MENU.put(2, List.of("Главная", "Выход", "Профиль", "Депозит", "Бонус", "Азарт", "Главная"));
        MENU.put(3, List.of("~", "0", "1", "2", "3", "4", "~"));

        CONFIRMED_PROFILE.put(1, List.of("~", "<", "~"));
        CONFIRMED_PROFILE.put(2, List.of("Профиль", "Назад", "Профиль"));
        CONFIRMED_PROFILE.put(3, List.of("~", "0", "~"));

        UNCONFIRMED_PROFILE.put(1, List.of("~", "<", "^", "~"));
        UNCONFIRMED_PROFILE.put(2, List.of("Профиль", "Назад", "Подтвердить", "Профиль"));
        UNCONFIRMED_PROFILE.put(3, List.of("~", "0", "1", "~"));

        HAVE_BONUSES.put(1, List.of("~", "<", "?", "*", "+", "~"));
        HAVE_BONUSES.put(2, List.of("Бонус", "Назад", "Получение", "Виды бонуса", "Применить", "Бонус"));
        HAVE_BONUSES.put(3, List.of("~", "0", "1", "2", "3", "~"));

        HAVE_NOT_BONUSES.put(1, List.of("~", "<", "?", "*", "~"));
        HAVE_NOT_BONUSES.put(2, List.of("Бонус", "Назад", "Получение", "Виды бонуса", "Бонус"));
        HAVE_NOT_BONUSES.put(3, List.of("~", "0", "1", "2", "~"));

        BALANCE.put(1, List.of("~", "<", "+", "-", "~"));
        BALANCE.put(2, List.of("Депозит", "Назад", "Пополнить", "Вывести", "Депозит"));
        BALANCE.put(3, List.of("~", "0", "1", "2", "~"));

        PLAY.put(1, List.of("~", "<", "777", "1/2", "%", ">/<", "...", "21!", "~"));
        PLAY.put(2, List.of("Азарт", "Назад", "Slots", "Evens", "Flips", "Hg/Lw", "Words", "BJack", "Азарт"));
        PLAY.put(3, List.of("~", "0", "1", "2", "3", "4", "5", "6", "~"));
    }

    private static String getActionPanel(TreeMap<Integer, List<String>> table) {
        StringBuilder actionPanel = new StringBuilder("\n");
        List<String> actions = table.get(2);
        int width, countActions = actions.size();
        String currentString;

        for (Map.Entry<Integer, List<String>> entry : table.entrySet()) {
            for (int i = 0; i < countActions; i++) {
                currentString = entry.getValue().get(i);
                width = actions.get(i).length();
                if (entry.getKey() == 2 && (i == 0 || i == countActions - 1)) {
                    currentString = Utils.toInfo(currentString);
                }
                if (i == countActions - 1) {
                    actionPanel.append(Utils.toCenter(currentString, width));
                } else {
                    actionPanel.append(Utils.toCenter(currentString, width)).append("  |  ");
                }
            }
            actionPanel.append("\n");
        }

        return actionPanel.toString();
    }

    public static final Map<ActionPanel, String> ACTION_PANELS = Map.ofEntries(
            Map.entry(ActionPanel.START, getActionPanel(START)),
            Map.entry(ActionPanel.MENU, getActionPanel(MENU)),
            Map.entry(ActionPanel.CONFIRMED_PROFILE, getActionPanel(CONFIRMED_PROFILE)),
            Map.entry(ActionPanel.UNCONFIRMED_PROFILE, getActionPanel(UNCONFIRMED_PROFILE)),
            Map.entry(ActionPanel.HAVE_BONUSES, getActionPanel(HAVE_BONUSES)),
            Map.entry(ActionPanel.HAVE_NOT_BONUSES, getActionPanel(HAVE_NOT_BONUSES)),
            Map.entry(ActionPanel.BALANCE, getActionPanel(BALANCE)),
            Map.entry(ActionPanel.PLAY, getActionPanel(PLAY))
    );
}
