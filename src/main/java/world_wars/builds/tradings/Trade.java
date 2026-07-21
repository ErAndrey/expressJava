package world_wars.builds.tradings;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.general.Utils;

public class Trade extends Build {

    private static int maxExport(Trade trade) {
        return switch (trade.lvl) {
            case 1 -> 15;
            case 2 -> 30;
            case 3 -> 50;
            default -> throw new IllegalStateException("Unexpected lvl trade value");
        };
    }
    private static int maxImport(Trade trade) {
        return switch (trade.lvl) {
            case 1 -> 20;
            case 2 -> 35;
            case 3 -> 50;
            default -> throw new IllegalStateException("Unexpected lvl trade value");
        };
    }

    public Trade() {
        super(1);
        this.type = BuildType.TRADE;
        this.defence = 0;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
    }

    public int getExportPower() { return maxExport(this); }
    public int getImportPower() { return maxImport(this); }

    @Override
    public void upgrade() {
        super.upgrade();
        System.out.println(Utils.toGreen("System: ") + "Торговый дом улучшен! Ожидайте обновления экспорта и импорта к следующему ходу!");
    }
}
