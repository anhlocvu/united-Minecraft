package com.nibblenerds.unitedminecraft.client.access;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Duck interface implemented by {@link
 * com.nibblenerds.unitedminecraft.client.mixin.DebugScreenOverlayAccessorMixin} to reach the
 * overlay's private level and chunk lookups, which every {@code DebugScreenEntry} needs as
 * arguments and which hold vanilla's own cache of the server chunk.
 */
public interface DebugScreenOverlayAccess {
	Level unitedMinecraft$getLevel();

	LevelChunk unitedMinecraft$getClientChunk();

	/** May be null: vanilla fetches it asynchronously and entries are written to cope. */
	LevelChunk unitedMinecraft$getServerChunk();
}
