package com.nibblenerds.unitedminecraft.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nibblenerds.unitedminecraft.client.access.DebugScreenOverlayAccess;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

/**
 * A live, structured copy of the F3 overlay. Vanilla flattens every entry's output into two
 * lists of strings before drawing, losing which entry (or group) a line came from, so this runs
 * the same enabled entries itself every tick with a displayer that keeps them apart. Each
 * section is one entry or group; its lines are recomputed every tick, so reading a line always
 * gives the current value. An entry that adds no text (the renderer toggles) makes no section.
 */
final class DebugOverlayModel {
	/** One entry's (or group's) lines. */
	record Section(Identifier id, List<String> lines) {
	}

	private static List<Section> sections = List.of();

	private DebugOverlayModel() {
	}

	static List<Section> sections() {
		return sections;
	}

	static boolean isOverlayShown(Minecraft client) {
		return client.getDebugOverlay().showDebugScreen();
	}

	static void reset() {
		sections = List.of();
	}

	static void tick(Minecraft client) {
		if (client.level == null || !isOverlayShown(client)) {
			reset();
			return;
		}
		DebugScreenOverlayAccess access = (DebugScreenOverlayAccess) client.getDebugOverlay();
		Level level = access.unitedMinecraft$getLevel();
		if (level == null) {
			return;
		}

		Map<Identifier, List<String>> collected = new LinkedHashMap<>();
		Collector collector = new Collector(collected);
		for (Identifier id : client.debugEntries.getCurrentlyEnabled()) {
			DebugScreenEntry entry = DebugScreenEntries.getEntry(id);
			if (entry == null) {
				continue;
			}
			collector.current = id;
			entry.display(collector, level, access.unitedMinecraft$getClientChunk(), access.unitedMinecraft$getServerChunk());
		}
		List<Section> built = new ArrayList<>(collected.size());
		collected.forEach((id, lines) -> built.add(new Section(id, List.copyOf(lines))));
		sections = List.copyOf(built);
	}

	/** Files plain lines under the entry being displayed and grouped lines under their group. */
	private static final class Collector implements DebugScreenDisplayer {
		private final Map<Identifier, List<String>> out;
		Identifier current;

		Collector(Map<Identifier, List<String>> out) {
			this.out = out;
		}

		@Override
		public void addPriorityLine(String line) {
			add(current, line);
		}

		@Override
		public void addLine(String line) {
			add(current, line);
		}

		@Override
		public void addToGroup(Identifier group, Collection<String> lines) {
			for (String line : lines) {
				add(group, line);
			}
		}

		@Override
		public void addToGroup(Identifier group, String line) {
			add(group, line);
		}

		private void add(Identifier section, String line) {
			out.computeIfAbsent(section, k -> new ArrayList<>()).add(line);
		}
	}
}
