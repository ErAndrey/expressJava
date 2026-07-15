package world_wars.trading;

import world_wars.Player;
import world_wars.builds.tradings.Trade;
import world_wars.diplomacy.RelationType;
import world_wars.general.ToString;

import java.util.List;
import java.util.Map;

public class TradingRequest {
    private static int counter = 1;

    private final int id;
    private final Player fromPlayer;
    private final List<RelationType> availableFor;

    private final CurrencyType selectedCurrency;
    private int countSelected;
    private final TradingType tradingType;
    private final CurrencyType expectedCurrency;
    private int countExpected;

    public TradingRequest(Player fromPlayer, List<RelationType> availableFor, CurrencyType selectedCurrency, int countSelected, TradingType tradingType, CurrencyType expectedCurrency, int countExpected) {
        this.id = counter++;
        this.fromPlayer = fromPlayer;
        this.availableFor = availableFor;
        this.selectedCurrency = selectedCurrency;
        this.countSelected = countSelected;
        this.tradingType = tradingType;
        this.expectedCurrency = expectedCurrency;
        this.countExpected = countExpected;
    }

    public int getId() { return this.id; }
    public Player getFromPlayer() { return this.fromPlayer; }
    public List<RelationType> getAvailableFor() { return this.availableFor; }
    public CurrencyType getSelectedCurrency() { return this.selectedCurrency; }
    public int getCountSelected() { return this.countSelected; }
    public void withdrawCountSelected(int count) { this.countSelected -= count; }
    public TradingType getTradingType() { return this.tradingType; }
    public CurrencyType getExpectedCurrency() { return this.expectedCurrency; }
    public int getCountExpected() { return this.countExpected; }

    @Override
    public String toString() {
        return ToString.forTradingRequest(this);
    }
}
