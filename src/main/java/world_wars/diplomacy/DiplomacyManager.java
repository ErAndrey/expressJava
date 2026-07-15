package world_wars.diplomacy;

import world_wars.Game;
import world_wars.Player;
import world_wars.general.Icon;
import world_wars.general.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class DiplomacyManager {
    private Map<Integer, Player> players;

    public void initPlayerRelations(Map<Integer, Player> players) {
        this.players = players;
        for (Player from : players.values()) {
            for (Player to : players.values()) setRelationBetweenPlayer(from, to, RelationType.NEUTRAL);
        }
    }

    public Player selectPlayer(Player who) {
        while (true) {
            int choice = Utils.nextInt("🆔 игрока: ");
            if (choice == who.getId()) {
                System.out.println(Utils.toRed("System: ") + "Вы не можете выбрать сами себя");
                continue;
            }
            if (players.containsKey(choice)) return players.get(choice);
            System.out.println(Utils.toRed("System: ") + "Такого игрока нет");
        }
    }
    public void diePlayer(Player who) {
        for (Player player : players.values()) {
            setRelationBetweenPlayer(who, player, RelationType.DIE);
        }
    }

    public List<RelationRequest> whatICanDoWithPlayer(Player who, Player with) {
        List<RelationRequest> actions = new ArrayList<>();
        RelationType relation = who.getRelations().get(with);

        switch (relation) {
            case NEUTRAL -> {
                actions.add(RelationRequest.REQUEST_FRIEND);

                boolean youHaveUnion = who.getRelations().values().contains(RelationType.UNION);

                if (youHaveUnion) {
                    List<Player> playersInUnionWithYou = who.getRelations().entrySet().stream()
                            .filter(entry -> entry.getValue() == RelationType.UNION)
                            .map(Map.Entry::getKey)
                            .toList();

                    boolean yourTeammateWarWithPlayerWith = false;

                    for (Player player : playersInUnionWithYou) {
                        if (with.getRelations().get(player) == RelationType.WAR) {
                            yourTeammateWarWithPlayerWith = true;
                            break;
                        }
                    }

                    if (!yourTeammateWarWithPlayerWith) {
                        actions.add(RelationRequest.PROPOSE_UNION_ATTACK_A_PLAYER);
                    }

                } else {
                    actions.add(RelationRequest.DECLARE_WAR);
                }
            }
            case FRIEND -> {
                actions.add(RelationRequest.DECLARE_NEUTRAL);

                boolean youHaveUnion = who.getRelations().values().contains(RelationType.UNION);
                boolean withHaveUnion = with.getRelations().values().contains(RelationType.UNION);

                if (youHaveUnion && !withHaveUnion) {
                    List<Player> playersInUnionWithYou = who.getRelations().entrySet().stream()
                            .filter(entry -> entry.getValue() == RelationType.UNION)
                            .map(Map.Entry::getKey)
                            .toList();

                    boolean yourTeammateWarWithPlayerWith = false;

                    for (Player player : playersInUnionWithYou) {
                        if (with.getRelations().get(player) == RelationType.WAR) {
                            yourTeammateWarWithPlayerWith = true;
                            break;
                        }
                    }

                    if (!yourTeammateWarWithPlayerWith) actions.add(RelationRequest.REQUEST_INVITE_IN_UNION);
                }
                if (!youHaveUnion && withHaveUnion) {
                    List<Player> playersWhoInUnionWithPlayerWith = with.getRelations().entrySet().stream()
                            .filter(entry -> entry.getValue() == RelationType.UNION)
                            .map(Map.Entry::getKey)
                            .toList();

                    boolean youWarWithPlayersFromUnionPlayerWith = false;

                    for (Player player : playersWhoInUnionWithPlayerWith) {
                        if (who.getRelations().get(player) == RelationType.WAR) {
                            youWarWithPlayersFromUnionPlayerWith = true;
                            break;
                        }
                    }

                    if (!youWarWithPlayersFromUnionPlayerWith) { // Если ты не воюешь с союзником игрока, то можешь попроситься к ним в союз
                        actions.add(RelationRequest.REQUEST_JOIN_IN_UNION);
                    }
                }
                if (!youHaveUnion && !withHaveUnion) actions.add(RelationRequest.REQUEST_CREATE_UNION);
                //последний кейс - это когда ты (who) и with - в разных союзах, их нельзя объединить, поэтому в рамках дружбы только нейтралитет
            }
            case UNION -> actions.add(RelationRequest.LEAVE_FROM_UNION);
            case WAR -> actions.add(RelationRequest.REQUEST_NEUTRAL);
        }
        return actions;
    }

    public void sendRequestToPlayer(Player from, RelationRequest request, Player to) {
        StringBuilder message = new StringBuilder(Utils.toGreen("\nSystem: "));

        Notification notification = new Notification(from, request);

        switch (request) {
            case DECLARE_WAR -> {
                if (to.getRelations().values().contains(RelationType.UNION)) { // если у to есть союзники

                    List<Player> playersInUnionWithPlayerTo = to.getRelations().entrySet().stream()
                            .filter(entry -> entry.getValue() == RelationType.UNION)
                            .map(Map.Entry::getKey)
                            .toList();

                    for (Player player : playersInUnionWithPlayerTo) {
                        setRelationBetweenPlayer(from, player, RelationType.WAR);
                        player.addNotification(notification.id(), notification);
                    }
                    message.append("Вы объявили войну игроку ").append(to).append(" и его союзникам : ").append(playersInUnionWithPlayerTo);
                } else {
                    message.append("Вы объявили войну игроку ").append(to);;
                }
                setRelationBetweenPlayer(from, to, RelationType.WAR);
                to.addNotification(notification.id(), notification);
            }
            case DECLARE_NEUTRAL -> {
                message.append("Вы объявили нейтралитет игроку ").append(to);
                setRelationBetweenPlayer(from, to, RelationType.NEUTRAL);
                to.addNotification(notification.id(), notification);
            }
            case REQUEST_NEUTRAL -> {
                message.append("Вы предложили перемирие игроку ").append(to);
                to.addNotification(notification.id(), notification);
            }
            case REQUEST_FRIEND -> {
                message.append("Вы предложили дружбу игроку ").append(to);
                to.addNotification(notification.id(), notification);
            }
            case REQUEST_CREATE_UNION -> {
                message.append("Вы предложили игроку ").append(to).append(" создать союз");
                to.addNotification(notification.id(), notification);
            }
            case REQUEST_JOIN_IN_UNION -> {
                notification.initJoinRequestApprovals();

                notification.addApprovalToJoinRequest(to);
                to.addNotification(notification.id(), notification);

                List<Player> playersInUnionWithPlayerTo = to.getRelations().entrySet().stream()
                        .filter(entry -> entry.getValue() == RelationType.UNION)
                        .map(Map.Entry::getKey)
                        .toList();

                for (Player player : playersInUnionWithPlayerTo) {
                    notification.addApprovalToJoinRequest(player);
                    player.addNotification(notification.id(), notification);
                }

                message.append("Вы отправили запрос на вступление в союз игроку ").append(to).append(", включая его союзников: ").append(playersInUnionWithPlayerTo);
            }
            case REQUEST_INVITE_IN_UNION -> {
                notification.initInviteRequestApprovals(to);
                to.addNotification(notification.id(), notification);

                List<Player> playersWhoInUnionWithYou = from.getRelations().entrySet().stream()
                        .filter(entry -> entry.getValue() == RelationType.UNION)
                        .map(Map.Entry::getKey)
                        .toList();

                for (Player player : playersWhoInUnionWithYou) {
                    notification.addApprovalToInviteRequest(player);
                    player.addNotification(notification.id(), notification);
                }

                notification.getInviteRequestApprovals().put(from, true); // Сразу голосуем за принятие в союз за from
                notification.voteFromApproval();

                message.append("Вы предложили союзникам: ").append(playersWhoInUnionWithYou).append(" добавить игрока ").append(to).append(" в ваш союз");
            }
            case LEAVE_FROM_UNION -> {
                List<Player> playersInUnionWithPlayerTo = to.getRelations().entrySet().stream()
                        .filter(entry -> entry.getKey() != from)
                        .filter(entry -> entry.getValue() == RelationType.UNION)
                        .map(Map.Entry::getKey)
                        .toList();

                for (Player player : playersInUnionWithPlayerTo) {
                    setRelationBetweenPlayer(from, player, RelationType.NEUTRAL);
                    player.addNotification(notification.id(), notification);
                }

                setRelationBetweenPlayer(from, to, RelationType.NEUTRAL);
                to.addNotification(notification.id(), notification);

                if (playersInUnionWithPlayerTo.isEmpty()) {
                    message.append("Вы разорвали союз с игроком ").append(to);
                } else {
                    message.append("Вы разорвали союз с игроком ").append(to).append(" включая других ваших союзников: ").append(playersInUnionWithPlayerTo);
                }
            }
            case PROPOSE_UNION_ATTACK_A_PLAYER -> {
                notification.initAttackProposeApprovals(to);

                List<Player> playersWhoInUnionWithYou = from.getRelations().entrySet().stream()
                        .filter(entry -> entry.getValue() == RelationType.UNION)
                        .map(Map.Entry::getKey)
                        .toList();

                for (Player player : playersWhoInUnionWithYou) {
                    notification.addApprovalToAttackPropose(player);
                    player.addNotification(notification.id(), notification);
                }

                notification.getAttackProposeApprovals().put(from, true); // Сразу одобряем атаку за from
                notification.voteFromApproval();

                message.append("Вы предложили союзникам ").append(playersWhoInUnionWithYou).append(" напасть на игрока ").append(to);
            }
        }

        System.out.println(message);
    }

    public void acceptRelationRequestFromNotification(Player whoAccepted, Notification notification) {
        StringBuilder message = new StringBuilder(Utils.toGreen("\nSystem: "));

        whoAccepted.getNotifications().remove(notification.id());
        RelationType type = RelationType.getTypeForRequest(notification.request());
        Notification responseNotification;

        switch (notification.request()) {
            case REQUEST_NEUTRAL -> {
                setRelationBetweenPlayer(whoAccepted, notification.fromPlayer(), type);

                message.append("Вы приняли запрос игрока ")
                        .append(notification.fromPlayer())
                        .append(" на перемирие с ним (🧡)");

                responseNotification = new Notification(new StringBuilder()
                        .append("(->🧡) Игрок ")
                        .append(whoAccepted)
                        .append(" согласился на перемирие")
                );

                notification.fromPlayer().addNotification(responseNotification.id(), responseNotification);
            }
            case REQUEST_FRIEND -> {
                setRelationBetweenPlayer(whoAccepted, notification.fromPlayer(), type);

                message.append("Вы приняли запрос игрока ")
                        .append(notification.fromPlayer())
                        .append(" на дружбу с ним (💚)");

                responseNotification = new Notification(new StringBuilder()
                        .append("(->💚) Игрок ")
                        .append(whoAccepted)
                        .append(" согласился с вами дружить")
                );

                notification.fromPlayer().addNotification(responseNotification.id(), responseNotification);
            }
            case REQUEST_CREATE_UNION -> {
                setRelationBetweenPlayer(whoAccepted, notification.fromPlayer(), type);

                message.append("Вы приняли запрос игрока ")
                        .append(notification.fromPlayer())
                        .append(" на создание союза с ним (💙)");

                responseNotification = new Notification(new StringBuilder()
                        .append("(->💙) Игрок ")
                        .append(whoAccepted)
                        .append(" согласился создвать с вами союз")
                );

                notification.fromPlayer().addNotification(responseNotification.id(), responseNotification);
            }
            case REQUEST_JOIN_IN_UNION -> {
                notification.getJoinRequestApprovals().put(whoAccepted, true);
                notification.voteFromApproval();

                message.append("Вы проголосовали за вступление игрока ")
                        .append(notification.fromPlayer())
                        .append(" в ваш союз (💙)")
                        .append(" Проголосовало: ")
                        .append(notification.getAlreadyVotedFor(notification));
            }
            case REQUEST_INVITE_IN_UNION -> {
                notification.getInviteRequestApprovals().put(whoAccepted, true);
                notification.voteFromApproval();

                if (whoAccepted.equals(notification.getInvitedPlayerInUnion())) {
                    message.append("Вы согласились присоединиться к союзу (💙)")
                            .append(" Проголосовало: ")
                            .append(notification.getAlreadyVotedFor(notification));
                } else {
                    message.append("Вы проголосовали за вступление игрока ")
                            .append(notification.getInvitedPlayerInUnion())
                            .append(" в ваш союз (💙)")
                            .append(" Проголосовало: ")
                            .append(notification.getAlreadyVotedFor(notification));
                }
            }
            case PROPOSE_UNION_ATTACK_A_PLAYER -> {
                notification.getAttackProposeApprovals().put(whoAccepted, true);
                notification.voteFromApproval();

                message.append("Вы проголосовали за нападение на игрока ")
                        .append(notification.getTargetToAttackPlayer())
                        .append(" (->💔)")
                        .append(" Проголосовало: ")
                        .append(notification.getAlreadyVotedFor(notification));
            }
            default -> throw new IllegalStateException("Игрок принял Request, который не подразумевает возможность его принятия");
        }

        System.out.println(message);
    }
    public void rejectRelationRequestFromNotification(boolean rejectFromPlayer, Player whoRejected, Notification notification) {
        StringBuilder message = new StringBuilder(Utils.toGreen("\nSystem: "));

        whoRejected.getNotifications().remove(notification.id());
        Notification responseNotification;

        switch (notification.request()) {
            case REQUEST_NEUTRAL -> {
                message.append("Вы отклонили запрос игрока ")
                        .append(notification.fromPlayer())
                        .append(" на перемирие с ним (🧡)");

                responseNotification = new Notification(new StringBuilder()
                        .append("(->🧡) Игрок ")
                        .append(whoRejected)
                        .append(" не согласился на перемирие")
                );

                notification.fromPlayer().addNotification(responseNotification.id(), responseNotification);
            }
            case REQUEST_FRIEND -> {
                message.append("Вы отклонили запрос игрока ")
                        .append(notification.fromPlayer())
                        .append(" на дружбу с ним (💚)");

                responseNotification = new Notification(new StringBuilder()
                        .append("(->💚) Игрок ")
                        .append(whoRejected)
                        .append(" не согласился с вами дружить")
                );

                notification.fromPlayer().addNotification(responseNotification.id(), responseNotification);
            }
            case REQUEST_CREATE_UNION -> {
                message.append("Вы отклонили запрос игрока ")
                        .append(notification.fromPlayer())
                        .append(" на создание союза с ним (💙)");

                responseNotification = new Notification(new StringBuilder()
                        .append("(->💙) Игрок ")
                        .append(whoRejected)
                        .append(" не согласился создвать с вами союз")
                );

                notification.fromPlayer().addNotification(responseNotification.id(), responseNotification);
            }
            case REQUEST_JOIN_IN_UNION -> {
                notification.voteFromApproval();

                message.append("Вы проголосовали против вступления игрока ")
                        .append(notification.fromPlayer())
                        .append(" в ваш союз (💙)")
                        .append(" Проголосовало: ")
                        .append(notification.getAlreadyVotedFor(notification));
            }
            case REQUEST_INVITE_IN_UNION -> {
                notification.voteFromApproval();

                if (whoRejected.equals(notification.getInvitedPlayerInUnion())) {
                    message.append("Вы отказались присоединиться к союзу (💙)")
                            .append(" Проголосовало: ")
                            .append(notification.getAlreadyVotedFor(notification));
                } else {
                    message.append("Вы проголосовали против вступление игрока ")
                            .append(notification.getInvitedPlayerInUnion())
                            .append(" в ваш союз (💙)")
                            .append(" Проголосовало: ")
                            .append(notification.getAlreadyVotedFor(notification));
                }
            }
            case PROPOSE_UNION_ATTACK_A_PLAYER -> {
                notification.voteFromApproval();

                message.append("Вы проголосовали против нападения на игрока ")
                        .append(notification.getTargetToAttackPlayer())
                        .append(" (->💔)")
                        .append(" Проголосовало: ")
                        .append(notification.getAlreadyVotedFor(notification));
            }
            default -> throw new IllegalStateException("Игрок принял Request, который не подразумевает возможность его принятия");
        }
        if (rejectFromPlayer) System.out.println(message);
    }

    public static void resultOfJoinRequest(Notification notification) {
        Player whoSendRequest = notification.fromPlayer();
        Set<Player> approvalsPlayers = notification.getJoinRequestApprovals().keySet();

        Notification forWhoSend;
        Notification forApprovals;

        boolean isAllApproved = notification.getJoinRequestApprovals().values().stream()
                .allMatch(accept -> accept);

        if (isAllApproved) {
            RelationType type = RelationType.getTypeForRequest(notification.request());
            for (Player whoApproved : approvalsPlayers) setRelationBetweenPlayer(whoApproved, whoSendRequest, type);
            forWhoSend = new Notification(new StringBuilder()
                    .append("(->💙) 👍 Ваш запрос на вступление в союз был одобрен, ваши союзники: ")
                    .append(approvalsPlayers)
            );
            forApprovals = new Notification(new StringBuilder()
                    .append("(->💙) 👍 Ваш союз: ")
                    .append(approvalsPlayers)
                    .append(" одобрил принять игрока ")
                    .append(whoSendRequest)
                    .append(" в союз!")
            );
        } else {
            forWhoSend = new Notification(new StringBuilder()
                    .append("(->💙) 👎 Ваш запрос на вступление в союз: ")
                    .append(approvalsPlayers)
                    .append(" не был одобрен. Голоса: ")
                    .append(notification.getStatusVoteJoinRequest())
            );
            forApprovals = new Notification(new StringBuilder()
                    .append("(->💙) 👎 Ваш союз: ")
                    .append(approvalsPlayers)
                    .append(" не согласился принять игрока ")
                    .append(whoSendRequest)
                    .append(" в союз. Голоса: ")
                    .append(notification.getStatusVoteJoinRequest())
            );
        }

        whoSendRequest.addNotification(forWhoSend.id(), forWhoSend);
        approvalsPlayers.forEach(player -> player.addNotification(forApprovals.id(), forApprovals));
    }
    public static void resultOfInviteRequest(Notification notification) {
        Player invitedPlayer = notification.getInvitedPlayerInUnion();
        Set<Player> approvalsPlayers = new HashSet<>(notification.getInviteRequestApprovals().keySet());
        approvalsPlayers.remove(invitedPlayer);

        Notification forInvited;
        Notification forApprovals;

        boolean isAllApproved = notification.getInviteRequestApprovals().values().stream()
                .allMatch(accept -> accept);

        boolean isInvitedApprove = notification.getInviteRequestApprovals().get(invitedPlayer);

        if (isAllApproved) {
            RelationType type = RelationType.getTypeForRequest(notification.request());
            for (Player whoApproved : approvalsPlayers) setRelationBetweenPlayer(whoApproved, invitedPlayer, type);
            forInvited = new Notification(new StringBuilder()
                    .append("(->💙) 👍 Союз: ")
                    .append(approvalsPlayers)
                    .append(", включая вас, одобрили присоединение к союзу!")
            );
            forApprovals = new Notification(new StringBuilder()
                    .append("(->💙) 👍 Ваш союз: ")
                    .append(approvalsPlayers)
                    .append(" одобрил принять игрока ")
                    .append(invitedPlayer)
                    .append(" в союз!")
            );
        } else {
            if (isInvitedApprove) {
                forInvited = new Notification(new StringBuilder()
                        .append("(->💙) 👎 Союз: ")
                        .append(approvalsPlayers)
                        .append(" не одобрил ваше присоединение к союзу. Голоса: ")
                        .append(notification.getStatusVoteInviteRequest())
                );
                forApprovals = new Notification(new StringBuilder()
                        .append("(->💙) 👎 Ваш союз: ")
                        .append(approvalsPlayers)
                        .append(" не согласился принять игрока ")
                        .append(invitedPlayer)
                        .append(" в союз. Голоса: ")
                        .append(notification.getStatusVoteInviteRequest())
                );
            } else {
                forInvited = new Notification(new StringBuilder()
                        .append("(->💙) 👎 Вы отказались от приглашения в союз: ")
                        .append(approvalsPlayers)
                        .append(" Все голоса: ")
                        .append(notification.getStatusVoteInviteRequest())
                );
                forApprovals = new Notification(new StringBuilder()
                        .append("(->💙) 👎 Игрок ")
                        .append(invitedPlayer)
                        .append(" не согласился присоединиться к вашему союзу ")
                        .append(approvalsPlayers)
                        .append(" Все голоса: ")
                        .append(notification.getStatusVoteInviteRequest())
                );
            }
        }

        invitedPlayer.addNotification(forInvited.id(), forInvited);
        approvalsPlayers.forEach(player -> player.addNotification(forApprovals.id(), forApprovals));
    }
    public static void resultOfAttackPropose(Notification notification) {
        Player targetToAttackPlayer = notification.getTargetToAttackPlayer();
        Set<Player> attackers = notification.getAttackProposeApprovals().keySet();

        Notification forTargets;
        Notification forApprovals;

        boolean targetHaveUnion = targetToAttackPlayer.getRelations().values().contains(RelationType.UNION);
        boolean isAllApproved = notification.getAttackProposeApprovals().values().stream()
                .allMatch(accept -> accept);

        if (isAllApproved) {
            RelationType type = RelationType.getTypeForRequest(notification.request());

            if (targetHaveUnion) {
                Set<Player> targetUnion = targetToAttackPlayer.getRelations().entrySet().stream()
                        .filter(entry -> entry.getValue() == RelationType.UNION)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());

                Set<Player> allEnemies = new HashSet<>(targetUnion);
                allEnemies.add(targetToAttackPlayer);

                forTargets = new Notification(new StringBuilder()
                        .append("(->💔) Ваш союз: ")
                        .append(allEnemies)
                        .append(" был атакован союзом: ")
                        .append(attackers)
                );

                for (Player enemy : allEnemies) {
                    for (Player attacker : attackers) {
                        setRelationBetweenPlayer(enemy, attacker, type);
                    }
                    enemy.addNotification(forTargets.id(), forTargets);
                }

                forApprovals = new Notification(new StringBuilder()
                        .append("(->💔) 👍 Ваш союз: ")
                        .append(attackers)
                        .append(" одобрил нападение на игрока ")
                        .append(targetToAttackPlayer)
                        .append(", включая его союзников: ")
                        .append(targetUnion)
                );
            } else {
                for (Player attacker : attackers) setRelationBetweenPlayer(attacker, targetToAttackPlayer, type);

                forTargets = new Notification(new StringBuilder()
                        .append("(->💔) Союз: ")
                        .append(attackers)
                        .append(" объявил вам войну")
                );

                targetToAttackPlayer.addNotification(forTargets.id(), forTargets);

                forApprovals = new Notification(new StringBuilder()
                        .append("(->💔) 👍 Ваш союз: ")
                        .append(attackers)
                        .append(" одобрил нападение на игрока ")
                        .append(targetToAttackPlayer)
                );
            }
        } else {
            forApprovals = new Notification(new StringBuilder()
                    .append("(->💔) 👎 Ваш союз: ")
                    .append(attackers)
                    .append(" не одобрил нападение на игрока ")
                    .append(targetToAttackPlayer)
                    .append(" Голоса: ")
                    .append(notification.getStatusVoteAttackPropose())
            );
        }

        attackers.forEach(player -> player.addNotification(forApprovals.id(), forApprovals));
    }

    public static void setRelationBetweenPlayer(Player from, Player to, RelationType type) {
        if (from.equals(to)) return;
        from.setRelation(to, type);
        to.setRelation(from, type);
    }
}
