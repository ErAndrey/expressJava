package practice_3;

public class GameSettings {
    private static int maxPlayers;

    final String gameName;
    int currentPlayers;

    public GameSettings(String gameName, int currentPlayers) {
        this.gameName = gameName;
        if (maxPlayers >= currentPlayers) {
            this.currentPlayers = currentPlayers;
        } else {
            this.currentPlayers = maxPlayers;
        }
    }

    public static void setMaxPlayers(int newMaxPlayers) {
        maxPlayers = newMaxPlayers;
    }

    public void addPlayer() {
        if (this.currentPlayers < maxPlayers) {
            currentPlayers++;
            System.out.println("Игрок добавлен");
            return;
        }
        System.out.println("Игрок не добавлен");
    }

    public void printGameStatus() {
        System.out.println("Название: " + this.gameName + ", Количество игроков: " + this.currentPlayers + "/" + maxPlayers);
    }
}
