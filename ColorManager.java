package GH_Donayre_src;

import robocode.*;
import java.awt.Color;

/**
 * Manages the visual appearance of the robot
 */
public class ColorManager {
    private AdvancedRobot robot;

    public ColorManager(AdvancedRobot robot) {
        this.robot = robot;
    }

    public void applyColors() {
        robot.setBodyColor(Color.WHITE);
        robot.setGunColor(Color.WHITE);
        robot.setRadarColor(Color.WHITE);
        robot.setBulletColor(Color.WHITE);
        robot.setScanColor(Color.WHITE);
    }
}