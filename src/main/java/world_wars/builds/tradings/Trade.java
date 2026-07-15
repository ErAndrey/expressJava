package world_wars.builds.tradings;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Produce;
import world_wars.entity.Build;
import world_wars.entity.BuildType;
import world_wars.general.Utils;

public class Trade extends Build {

    private static int maxExport(Trade trade, int countFarmingBuild) {
        return (int) (countFarmingBuild * 2.5) + switch (trade.lvl) {
            case 1 -> 30;
            case 2 -> 60;
            case 3 -> 100;
            default -> throw new IllegalStateException("Unexpected lvl trade value");
        };
    }
    private static int maxImport(Trade trade, int countFarmingBuild) {
        return (int) (countFarmingBuild * 1.25) + switch (trade.lvl) {
            case 1 -> 40;
            case 2 -> 70;
            case 3 -> 110;
            default -> throw new IllegalStateException("Unexpected lvl trade value");
        };
    }

    private int countForImport;
    private int countForExport;

    public Trade() {
        super(1);
        this.type = BuildType.TRADE;
        this.defence = 0;
        this.consume = Consume.getConsume(this);
        this.produce = Produce.getProduce(this);
        this.countForImport = maxImport(this, 1);
        this.countForExport = maxExport(this, 1);
    }

    public int getCountForImport() { return this.countForImport; }
    public int getCountForExport() { return this.countForExport; }
    public void deliveryImport(int count) { this.countForImport -= count; }
    public void deliveryExport(int count) { this.countForExport -= count; }
    public void resetDelivery(int countFarmingBuild) {
        this.countForImport = maxImport(this, countFarmingBuild);
        this.countForExport = maxExport(this, countFarmingBuild);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        System.out.println(Utils.toGreen("System: ") + "Торговый дом улучшен! Ожидайте обновления экспорта и импорта к следующему ходу!");
    }
}
