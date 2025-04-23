package GH_Donayre_src;

import robocode.*;
import robocode.util.Utils;
import java.awt.geom.Point2D;

public class TargetingSystem {
    private AdvancedRobot robot;
    private double lastKnownEnemyEnergy = 100.0;
    private boolean enemyFiredRecently = false;
    private double lastEnemyHeading = 0;

    public TargetingSystem(AdvancedRobot robot) {
        this.robot = robot;
    }

    public void updateEnemyEnergy(ScannedRobotEvent enemy) {
        double currentEnemyEnergy = enemy.getEnergy();
        double energyDrop = lastKnownEnemyEnergy - currentEnemyEnergy;
        enemyFiredRecently = energyDrop > 0 && energyDrop <= 3;
        lastKnownEnemyEnergy = currentEnemyEnergy;
    }

    public boolean hasEnemyFired() {
        return enemyFiredRecently;
    }

    public void aimAndFire(ScannedRobotEvent enemy) {
        Point2D.Double predictedPosition = simulateEnemyPath(enemy); // predicts the absolute x,y coordinate of
                                                                     // the enemy
        double gunTurnDegrees = calculateGunTurnTo(predictedPosition);
        robot.setTurnGunRight(gunTurnDegrees);

        if (robot.getGunHeat() == 0 && Math.abs(gunTurnDegrees) < 5) {
            double firePower = chooseFirePower(enemy.getDistance());
            robot.setFire(firePower);
        }
    }

    private double chooseFirePower(double distance) {
        if (distance < 100)
            return 3.0;
        if (distance < 200)
            return 2.5;
        if (distance < 300)
            return 2.0;
        if (distance < 400)
            return 1.5;
        return 1.0;
    }

    private Point2D.Double simulateEnemyPath(ScannedRobotEvent enemy) {
        double bulletSpeed = 20 - 3 * chooseFirePower(enemy.getDistance());
        double absoluteBearing = robot.getHeadingRadians() + enemy.getBearingRadians();

        // polar to cartesian coordinate.
        double enemyX = robot.getX() + enemy.getDistance() * Math.sin(absoluteBearing);
        double enemyY = robot.getY() + enemy.getDistance() * Math.cos(absoluteBearing);
        double predictedX = enemyX;
        double predictedY = enemyY;
        double deltaTime = 0; // can be called change in time.

        double enemyHeading = enemy.getHeadingRadians(); // where the enemy is heading
        double enemyHeadingChange = enemyHeading - lastEnemyHeading; // change in direction
        lastEnemyHeading = enemyHeading;

        while (true) {
            double predictedEnemyDistanceFromRobot = Point2D.distance(robot.getX(), robot.getY(), predictedX,
                    predictedY);
            double bulletDistanceAfterDeltaTime = (++deltaTime) * bulletSpeed;

            if (bulletDistanceAfterDeltaTime >= predictedEnemyDistanceFromRobot) {
                break;
            }

            enemyHeading += enemyHeadingChange; // updates which direction the enemy is heading after a turn/tick
            predictedX += Math.sin(enemyHeading) * enemy.getVelocity(); // treat velocity as the radius then simply
                                                                        // convert
            // it to cartesian coodinates.
            predictedY += Math.cos(enemyHeading) * enemy.getVelocity();
        }

        return new Point2D.Double(predictedX, predictedY);
    }

    private double calculateGunTurnTo(Point2D.Double targetPosition) {
        // calculus yarn?
        double deltaX = targetPosition.x - robot.getX();
        double deltaY = targetPosition.y - robot.getY();
        double angleToTarget = Math.atan2(deltaX, deltaY); // returns angle in radians.

        double gunTurnRadians = Utils.normalRelativeAngle(angleToTarget - robot.getGunHeadingRadians());
        return Math.toDegrees(gunTurnRadians);
    }

    public void fireMaxPower() {
        if (robot.getGunHeat() == 0) {
            robot.fire(3);
        }
    }
}
