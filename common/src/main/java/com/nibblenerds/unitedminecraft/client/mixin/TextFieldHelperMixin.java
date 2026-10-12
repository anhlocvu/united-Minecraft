package com.nibblenerds.unitedminecraft.client.mixin;

import java.util.function.Supplier;

import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.util.Util;

/**
 * Provides delta-narration for {@link TextFieldHelper}, the internal text engine backing
 * Minecraft's sign editing screens ({@link net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen}).
 * Narrates characters typed/pasted, characters/words deleted, and text navigated over with arrow keys.
 */
@Mixin(TextFieldHelper.class)
public abstract class TextFieldHelperMixin {
	@Shadow
	@Final
	private Supplier<String> getMessageFn;

	@Shadow
	private int cursorPos;

	@Shadow
	private int selectionPos;

	@Shadow
	private String getSelected(String text) {
		throw new AssertionError();
	}

	@Inject(method = "insertText(Ljava/lang/String;Ljava/lang/String;)V", at = @At("HEAD"))
	private void unitedMinecraft$narrateInsertedText(String currentText, String text, CallbackInfo ci) {
		if (text != null && !text.isEmpty()) {
			unitedMinecraft$narrate(text);
		} else if (selectionPos != cursorPos && currentText != null) {
			String selected = getSelected(currentText);
			if (selected != null && !selected.isBlank()) {
				unitedMinecraft$narrate(selected);
			}
		}
	}

	@Inject(method = "keyPressed", at = @At("HEAD"))
	private void unitedMinecraft$narrateCursorMovement(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
		if (Minecraft.getInstance().hasShiftDown()) {
			return;
		}

		String msg = getMessageFn.get();
		if (msg == null || msg.isEmpty()) {
			return;
		}

		boolean hasCtrl = Minecraft.getInstance().hasControlDown();
		switch (event.key()) {
			case InputConstants.KEY_LEFT -> {
				int target = hasCtrl
						? StringSplitter.getWordPosition(msg, -1, cursorPos, true)
						: Util.offsetByCodepoints(msg, cursorPos, -1);
				unitedMinecraft$narrate(unitedMinecraft$textBetween(target, cursorPos));
			}
			case InputConstants.KEY_RIGHT -> {
				int target = hasCtrl
						? StringSplitter.getWordPosition(msg, 1, cursorPos, true)
						: Util.offsetByCodepoints(msg, cursorPos, 1);
				unitedMinecraft$narrate(unitedMinecraft$textBetween(cursorPos, target));
			}
			case InputConstants.KEY_HOME -> {
				if (!msg.isEmpty()) {
					unitedMinecraft$narrate(msg.substring(0, 1));
				}
			}
			case InputConstants.KEY_END -> {
				if (!msg.isEmpty()) {
					unitedMinecraft$narrate(msg.substring(msg.length() - 1));
				}
			}
			default -> {
			}
		}
	}

	@Inject(method = "keyPressed", at = @At("RETURN"))
	private void unitedMinecraft$narrateSelection(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
		if (selectionPos != cursorPos) {
			String msg = getMessageFn.get();
			if (msg != null && !msg.isEmpty()) {
				String selected = getSelected(msg);
				if (selected != null && !selected.isBlank()) {
					unitedMinecraft$narrate(selected);
				}
			}
		}
	}

	@Inject(method = "removeCharsFromCursor", at = @At("HEAD"))
	private void unitedMinecraft$narrateErasedText(int count, CallbackInfo ci) {
		String msg = getMessageFn.get();
		if (msg == null || msg.isEmpty()) {
			return;
		}
		if (selectionPos != cursorPos) {
			String selected = getSelected(msg);
			if (selected != null && !selected.isBlank()) {
				unitedMinecraft$narrate(selected);
			}
		} else {
			int target = Util.offsetByCodepoints(msg, cursorPos, count);
			unitedMinecraft$narrate(unitedMinecraft$textBetween(cursorPos, target));
		}
	}

	@Unique
	private String unitedMinecraft$textBetween(int start, int end) {
		String msg = getMessageFn.get();
		if (msg == null) {
			return "";
		}
		int from = Math.max(0, Math.min(start, end));
		int to = Math.min(msg.length(), Math.max(start, end));
		return from == to ? "" : msg.substring(from, to);
	}

	@Unique
	private void unitedMinecraft$narrate(String text) {
		if (text != null && !text.isEmpty()) {
			Minecraft.getInstance().getNarrator().saySystemNow(text);
		}
	}
}
