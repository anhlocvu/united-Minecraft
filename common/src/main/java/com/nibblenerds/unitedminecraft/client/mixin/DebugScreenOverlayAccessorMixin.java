package com.nibblenerds.unitedminecraft.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.nibblenerds.unitedminecraft.client.access.DebugScreenOverlayAccess;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

/** Exposes the F3 overlay's private level/chunk lookups - see {@link DebugScreenOverlayAccess}. */
@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayAccessorMixin implements DebugScreenOverlayAccess {
	@Invoker("getLevel")
	@Override
	public abstract Level unitedMinecraft$getLevel();

	@Invoker("getClientChunk")
	@Override
	public abstract LevelChunk unitedMinecraft$getClientChunk();

	@Invoker("getServerChunk")
	@Override
	public abstract LevelChunk unitedMinecraft$getServerChunk();
}
