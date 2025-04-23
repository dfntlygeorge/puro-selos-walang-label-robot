package GH_Donayre_src;

import robocode.*;

public class PuroSelosWalangLabel extends AdvancedRobot {

	// components
	private RadarSystem radarSystem;
	private MovementSystem movementSystem;
	private TargetingSystem targetingSystem;
	private RammingSystem rammingSystem;
	private ColorManager colorManager;

	public void run() {
		radarSystem = new RadarSystem(this);
		movementSystem = new MovementSystem(this);
		targetingSystem = new TargetingSystem(this);
		rammingSystem = new RammingSystem(this);
		colorManager = new ColorManager(this);

		colorManager.applyColors();

		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);

		while (true) {
			radarSystem.update(); // search enemies
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent dumaanSiCrushEvent) {
		radarSystem.onScannedRobot(dumaanSiCrushEvent);

		// focus only on the targeted robot.
		if (!dumaanSiCrushEvent.getName().equals(radarSystem.getCurrentTarget())) {
			return;
		}

		targetingSystem.updateEnemyEnergy(dumaanSiCrushEvent);

		if (rammingSystem.doWeRamItOrNah(dumaanSiCrushEvent)) {
			rammingSystem.executeRamming(dumaanSiCrushEvent);
		} else {
			movementSystem.executeMovement(dumaanSiCrushEvent, targetingSystem.hasEnemyFired());
			targetingSystem.aimAndFire(dumaanSiCrushEvent);
		}
	}

	public void onHitByBullet(HitByBulletEvent tekaLangBakitAkoEvent) {
		if (!rammingSystem.isRamming()) {
			movementSystem.onHitByBullet(tekaLangBakitAkoEvent);
		}
	}

	public void onHitWall() { // landi HASDHASHDHAS
		movementSystem.onHitWall();
		rammingSystem.onHitWall();
	}

	public void onHitRobot(HitRobotEvent loveAtFirstHitEvent) {
		radarSystem.setCurrentTarget(loveAtFirstHitEvent.getName());
		targetingSystem.fireMaxPower();
		if (rammingSystem.isRamming()) {
			rammingSystem.continueRamming(loveAtFirstHitEvent);
		} else {
			movementSystem.avoidRobot();
		}
	}

	public void onRobotDeath(RobotDeathEvent nagFadeSiKaSituationshipMoEvent) {
		radarSystem.onRobotDeath(nagFadeSiKaSituationshipMoEvent);
		rammingSystem.onRobotDeath(nagFadeSiKaSituationshipMoEvent);
	}

}