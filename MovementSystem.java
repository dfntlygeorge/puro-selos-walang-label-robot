package GH_Donayre_src;

import robocode.*;

public class MovementSystem {
    private AdvancedRobot robot;
    private int moveDirection = 1; // 1-forward, -1-backward
    private boolean isMoving = true;

    // receives the robot from the main class so we can do something with it. It's
    // like prop drilling in react.
    public MovementSystem(AdvancedRobot robot) {
        this.robot = robot;
    }

    public int getMoveDirection() {
        return moveDirection;
    }

    public void executeMovement(ScannedRobotEvent enemy, boolean enemyFired) {
        double bearing = enemy.getBearing();// returns angle of the enemy relative to my heading.
        // stop and go movement, basically we want to mess with their prediction logic.
        // That's dirty.
        if (enemyFired) {
            isMoving = false;
            robot.setAhead(0);
        } else if (!enemyFired && !isMoving) { // move if the enemy has not fired and the our robot is not moving.
            isMoving = true;
            robot.setAhead(150 * moveDirection); // either forward or backward.
        }

        if (isMoving) {
            robot.setTurnRight(bearing + 90 - (15 * moveDirection)); // move perpendicular and add offset so it's not
                                                                     // linear. It would look
                                                                     // like our robot is circling the enemy. Shet i
                                                                     // never knew I would use math like this.
        }
    }

    public void onHitByBullet(HitByBulletEvent bullet) {
        robot.setTurnRight(90 - bullet.getBearing()); // sets the direction we're moving
        robot.setAhead(100 * moveDirection); // move

        // adds unpredictability, why 0.7? kasi lucky 7 HASDHASHDHAS
        if (Math.random() > 0.7) {
            moveDirection *= -1;
        }
    }

    public void onHitWall() {
        moveDirection *= -1; // flips direction so that we our robot moves it is not toward the wall again.
        robot.setBack(100); // quick response when we hit a wall.
    }

    public void avoidRobot() { // shy type tayo
        robot.setBack(50);
        robot.setTurnRight(30);
    }

}