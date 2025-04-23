package GH_Donayre_src;

import java.awt.Color;

import robocode.*;
import robocode.util.Utils;

public class RadarSystem {
    private AdvancedRobot robot; // just for type safety

    private String currentTarget = null;
    private long lastScanTime = 0; // keeps track of the tick/time when an enemy was scanned

    private double closestDistanceThisTick = Double.MAX_VALUE; // used for finding the nearest robot
    private long lastRadarTick = -1; // prevents resetting closestDistanceThisTick more than once pet tick.
    private int killCount = 0; // kase gusto ko.

    public RadarSystem(AdvancedRobot robot) {
        this.robot = robot;
    }

    public String getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(String target) {
        this.currentTarget = target;
    }

    public void update() {
        // if have not found any enemy lately and find one.
        if (robot.getTime() - lastScanTime > 5) {
            robot.setTurnRadarRight(Double.POSITIVE_INFINITY);
        }

        if (robot.getTime() - lastScanTime > 10) {
            currentTarget = null;
        }
    }

    public void onScannedRobot(ScannedRobotEvent enemy) {
        long currentTick = robot.getTime();

        // every second/tick reset the closest distance
        if (currentTick != lastRadarTick) {
            closestDistanceThisTick = Double.MAX_VALUE;
            lastRadarTick = currentTick;
        }
        // remember when we last saw someone
        lastScanTime = currentTick;

        double distance = enemy.getDistance();
        boolean shouldTrack = false;

        if (distance < closestDistanceThisTick) { // immediately switch our target to closest enemy.
            currentTarget = enemy.getName();
            closestDistanceThisTick = distance;
            shouldTrack = true;
        }

        if (shouldTrack) { // points are radar to the enemy to keep track of it
            double radarTurn = robot.getHeadingRadians() + enemy.getBearingRadians()
                    - robot.getRadarHeadingRadians();
            robot.setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn) * 1.9); // turn the radar to the enemy
                                                                                        // with overshoot so even if the
                                                                                        // enemy moves a little in the
                                                                                        // next tick we can still see
                                                                                        // them.
        }
    }

    public void onRobotDeath(RobotDeathEvent enemy) {
        if (enemy.getName().equals(currentTarget)) {
            currentTarget = null;
            killCount++;
            int fadeAmount = killCount * 55; // Fade faster with more kills
            int greenBlue = Math.max(0, 255 - fadeAmount); // Decrease G & B

            Color redderColor = new Color(255, greenBlue, greenBlue); // Max red, fading GB
            robot.setColors(redderColor, redderColor, redderColor);

        }
    }
}
