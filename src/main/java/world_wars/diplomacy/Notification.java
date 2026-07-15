package world_wars.diplomacy;

import world_wars.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Notification{
    private static int counter = 1;

    private static boolean setCanAccept(RelationRequest request) {
        return switch (request) {
            case REQUEST_NEUTRAL, REQUEST_FRIEND, REQUEST_CREATE_UNION, REQUEST_JOIN_IN_UNION, REQUEST_INVITE_IN_UNION, PROPOSE_UNION_ATTACK_A_PLAYER -> true;
            case DECLARE_NEUTRAL, DECLARE_WAR, LEAVE_FROM_UNION, NOTIFY -> false;
        };
    }
    private static boolean setNeedApproval(RelationRequest request) {
        return switch (request) {
            case REQUEST_JOIN_IN_UNION, REQUEST_INVITE_IN_UNION, PROPOSE_UNION_ATTACK_A_PLAYER -> true;
            case REQUEST_NEUTRAL, REQUEST_FRIEND, REQUEST_CREATE_UNION, DECLARE_NEUTRAL, DECLARE_WAR, LEAVE_FROM_UNION, NOTIFY -> false;
        };
    }

    private static boolean isAllVoted(Notification notification) {
        return switch (notification.request) {
            case REQUEST_JOIN_IN_UNION -> notification.joinRequestApprovals.size() == notification.countApprovalsVote;
            case REQUEST_INVITE_IN_UNION -> notification.inviteRequestApprovals.size() == notification.countApprovalsVote;
            case PROPOSE_UNION_ATTACK_A_PLAYER -> notification.attackProposeApprovals.size() == notification.countApprovalsVote;
            default -> false;
        };
    }
    private static void checkResultAndNotify(Notification notification) {
        switch (notification.request) {
            case REQUEST_JOIN_IN_UNION -> DiplomacyManager.resultOfJoinRequest(notification);
            case REQUEST_INVITE_IN_UNION -> DiplomacyManager.resultOfInviteRequest(notification);
            case PROPOSE_UNION_ATTACK_A_PLAYER -> DiplomacyManager.resultOfAttackPropose(notification);
        }
    }
    public static String getAlreadyVotedFor(Notification notification) {
        return notification.countApprovalsVote + "/" + switch (notification.request) {
            case REQUEST_JOIN_IN_UNION -> notification.joinRequestApprovals.size();
            case REQUEST_INVITE_IN_UNION -> notification.inviteRequestApprovals.size();
            case PROPOSE_UNION_ATTACK_A_PLAYER -> notification.attackProposeApprovals.size();
            default -> throw new IllegalStateException("");
        };
    }

    private int id;
    private Player fromPlayer;
    private RelationRequest request;
    private boolean isCanAccept;
    private boolean isNeedApproval;

    private int countApprovalsVote; // счетчик для голосов
    private StringBuilder message; // notify

    private Map<Player, Boolean> joinRequestApprovals;
    private Player invitedPlayerInUnion;
    private Map<Player, Boolean> inviteRequestApprovals;
    private Player targetToAttackPlayer;
    private Map<Player, Boolean> attackProposeApprovals;

    public Notification(Player fromPlayer, RelationRequest request) {
        this.id = counter++;
        this.fromPlayer = fromPlayer;
        this.request = request;
        this.isCanAccept = setCanAccept(request);
        this.isNeedApproval = setNeedApproval(request);
    }

    public Notification(StringBuilder message) {
        this.id = counter++;
        this.message = message;
        this.request = RelationRequest.NOTIFY;
        this.isCanAccept = setCanAccept(RelationRequest.NOTIFY);
        this.isNeedApproval = setNeedApproval(RelationRequest.NOTIFY);
    }

    public int id() { return this.id; }
    public Player fromPlayer() { return this.fromPlayer; }
    public RelationRequest request() { return this.request; }
    public boolean isCanAccept() { return this.isCanAccept; }
    public boolean isNeedApproval() { return this.isNeedApproval; }
    public void voteFromApproval() { // даже если проигнорировал
        this.countApprovalsVote++;
        if (isAllVoted(this)) checkResultAndNotify(this);
    }

    public Map<Player, Boolean> getJoinRequestApprovals() { return this.joinRequestApprovals; }
    public void initJoinRequestApprovals() {
        this.joinRequestApprovals = new HashMap<>();
    }
    public void addApprovalToJoinRequest(Player whoNeedApprove) {
        this.joinRequestApprovals.put(whoNeedApprove, false);
    }
    public String getStatusVoteJoinRequest() {
        int whoApproved = (int) joinRequestApprovals.values().stream()
                .filter(Boolean::booleanValue)
                .count();
        int whoNeedApproved = joinRequestApprovals.size();
        return whoApproved + "/" + whoNeedApproved + " 👍";
    }

    public Map<Player, Boolean> getInviteRequestApprovals() { return this.inviteRequestApprovals; }
    public void initInviteRequestApprovals(Player invitedPlayer) {
        this.inviteRequestApprovals = new HashMap<>();
        this.inviteRequestApprovals.put(invitedPlayer, false);
        this.invitedPlayerInUnion = invitedPlayer;
    }
    public void addApprovalToInviteRequest(Player whoNeedApprove) {
        this.inviteRequestApprovals.put(whoNeedApprove, false);
    }
    public Player getInvitedPlayerInUnion() { return this.invitedPlayerInUnion; }
    public String getStatusVoteInviteRequest() {
        int whoApproved = (int) inviteRequestApprovals.values().stream()
                .filter(Boolean::booleanValue)
                .count();
        int whoNeedApproved = inviteRequestApprovals.size();
        return whoApproved + "/" + whoNeedApproved + " 👍";
    }
    private Set<Player> getUnionForInvited() {
        Set<Player> players = new HashSet<>(inviteRequestApprovals.keySet());
        players.remove(invitedPlayerInUnion);
        return players;
    }

    public Map<Player, Boolean> getAttackProposeApprovals() { return this.attackProposeApprovals; }
    public void initAttackProposeApprovals(Player targetToAttackPlayer) {
        this.attackProposeApprovals = new HashMap<>();
        this.targetToAttackPlayer = targetToAttackPlayer;
    }
    public void addApprovalToAttackPropose(Player whoNeedApproved) {
        this.attackProposeApprovals.put(whoNeedApproved, false);
    }
    public Player getTargetToAttackPlayer() { return this.targetToAttackPlayer; }
    public String getStatusVoteAttackPropose() {
        int whoApproved = (int) attackProposeApprovals.values().stream()
                .filter(Boolean::booleanValue)
                .count();
        int whoNeedApproved = attackProposeApprovals.size();
        return whoApproved + "/" + whoNeedApproved + " 👍";
    }

    private String getNameFromRequestV2(RelationRequest request) {
        StringBuilder sb = new StringBuilder();

        if (this.isCanAccept) {
            sb.append("❗❗❗ #");
        } else {
            sb.append("❕❕❕ #");
        }

        sb.append(id);

        switch (request) {
            case DECLARE_NEUTRAL -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" перестал с вами дружить (💚->🧡)");
            }
            case REQUEST_NEUTRAL -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" предлагает вам перемирие (💔->🧡)");
            }
            case REQUEST_FRIEND -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" предлагает вам дружбу (🧡->💚)");
            }
            case REQUEST_CREATE_UNION -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" предлагает вам создать союз (💚->💙)");
            }
            case REQUEST_JOIN_IN_UNION -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" просит принять его в ваш союз: ")
                        .append(joinRequestApprovals.keySet())
                        .append(" (->💙) Проголосовало: ")
                        .append(getAlreadyVotedFor(this));
            }
            case REQUEST_INVITE_IN_UNION -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" предлагает добавить игрока ")
                        .append(invitedPlayerInUnion)
                        .append(" в союз: ")
                        .append(getUnionForInvited())
                        .append(" (->💙) Проголосовало: ")
                        .append(getAlreadyVotedFor(this));
            }
            case LEAVE_FROM_UNION -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" выходит из вашего союза (💙->🧡)");
            }
            case DECLARE_WAR -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" объявляет вам войну (->💔)");
            }
            /*
                if (isAttackFromUnion) {
                    sb.append(" : Союз ").append(attackers);
                } else {
                    sb.append(" : Игрок ").append(fromPlayer);
                }
                if (targetToAttackPlayerInUnion) {
                    sb.append(" объявляет войну вашему союзнику, включая вас (->💔)");
                } else {
                    sb.append(" объявляет вам войну (->💔)");
                }
                 */
            case PROPOSE_UNION_ATTACK_A_PLAYER -> {
                sb.append(" : Игрок ")
                        .append(fromPlayer)
                        .append(" предлагает вашему союзу: ")
                        .append(attackProposeApprovals.keySet())
                        .append(" напасть на игрока ")
                        .append(targetToAttackPlayer)
                        .append(" (->💔) Проголосовало: ")
                        .append(getAlreadyVotedFor(this));
            }
            case NOTIFY -> sb.append(" ").append(message);
        };
        return sb.toString();
    }

    @Override
    public String toString() {
        return getNameFromRequestV2(request);
    }
}
