package GH_Donayre_src;

import robocode.*;
import robocode.util.Utils;

public class RammingSystem {
    private AdvancedRobot robot;
    private boolean isRamming = false;
    private String currentTarget = null;

    public RammingSystem(AdvancedRobot robot) {
        this.robot = robot;
    }

    public boolean isRamming() {
        return isRamming;
    }

    public boolean doWeRamItOrNah(ScannedRobotEvent enemy) {
        return enemy.getEnergy() < 20 && enemy.getDistance() < 150; // ram it if it's low health and near.
    }

    public void executeRamming(ScannedRobotEvent enemy) {
        // ramming mode and this robot is the target.
        isRamming = true;
        currentTarget = enemy.getName();

        robot.setTurnRight(enemy.getBearing()); // turn to the enemy
        robot.setAhead(enemy.getDistance() + 5); // ram it bitch (add 5 to ensure it's de- eme)

        double gunDegrees = Utils.normalRelativeAngleDegrees(
                robot.getHeading() + enemy.getBearing() - robot.getGunHeading()); // gets the direction or degree our
                                                                                  // gun
        // should turn
        robot.setTurnGunRight(gunDegrees); // points the gun to the enemy.

        // Fire at point-blank range if gun is aligned
        if (robot.getGunHeat() == 0 && Math.abs(gunDegrees) < 10) { // only fire if we're allowed to and if the gun is
                                                                    // pointing close enough to the enemies.
            robot.setFire(3); // maxxxxx
        }
    }

    public void continueRamming(HitRobotEvent enemy) {
        robot.setTurnRight(enemy.getBearing());
        robot.setAhead(40);
    }

    public void onHitWall() {
        if (isRamming) {
            isRamming = false;
        }
    }

    public void onRobotDeath(RobotDeathEvent enemy) {
        if (isRamming && enemy.getName().equals(currentTarget)) {
            isRamming = false;
            currentTarget = null;
        }
    }
}