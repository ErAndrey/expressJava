package new_task.task_7;

public enum Game {
    ODDS_OR_EVENS("Odds or Evens", 100),
    FLIP_COIN("Flip coins", 100),
    HIGHER_OR_LOWER("Higher or Lower", 100),
    SLOTS_2("x2 Slots", 100),
    SLOTS_3("x3 Slots", 200),
    SLOTS_4("x4 Slots", 200),
    SLOTS_5("x5 Slots", 400),
    GUESS_THE_WORDS("Guess the Words", 500),
    BLACKJACK("Blackjack", 1000),
    ROCK_PAPER_SCISSORS_1("x1 RPS", 200),
    ROCK_PAPER_SCISSORS_3("x3 RPS", 200),
    ROCK_PAPER_SCISSORS_5("x5 RPS", 200),
    ROCK_PAPER_SCISSORS_7("x7 RPS", 200);

    private final String name;
    private final int minBalanceForPlay;

    Game(String name, int minBalanceForPlay) {
        this.name = name;
        this.minBalanceForPlay = minBalanceForPlay;
    }

    public String getName() { return this.name; }
    public int getMinBalanceForPlay() { return this.minBalanceForPlay; }
}
