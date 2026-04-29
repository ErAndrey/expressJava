    package new_task.task_7;

    import java.util.Objects;

    public class User {
        private final String username;
        private final String password;
        private Player player; // может быть null (если зарегистрирован, но не создал игровой профиль)

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.player = null;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public boolean hasPlayer() {
            return player != null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(username, user.username);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username);
        }
    }