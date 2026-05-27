package new_task.task_7;

import new_task.task_7.bet.Bet;
import new_task.task_7.bet.BetType;

import java.util.Map;
import java.util.stream.Collectors;

public class Multipliers {
    public static final double REDUCE_MULTIPLIER_FOR_SLOTS_GAME = 50.0; // В %
    private static final Map<String, Double> HONEST_SLOTS_MULTIPLIERS = Map.ofEntries(
            // 5 слотов
            Map.entry("5_5s", 1780.64), // Честный кф - 10000 // 10*1*1*1*1 = 10 -> 0.01% ++
            Map.entry("5_4r+2s+2s", 347.2), // Честный кф - 1111 // 10*1*9*1*1 *1(способ) = 90 -> 0.09% ++
            Map.entry("5_4s", 274.38), // Честный кф - 556 // 10*1*1*1*9 *2(способа) = 180 -> 0.18% ++
            Map.entry("5_3s+2s", 201.0), // Честный кф - 556 // 10*1*1*9*1 *2(способа) = 180 -> 0.18% ++
            Map.entry("5_3r+2r", 70.2), // Честный кф - 111 // 10*9*1*1*1 *1(способ) = 900 -> 0.9% ++
            Map.entry("5_4r+3s", 199.21), // Честный кф - 556 // 10*9*1*1*1 *2(способа) = 180 -> 0.18% ++
            Map.entry("5_2r+3s", 188.3), // Честный кф - 556 // 10*9*1*1*1 *1(способ) = 90 -> 0.09% ++
            Map.entry("5_3r+2s+2s", 194.0), // Честный кф - 556 // 10*9*1*1*1 *2(способа) = 180 -> 0.18% ++
            Map.entry("5_3s", 35.8), // Честный кф - 46.3 // 10*1*1*9*8 *3(способа) = 2160 -> 2.16% ++
            Map.entry("5_2s+2s", 32.9), // Честный кф - 46.3 // 10*1*9*1*8 *3(способа) = 2160 -> 2.16% ++
            Map.entry("5_3r+2s", 20.34), // Честный кф - 27.8 // 10*9*8*1*1 *6(способов) = 4320 -> 4.32% ++
            Map.entry("5_2r+2r", 7.83), // Честный кф - 9.25 // = 10800 -> 10.8% ++
            Map.entry("5_2r+2s", 9.72), // Честный кф - 23.1 // 10*9*8*1*1 *6(способов) = 4320 -> 4.32% ++
            Map.entry("5_3r", 12.14), // Честный кф - 19.8 // 10*1*1*9*9 *10(способов) = 8100 -> 8.1% ++
            Map.entry("5_2s", 3.22), // Честный кф - 4.9 // 10*1*9*8*7 *4(способа) = 20160 -> 20.16% ++
            Map.entry("5_2r", 2.54), // Честный кф - 1.98 // 10*9*8*7*1 *10(способов) = 50400 -> 50.4% ++

            // 4 слота - 10 000 комбинаций
            Map.entry("4_4s", 295.8), // Честный кф - 1000 // 10*1*1*1 = 10 -> 0.1%
            Map.entry("4_2r+2r", 18.52), // Честный кф - 18.52 // 10*9*1*1 *6(способов) = 540 -> 5.4% ++
            Map.entry("4_2r+2s", 37.04), // Честный кф - 111.11 // 10*9*1*1 *1(способ) = 90 -> 0.9% ++
            Map.entry("4_3r+2s", 45.61), // Честный кф - 55.56 // 10*9*1*1 *2(способа) = 180 -> 1.8% ++
            Map.entry("4_2s+2s", 37.04), // Честный кф - 55.56 // 10*1*9*1 *2(способа) = 180 -> 1.8% ++
            Map.entry("4_3s", 27.78), // Честный кф - 55.56 // 10*1*1*9 *2(способа) = 180 -> 1.8% ++
            Map.entry("4_2s", 3.14), // Честный кф - 4.63 // 10*1*9*8 *3(способа) = 2160 -> 21.6% ++
            Map.entry("4_2r", 2.76), // Честный кф - 2.31 // 10*9*8*1 *6(способов) = 4320 -> 43.2% ++

            // 3 слота - 1 000 комбинаций
            Map.entry("3_3s", 121.2), // Честный кф - 100 // 10*1*1 = 10 -> 1% ++
            Map.entry("3_2s", 7.56), // Честный кф - 5.56 // 10*1*9 *2(способа) = 180 -> 18% ++
            Map.entry("3_2r", 6.35), // Честный кф - 3.7 // 10*1*9 *3(способа) = 270 -> 27% ++

            // 2 слота - 100 комбинаций
            Map.entry("2_2s", 21.0) // Честный кф - 10 // 10*1 = 10 -> 10%
    );

    public static final Map<String, Double> SLOTS_MULTIPLIERS; // Для всех слотов
    public static final double ODDS_OR_EVEN_MULTIPLIER = 2.01;
    public static final double FLIP_COIN_MULTIPLIER = 2.01;

    static {
        SLOTS_MULTIPLIERS = prepareSlotsMultipliers();
    }

    private static Map<String, Double> prepareSlotsMultipliers() {
        return HONEST_SLOTS_MULTIPLIERS.entrySet().stream().
                collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> getClearMultiplier(entry.getValue() * ((100.0 - REDUCE_MULTIPLIER_FOR_SLOTS_GAME) / 100.0))
                ));
    }

    public static double getMultiplierForSlots(int slotCount, Map<Integer, Integer> sequence, Map<Integer, Integer> clearRepeat) {
        /**
         * clearRepeat.isEmpty == true
         *      sequence.isEmpty == true (ни повторений, ни тем более последовательности) -> Проигрыш -> x0
         *      sequence.isEmpty == false (только последовательности)
         *          5 :
         *              5 [5=1] - [5, 5, 5, 5, 5] = Джекпот +1 -> 10 / 100 000 = 0.01% -> x10 000
         *              4 [4=1] - [4, 4, 4, 4, 0] = Каре последовательности +2 -> 180 / 100 000 = 0.18% -> x556
         *              3+2 / 2+3 [2=1, 3=1] - [2, 2, 3, 3, 3] = Фул-хаус последовательности +3 -> 180 / 100 000 = 0.18% -> x556
         *              3 [3=1] - [0, 3, 3, 3, 0] = Сет последовательности +4 -> 2 160 / 100 000 = 2.16% -> x46.3
         *              2+2 [2=2] - [2, 2, 0, 3, 3] = Две пары последовательности +5 -> 2 160 / 100 000 = 2.16% -> x46.3
         *              2 [2=1] - [1, 2, 2, 3, 4] = Одна пара последовательности +6 -> 20 160 / 100 000 = 20.16% -> x4.96
         *          4 :
         *              4 [4=1] - [4, 4, 4, 4] = Джекпот +1 -> 10 / 10 000 = 0.1% -> x1 000
         *              3 [3=1] - [3, 3, 3, 0] = Сет последовательности +2 -> 180 / 10 000 = 1.8% -> x55.6
         *              2+2 [2=2] - [1, 1, 2, 2] = Две пары последовательности +5 -> 180 / 10 000 = 1.8% -> x55.6
         *              2 [2=1] - [1, 2, 2, 3] = Одна пара последовательности +6 -> 2 160 / 10 000 = 21.6% -> x4.63
         *          3 :
         *              3 [3=1] - [3, 3, 3] = Джекпот +1 -> 10 / 1 000 = 1% -> x100
         *              2 [2=1] - [2, 2, 0] = Одна пара последовательности +6 -> 180 / 1 000 = 18% -> x5.56
         *          2 :
         *              2 [2=1] - [2, 2] = Джекпот +1 -> 10 / 100 = 10% -> x10
         * clearRepeat.isEmpty == false
         *      sequence.isEmpty == true (только повторения)
         *          5 :
         *              3 [3=1] - [1, 2, 1, 3, 1] = Сет повторения +7 -> 5 040 / 100 000 = 5.04% -> x19.8
         *              3+2 / 2+3 [2=1, 3=1] - [1, 2, 1, 2, 1] = Фул-хаус повторения +8 -> 180 / 100 000 = 0.18% -> x556
         *              2+2 [2=2] - [1, 2, 0, 1, 2] = Две пары повторения +9 -> 4 320 / 100 000 = 4.32% -> x23.1
         *              2 [2=1] - [0, 1, 2, 3, 0] = Одна пара повторения +10 -> 30 240 / 100 000 = 30.24% -> x3.31
         *          4 :
         *              2+2 [1, 2, 1, 2] = Две пары повторения +9 -> 90 / 10 000 = 0.9% -> x111
         *              2 [0, 1, 2, 1] = Одна пара повторения +10 -> 2 160 / 10 000 = 21.6% -> x4.63
         *              // 10 * 9 * 9 * 1
         *              //
         *          3 :
         *              2 [1, 2, 1] = Одна пара повторения +10 -> 90 / 1 000 = 9% -> x11.1
         *      sequence.isEmpty == false (повторения + последовательности)
         *          5 :
         *              4пов1+3пос1 - [5,5,5,0,5] / [5,0,5,5,5] +11 -> 180 / 100 000 = 0.18% -> x556
         *              4пов1+2пос2 - [5,5,0,5,5] +12 -> 90 / 100 000 = 0.09% -> x1 111
         *              3пов1+2пос1 - [5,5,0,1,5] / [5,0,1,5,5] / [0,5,1,5,5]... +13 -> 3 600 / 100 000 = 3.6% -> x27.8
         *              3пов1+2пос2 - [5,5,0,0,5]... +14 -> 180 / 100 000 = 0.18% -> x556
         *              2пов1+3пос1 - [0,0,5,5,5] +15 -> 180 / 100 000 = 0.18% -> x556
         *              2пов1+2пос1 - [0,5,5,0,1] +16 -> 4 320 / 100 000 = 4.32% -> x23.1
         *          4 :
         *              3пов1+2пос1 - [4,4,0,4] +13 -> 180 / 10 000 = 1.8% -> x55.6
         *              2пов1+2пос1 - [4,0,0,4] +16 -> 90 / 10 000 = 0.9% -> x111
         */

        if (clearRepeat.isEmpty()) {
            // 0 Поражение
            if (sequence.isEmpty()) {
                System.out.println("\nУвы, ни одно число не совпало. Повезет в следующий раз!");
                return 0;
            }

            // 1 Джекпот
            if (sequence.containsKey(slotCount)) {
                System.out.print("\nДжекпот! Все числа совпали!");
                return switch (slotCount) {
                    case 5 -> SLOTS_MULTIPLIERS.get("5_5s");
                    case 4 -> SLOTS_MULTIPLIERS.get("4_4s");
                    case 3 -> SLOTS_MULTIPLIERS.get("3_3s");
                    case 2 -> SLOTS_MULTIPLIERS.get("2_2s");
                    default -> {
                        System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Джекпот\"");
                        yield -1;
                    }
                };
            }

            // 2 Почти джекпот последовательности
            if (sequence.containsKey(slotCount - 1)) {
                System.out.print("\nПоздравляем! Еще чуть-чуть и был бы джекпот!");
                return switch (slotCount) {
                    case 5 -> SLOTS_MULTIPLIERS.get("5_4s");
                    case 4 -> SLOTS_MULTIPLIERS.get("4_3s");
                    case 3 -> SLOTS_MULTIPLIERS.get("3_2s");
                    default -> {
                        System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Почти джекпот последовательности\"");
                        yield -1;
                    }
                };
            }

            if (sequence.containsKey(3) && sequence.get(3) == 1) {
                // 3 Фул-хаус последовательности
                if (sequence.containsKey(2) && sequence.get(2) == 1) {
                    System.out.print("\nВау, последовательный фул-хаус!");
                    return SLOTS_MULTIPLIERS.get("5_3s+2s");
                }

                // 4 Сет последовательности
                System.out.print("\nПоследовательный сет, три в ряд!");
                return SLOTS_MULTIPLIERS.get("5_3s");
            }

            if (sequence.containsKey(2)) {
                // 5 Две пары последовательности
                if (sequence.get(2) == 2) {
                    System.out.print("\nОгонь! Две последовательные пары!");
                    return switch (slotCount) {
                        case 5 -> SLOTS_MULTIPLIERS.get("5_2s+2s");
                        case 4 -> SLOTS_MULTIPLIERS.get("4_2s+2s");
                        default -> {
                            System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Две пары последовательности\"");
                            yield -1;
                        }
                    };
                }

                // 6 Одна пара последовательности
                if (sequence.get(2) == 1) {
                    System.out.print("\nОдна последовательная пара!");
                    return switch (slotCount) {
                        case 5 -> SLOTS_MULTIPLIERS.get("5_2s");
                        case 4 -> SLOTS_MULTIPLIERS.get("4_2s");
                        case 3 -> SLOTS_MULTIPLIERS.get("3_2s");
                        default -> {
                            System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Одна пара последовательности\"");
                            yield -1;
                        }
                    };
                }
            }
        } else {
            if (sequence.isEmpty()) {
                if (clearRepeat.containsKey(3) && clearRepeat.get(3) == 1) {
                    // 8 Фул-хаус повторения
                    if (clearRepeat.containsKey(2) && clearRepeat.get(2) == 1) {
                        System.out.print("\nЕее, это классический фул-хаус!");
                        return SLOTS_MULTIPLIERS.get("5_3r+2r");
                    }

                    // 7 Сет повторения
                    System.out.print("\nТри числа совпали! Это сет!");
                    return SLOTS_MULTIPLIERS.get("5_3r");
                }

                if (clearRepeat.containsKey(2)) {
                    // 9 Две пары повторения
                    if (clearRepeat.get(2) == 2) {
                        System.out.print("\nДве пары!");
                        return switch (slotCount) {
                            case 5 -> SLOTS_MULTIPLIERS.get("5_2r+2r");
                            case 4 -> SLOTS_MULTIPLIERS.get("4_2r+2r");
                            default -> {
                                System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Две пары повторения\"");
                                yield -1;
                            }
                        };
                    }

                    // 10 Одна пара повторения
                    if (clearRepeat.get(2) == 1) {
                        System.out.print("\nДва числа совпали! Одна пара!");
                        return switch (slotCount) {
                            case 5 -> SLOTS_MULTIPLIERS.get("5_2r");
                            case 4 -> SLOTS_MULTIPLIERS.get("4_2r");
                            case 3 -> SLOTS_MULTIPLIERS.get("3_2r");
                            default -> {
                                System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"Одна пара повторения\"");
                                yield -1;
                            }
                        };
                    }
                }
            } else {
                if (clearRepeat.containsKey(4) && clearRepeat.get(4) == 1) {
                    // 11 4пов1+3пос1
                    if (sequence.containsKey(3) && sequence.get(3) == 1) {
                        System.out.print("\nТри в ряд! Да еще и 4 числа совпали!");
                        return SLOTS_MULTIPLIERS.get("5_4r+3s");
                    }

                    // 12 4пов1+2пос2
                    if (sequence.containsKey(2) && sequence.get(2) == 2) {
                        System.out.print("\nДве пары в ряд! Да еще и 4 числа совпали!");
                        return SLOTS_MULTIPLIERS.get("5_4r+2s+2s");
                    }
                }

                if (clearRepeat.containsKey(3) && clearRepeat.get(3) == 1) {
                    if (sequence.containsKey(2)) {
                        // 13 3пов1+2пос1
                        if (sequence.get(2) == 1) {
                            System.out.print("\nПара в ряд! Да еще и 3 числа совпали!");
                            return switch (slotCount) {
                                case 5 -> SLOTS_MULTIPLIERS.get("5_3r+2s");
                                case 4 -> SLOTS_MULTIPLIERS.get("4_3r+2s");
                                default -> {
                                    System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"3пов1+2пос1\"");
                                    yield -1;
                                }
                            };
                        }

                        // 14 3пов1+2пос2
                        if (sequence.get(2) == 2) {
                            System.out.print("\nДве пары в ряд! Да еще и 3 числа совпали!");
                            return SLOTS_MULTIPLIERS.get("5_3r+2s+2s");
                        }
                    }
                }

                if (clearRepeat.containsKey(2) && clearRepeat.get(2) == 1) {
                    // 15 2пов1+3пос1
                    if (sequence.containsKey(3) && sequence.get(3) == 1) {
                        System.out.print("\nТри числа в ряд! Да еще и пара повторения!");
                        return SLOTS_MULTIPLIERS.get("5_2r+3s");
                    }

                    // 16 2пов1+2пос1
                    if (sequence.containsKey(2) && sequence.get(2) == 1) {
                        System.out.print("\nДве пары! Одна в ряд, другая совпадение.");
                        return switch (slotCount) {
                            case 5 -> SLOTS_MULTIPLIERS.get("5_2r+2s");
                            case 4 -> SLOTS_MULTIPLIERS.get("4_2r+2s");
                            default -> {
                                System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "в \"2пов1+2пос1\"");
                                yield -1;
                            }
                        };
                    }
                }
            }
        }
        System.out.println(Utils.toError("System: Ошибка расчета коэффициента ") + "ни один из сценариев");
        return -1; // Не пойманный кейс
    }

    public static double getMultiplierForHigherOrLower(int currentNumber, boolean betOnHigher) {
        return switch (currentNumber) {
            case 2 -> betOnHigher ? 1.19 : 9.0;
            case 3 -> betOnHigher ? 1.33 : 4.57;
            case 4 -> betOnHigher ? 1.54 : 3.14;
            case 5 -> betOnHigher ? 1.85 : 2.32;
            case 6 -> betOnHigher ? 2.32 : 1.85;
            case 7 -> betOnHigher ? 3.14 : 1.54;
            case 8 -> betOnHigher ? 4.57 : 1.33;
            case 9 -> betOnHigher ? 9.0 : 1.19;
            default -> 1.0;
        };
    }

    public static double getMultiplierForBlackjack(boolean isBlackjack, boolean isDoubleDown) {
        return (isBlackjack ? (isDoubleDown ? 2.75 : 2.5) : (isDoubleDown ? 2.25 : 2.0));
    }

    public static double getClearMultiplier(double multiplier) {
        return Math.ceil(multiplier * 100.0) / 100.0;
    }

    public static double getActualMultiplier(Bet bet, double multiplier) {
        return bet.getType() == BetType.FREE_BET ? getClearMultiplier(multiplier - 1.0) : multiplier;
    }
}
