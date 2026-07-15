package world_wars;

import world_wars.diplomacy.DiplomacyManager;
import world_wars.diplomacy.RelationType;
import world_wars.general.Color;
import world_wars.general.Currency;
import world_wars.general.Utils;
import world_wars.trading.TradingManager;

import java.util.*;

public class Game {
    private List<Color> colors;
    private Map<Integer, Player> players;

    public Game() {
        this.colors = new ArrayList<>(List.of(Color.values()));
        this.players = new HashMap<>();
    }

    private Color getNextColor() {
        Collections.shuffle(colors);
        return colors.remove(0);
    }


    public void start() {
        DiplomacyManager diplomacyManager = new DiplomacyManager();
        TradingManager tradingManager = new TradingManager();
        int playerCount = 3;//Utils.selectNumber(2, 8, "Привет! Укажи количество игроков");
        for (int i = 0; i < playerCount; i++) {
            Player player = new Player(diplomacyManager, tradingManager, getNextColor());
            this.players.put(player.getId(), player);
        }
        diplomacyManager.initPlayerRelations(this.players);
        System.out.println(Utils.toYellow("System: ") + "В игре " + players.size() + " игроков. Очередность хода: " + players.values() + ", удачи!");
        this.play();
    }


    public void startV2() {
        DiplomacyManager diplomacyManager = new DiplomacyManager();
        TradingManager tradingManager = new TradingManager();
        //int playerCount = 3;//Utils.selectNumber(2, 8, "Привет! Укажи количество игроков");
        Player p1 = new Player(diplomacyManager, tradingManager, getNextColor());
        Player p2 = new Player(diplomacyManager, tradingManager, getNextColor());
        Player p3 = new Player(diplomacyManager, tradingManager, getNextColor());
        Player p4 = new Player(diplomacyManager, tradingManager, getNextColor());

        this.players.put(p1.getId(), p1);
        this.players.put(p2.getId(), p2);
        this.players.put(p3.getId(), p3);
        this.players.put(p4.getId(), p4);

        diplomacyManager.initPlayerRelations(this.players);

        DiplomacyManager.setRelationBetweenPlayer(p1, p2, RelationType.UNION);
        DiplomacyManager.setRelationBetweenPlayer(p3, p4, RelationType.UNION);


        p1.depositCurrencyForTest(Currency.of(25,0,0,0,0,0));


        System.out.println(Utils.toYellow("System: ") +
                "В игре " + players.size() + " игроков. Очередность хода: " + players.values() + ", удачи!");
        this.play();
    }

    private void play() {
        int move = 1;
        while (players.size() > 1) {
            System.out.println("\nХод 👣#" + move++);
            players.values().removeIf(player -> player.move() == 0);
        }
        System.out.println(Utils.toGreen("System: ") + players.values().stream().findFirst().get() + " победил!");
    }

}
