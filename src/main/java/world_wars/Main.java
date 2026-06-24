package world_wars;

import world_wars.ccpu.Consume;
import world_wars.ccpu.Create;
import world_wars.ccpu.Upgrade;

public class Main {

    public static void main(String[] args) {
        State state = new State();

        System.out.println("Upgrade: ");
        System.out.println(Upgrade.getUpgrade(state.getCapital()));
        Upgrade.getToUpgrade().forEach((type, upgrade) -> System.out.println(type + " : " + upgrade));
        System.out.println();

        System.out.println("Create: ");

        System.out.println(state.getCapital() + " lvl " + state.getCapital().getLvl());
        System.out.println("builds: " + Create.getAvailableBuildToCreate(state));
        System.out.println("units: " + Create.getAvailableUnitToCreate(state));
        state.getCapital().upgrade();

        System.out.println(state.getCapital() + " lvl " + state.getCapital().getLvl());
        System.out.println("builds: " + Create.getAvailableBuildToCreate(state));
        System.out.println("units: " + Create.getAvailableUnitToCreate(state));
        state.getCapital().upgrade();

        System.out.println(state.getCapital() + " lvl " + state.getCapital().getLvl());
        System.out.println("builds: " + Create.getAvailableBuildToCreate(state));
        System.out.println("units: " + Create.getAvailableUnitToCreate(state));
        state.getCapital().upgrade();

        System.out.println();

        System.out.println("Consume: ");
        System.out.println("Builds: ");
        Consume.getBuildConsuming().forEach((type, map) -> System.out.println(type + " : " + map));
        System.out.println("Units: ");
        Consume.getUnitConsuming().forEach((type, currency) -> System.out.println(type + " : " + currency));

        System.out.println();



    }

    /**
     *
     *
     * State - Currency currentBalance, Currency changeBalance, int lvl <- Capital,
     * Здания:
     * Rewardable // Те что приносят доход
     *
     *
     * Ресурсы:
     * Mineable // Те что добываются 
     * Treadable // Те что торгуются
     */

    /**
     * Иконки:
     *
     * Buttons : 0️⃣1️⃣2️⃣3️⃣4️⃣5️⃣6️⃣7️⃣8️⃣9️⃣
     * ❓❔❌ 💞
     * Tech (lvl) / 🎓 : ❗❕❕❕ . ❗❗❕❕ . ❗❗❗❕ . ❗❗❗❗ / ❕❗❗❗ / ❕❕❗❗ / ❕❕❕❗ / ❕❕❕❕
     * Destroy Build / 🔄 / 🧨
     * Upgrade Build / ⏫ / ✨ / 🔧
     * Time / ⏰ / 🕒 / 🧭
     * Fire / 🔥
     * Diplomacy / 🤝 🤍💚🖤
     * End move / ⛔
     * Create / ➕
     * Shop / 🛒
     *
     */

    /**
     * Ресурсы:
     *
     * Gold  / 💰 -> Юниты, постройки, улучшения
     * Food  / 🍒 -> Юниты, улучшения
     *
     * Stone / 🧱 -> Юниты(деф), постройки, улучшения
     * Tree  / 🌳 -> Юниты(деф), постройки, улучшения
     * Ore   / 💎 -> Юниты, постройки, улучшения
     * Oil   / 🛢️ -> Юниты, улучшения
     */

    /**
     * Месторождения:
     *
     * Forest   / 🌲 -> 🌳
     * Mountain / 🌋 -> 🧱
     * Mineral  / 🧊 -> 💎
     * OilField / 🩸 -> 🛢️
     */

    /**
     * Здания:
     *                Capital  |  Def  |    Price    |  -/Move   |  +/Move
     * Capital / 🏰      -        1           -           -          💰💎
     * Farm    / 🌱      1        0         🌳🧱          💰          🍒
     * Factory / 🔨      1        0        💰🧱💎         🍒         💰🧱
     * Sawmill / 🪓      1        0        💰🌳💎         🍒         💰🌳
     * Mine    / ⛏️      1        0        🌳🧱💎         🍒         💰💎
     * OilRig  / 🗼      2        0       💰🌳🧱💎       💰💎         🛢️️
     *
     * Shop    / 🛒      1        0        🌳🧱💎          -         🌳🧱
     * Trade   / 💒      2        0         💰💎           -           -
     * Barracks/ 🎪      1        1        🌳🧱💎        💰🍒         -
     * Technique 🏭      2        0       💰🌳🧱💎     🌳🧱💎🛢️       💰
     *
     * Tower 1 / 🔭      1        1           🌳           💰          -
     * Tower 2 / 🎏      2        2         💰🌳💎         💰          -
     * Tower 3 / 🎆      3        3         💰🧱💎        💰💎         -
     * AirDef  / 📡      4        4       💰🌳🧱💎🛢️      💰💎🛢️        -
     */

    /**
     * Улучшения и изменения:
     *             |      Lvl 1     |     -> Lvl 2      |     -> Lvl 3      |      -> Lvl 4
     * Capital / 🏰        -              🍒🌳🧱💎          💰🍒🌳🧱💎         💰🍒🌳🧱💎🛢️
     *                    👨‍💼️x1             👨‍💼️x2👨‍✈️‍x1          👨‍💼️x3👨‍✈️‍x2💂x1       👨‍💼️x4👨‍✈️‍x2💂x2🛺x1
     * Farm    / 🌱    lv1🏰+🌳🧱        lv2🏰+🌳🧱         lv3🏰+🌳🧱           lv4🏰+🌳🧱
     * Factory / 🔨   lv1🏰+💰🧱💎      lv2🏰+💰🧱💎       lv3🏰+💰🧱💎         lv4🏰+💰🧱💎
     * Sawmill / 🪓   lv1🏰+💰🌳💎      lv2🏰+💰🌳💎       lv3🏰+💰🌳💎         lv4🏰+💰🌳💎
     * Mine    / ⛏️   lv1🏰+🌳🧱💎      lv2🏰+🌳🧱💎      lv3🏰+🌳🧱💎         lv4🏰+🌳🧱💎
     * OilRig  / 🗼        -            lv2🏰+💰🌳🧱💎     lv3🏰+💰🌳🧱💎       lv4🏰+💰🌳🧱💎
     *
     * Shop    / 🛒   lv1🏰+🌳🧱💎     lv2🏰+🍒🌳🧱💎    lv3🏰+💰🍒🌳🧱💎      lv4🏰+💰🍒💎
     *                  🍒🌳🧱💎             +🛢️                 +💰                   -%
     * Army    / 🎪   lv1🏰+🌳🧱💎      lv2🏰+🍒🌳🧱      lv3🏰+💰🍒🌳🧱      lv4🏰+💰🍒🌳🧱💎
     *                  👨‍💼️х2👨‍✈️‍х1          👨‍💼️х3👨‍✈️‍х2💂х1       👨‍💼️х4👨‍✈️‍х3💂х2          👨‍💼️х6👨‍✈️‍х4💂х3
     * Tech    / 🏭        -            lv2🏰+💰🌳🧱💎    lv3🏰+💰🌳🧱💎🛢️     lv4🏰+💰🌳🧱💎🛢️             -
     *                     -                 🛺x1           🛺x2✈️x1🪂x1          🛺x3✈️x2🪂x3                -
     * Trade   / 💒        -              lv2🏰+💰💎       lv3🏰+💰🌳🧱💎     lv4🏰+💰🌳🧱💎🛢️             -
     *                     -              💰🍒🌳🧱до7     💰🍒🌳🧱💎🛢️до18     💰🍒🌳🧱💎🛢️до37             -
     *
     * Tower 1 / 🔭     lv1🏰+🌳         lv2🏰+💰🌳💎        lv3🏰+💰🧱💎       lv4🏰+💰🌳🧱💎
     *                     🔭                 ->🎏                ->🎆                ->📡
     * Tower 2 / 🎏        -             lv2🏰+💰🌳💎        lv3🏰+💰🧱💎       lv4🏰+💰🌳🧱💎
     *                     -                   🎏                 ->🎆                ->📡                   -
     * Tower 3 / 🎆        -                   -              lv3🏰+💰🧱💎       lv4🏰+💰🌳🧱💎                 -
     *                     -                   -                   🎆                 ->📡
     * AirDef  / 📡        -                   -                   -             lv4🏰+💰🌳🧱💎🛢️
     *                     -                   -                   -                    📡
     */

    /**
     * Юниты:
     *                Capital  |  Pow&Def  |  Move radius  | Attack radius  |   Price   |   -/Move
     * Scout    / 👨‍💼️     1          1/0            3               1              💰         💰🍒
     * Soldier  / 👨‍✈️‍     1          2/1            3              2              💰         💰🍒💎
     * Sniper   / 💂     2          2/0            1               3             💰🌳       💰🍒🌳💎
     * Tank     / 🛺     2          3/2            2               2            💰🧱💎     💰🍒💎🛢️
     * Plane    / ✈️     3          4/3            3               3            💰🧱💎     💰🍒💎🛢️
     * Drone    / 🪂     3          3/0            4               4            💰🌳💎        💰🛢️
     * FlareGun / 🚀     4          5/2             1               5           💰🌳🧱💎    💰🌳🧱🛢️
     */
}
