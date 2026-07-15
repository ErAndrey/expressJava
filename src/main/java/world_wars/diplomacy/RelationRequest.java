package world_wars.diplomacy;

import world_wars.general.Icon;

public enum RelationRequest {
    DECLARE_NEUTRAL(Icon.TO_NEUTRAL),
    REQUEST_NEUTRAL(Icon.TO_NEUTRAL),

    REQUEST_FRIEND(Icon.TO_FRIEND),

    REQUEST_CREATE_UNION(Icon.TO_UNION),
    REQUEST_JOIN_IN_UNION(Icon.TO_UNION),
    REQUEST_INVITE_IN_UNION(Icon.TO_UNION),

    LEAVE_FROM_UNION(Icon.TO_NEUTRAL),

    DECLARE_WAR(Icon.TO_WAR),
    PROPOSE_UNION_ATTACK_A_PLAYER(Icon.TO_WAR),

    NOTIFY(Icon.NOTIFY);

    private Icon icon;
    RelationRequest(Icon icon) { this.icon = icon; }

    private String getNameRequest() {
        return switch (this) {
            case DECLARE_NEUTRAL -> "Перестать дружить ";
            case REQUEST_NEUTRAL -> "Предложить перемирие ";
            case REQUEST_FRIEND -> "Предложить дружбу ";
            case REQUEST_CREATE_UNION -> "Предложить создать союз ";
            case REQUEST_JOIN_IN_UNION -> "Попроситься в союз ";
            case REQUEST_INVITE_IN_UNION -> "Пригласить в союз ";
            case LEAVE_FROM_UNION -> "Выйти из союза ";
            case DECLARE_WAR -> "Объявить войну ";
            case PROPOSE_UNION_ATTACK_A_PLAYER -> "Предложить союзникам напасть на игрока ";
            case NOTIFY -> "";
        };
    }

    @Override
    public String toString() { return getNameRequest() + icon; }
}
