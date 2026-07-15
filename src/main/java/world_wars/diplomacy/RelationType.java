package world_wars.diplomacy;

import world_wars.general.Icon;

public enum RelationType {
    NEUTRAL(Icon.NEUTRAL),
    FRIEND(Icon.FRIEND),
    UNION(Icon.UNION),
    WAR(Icon.WAR),
    DIE(Icon.DIE);

    private Icon icon;
    public Icon getIcon() { return this.icon; }
    RelationType(Icon icon) { this.icon = icon; }

    public static RelationType getTypeForRequest(RelationRequest request) {
        return switch (request) {
            case DECLARE_NEUTRAL, REQUEST_NEUTRAL, LEAVE_FROM_UNION -> NEUTRAL;
            case REQUEST_FRIEND -> FRIEND;
            case REQUEST_CREATE_UNION, REQUEST_JOIN_IN_UNION, REQUEST_INVITE_IN_UNION -> UNION;
            case DECLARE_WAR, PROPOSE_UNION_ATTACK_A_PLAYER -> WAR;
            default -> throw new IllegalStateException("Сюда попал информационный реквест, который не говорит об смене отношения");
        };
    }

    private String getNameForType() {
        return switch (this) {
            case NEUTRAL -> " (Нейтрал)";
            case FRIEND -> " (Друг)";
            case UNION -> " (Союзник)";
            case WAR -> " (Враг)";
            case DIE -> " (Погиб)";
        };
    }

    @Override
    public String toString() { return icon + getNameForType(); }
}
