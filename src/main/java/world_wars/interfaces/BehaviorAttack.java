package world_wars.interfaces;

import world_wars.entity.Build;
import world_wars.entity.Unit;

public interface BehaviorAttack {
    //boolean canAttackUnit & canAttackBuild ?
    void attackUnit(Unit target);
    void attackBuild(Build target);
}
