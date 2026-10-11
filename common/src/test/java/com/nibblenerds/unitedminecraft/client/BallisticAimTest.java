package com.nibblenerds.unitedminecraft.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.minecraft.world.phys.Vec3;

class BallisticAimTest {
	private static final Vec3 EYE = new Vec3(0.0, 64.0, 0.0);
	// A fully drawn bow.
	private static final double FULL_DRAW_SPEED = 3.0;

	@Test
	void levelTargetNeedsAnUpwardArc() {
		CameraUtil.BallisticAim aim = CameraUtil.ballisticAim(EYE, new Vec3(0.0, 64.0, 30.0), FULL_DRAW_SPEED);
		assertTrue(aim.reachable());
		// Vanilla pitch is negative looking up: gravity means aiming above a level target.
		assertTrue(aim.pitch() < 0.0f);
		assertEquals(0.0f, aim.yaw(), 1.0e-3f);
	}

	@Test
	void furtherTargetsNeedMoreArc() {
		float near = CameraUtil.ballisticAim(EYE, new Vec3(0.0, 64.0, 15.0), FULL_DRAW_SPEED).pitch();
		float far = CameraUtil.ballisticAim(EYE, new Vec3(0.0, 64.0, 50.0), FULL_DRAW_SPEED).pitch();
		assertTrue(far < near);
	}

	@Test
	void aSlowerArrowNeedsMoreArcForTheSameShot() {
		Vec3 target = new Vec3(0.0, 64.0, 25.0);
		float full = CameraUtil.ballisticAim(EYE, target, FULL_DRAW_SPEED).pitch();
		float half = CameraUtil.ballisticAim(EYE, target, 1.5).pitch();
		assertTrue(half < full);
	}

	@Test
	void aTargetBeyondTheArrowsRangeIsUnreachable() {
		assertFalse(CameraUtil.ballisticAim(EYE, new Vec3(0.0, 64.0, 400.0), 1.0).reachable());
	}

	@Test
	void yawFollowsMinecraftsConvention() {
		// Yaw 0 faces south (+Z), and 90 faces west (-X).
		assertEquals(90.0f, CameraUtil.ballisticAim(EYE, new Vec3(-20.0, 64.0, 0.0), FULL_DRAW_SPEED).yaw(), 1.0e-3f);
		assertEquals(-90.0f, CameraUtil.ballisticAim(EYE, new Vec3(20.0, 64.0, 0.0), FULL_DRAW_SPEED).yaw(), 1.0e-3f);
	}
}
