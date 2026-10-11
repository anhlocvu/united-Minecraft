package com.nibblenerds.unitedminecraft.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mojang.blaze3d.platform.InputConstants;

class AttackUseKeySwapTest {
	private static final InputConstants.Key LEFT_CLICK = InputConstants.Type.MOUSE.getOrCreate(0);
	private static final InputConstants.Key RIGHT_CLICK = InputConstants.Type.MOUSE.getOrCreate(1);
	private static final InputConstants.Key COMMA = InputConstants.Type.KEYBOARD.getOrCreate(InputConstants.KEY_COMMA);
	private static final InputConstants.Key PERIOD = InputConstants.Type.KEYBOARD.getOrCreate(InputConstants.KEY_PERIOD);
	private static final InputConstants.Key Z = InputConstants.Type.KEYBOARD.getOrCreate(InputConstants.KEY_Z);
	private static final InputConstants.Key X = InputConstants.Type.KEYBOARD.getOrCreate(InputConstants.KEY_X);

	private static AttackUseKeySwap.Plan plan(InputConstants.Key attack, InputConstants.Key use,
			InputConstants.Key savedAttack, InputConstants.Key savedUse) {
		return AttackUseKeySwap.plan(attack, use, LEFT_CLICK, RIGHT_CLICK, savedAttack, savedUse);
	}

	@Test
	void fromCustomKeysGoesToMouseAndRemembersThem() {
		AttackUseKeySwap.Plan plan = plan(COMMA, PERIOD, null, null);
		assertEquals(LEFT_CLICK, plan.attack());
		assertEquals(RIGHT_CLICK, plan.use());
		assertTrue(plan.rememberCurrent());
	}

	@Test
	void rebindingAfterwardsIsPickedUpBecauseTheCurrentKeysAreRead() {
		// Saved set is stale (Z/X), but the player has since rebound to comma/period in Controls:
		// leaving them still remembers what is bound now, never what was saved.
		AttackUseKeySwap.Plan plan = plan(COMMA, PERIOD, Z, X);
		assertTrue(plan.rememberCurrent());
		assertEquals(LEFT_CLICK, plan.attack());
	}

	@Test
	void fromMouseGoesBackToTheSavedKeys() {
		AttackUseKeySwap.Plan plan = plan(LEFT_CLICK, RIGHT_CLICK, Z, X);
		assertEquals(Z, plan.attack());
		assertEquals(X, plan.use());
		assertFalse(plan.rememberCurrent());
	}

	@Test
	void fromMouseWithNothingSavedUsesTheRecommendedKeys() {
		AttackUseKeySwap.Plan plan = plan(LEFT_CLICK, RIGHT_CLICK, null, null);
		assertEquals(COMMA, plan.attack());
		assertEquals(PERIOD, plan.use());
		assertFalse(plan.rememberCurrent());
	}

	@Test
	void aHalfSavedSetCountsAsNothingSaved() {
		AttackUseKeySwap.Plan plan = plan(LEFT_CLICK, RIGHT_CLICK, Z, null);
		assertEquals(COMMA, plan.attack());
	}

	@Test
	void oneMouseButtonAndOneKeyIsStillACustomSetup() {
		// Attack on comma with use still on right-click: not the mouse defaults, so it is
		// remembered as-is and the swap goes to the full mouse pair.
		AttackUseKeySwap.Plan plan = plan(COMMA, RIGHT_CLICK, null, null);
		assertTrue(plan.rememberCurrent());
		assertEquals(LEFT_CLICK, plan.attack());
	}
}
