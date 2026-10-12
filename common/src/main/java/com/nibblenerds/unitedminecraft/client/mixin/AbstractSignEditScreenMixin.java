package com.nibblenerds.unitedminecraft.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignTextSlot;

/**
 * Handles narration for {@link AbstractSignEditScreen}: announces sign side and initial line on open,
 * narrates line changes when navigating with Up/Down/Enter, and announces update confirmation on close.
 */
@Mixin(AbstractSignEditScreen.class)
public abstract class AbstractSignEditScreenMixin extends Screen {
	@Shadow
	protected SignBlockEntity sign;

	@Shadow
	@Final
	private String[] messages;

	@Shadow
	@Final
	private SignTextSlot slot;

	@Shadow
	private int line;

	@Unique
	private int unitedMinecraft$lineBeforeKey;

	protected AbstractSignEditScreenMixin(Component title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"))
	private void unitedMinecraft$narrateOpen(CallbackInfo ci) {
		Component sideName = Component.translatable(slot == SignTextSlot.FRONT
				? "united_minecraft.narrate.sign_side_front"
				: "united_minecraft.narrate.sign_side_back");
		String currentLine = messages[line];
		Component lineText = (currentLine == null || currentLine.isBlank())
				? Component.translatable("united_minecraft.narrate.sign_line_empty", line + 1)
				: Component.translatable("united_minecraft.narrate.sign_line", line + 1, currentLine);
		Minecraft.getInstance().getNarrator().saySystemNow(Component.translatable(
				"united_minecraft.narrate.sign_edit_open", this.title, sideName, lineText));
	}

	@Inject(method = "keyPressed", at = @At("HEAD"))
	private void unitedMinecraft$captureLineBefore(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
		unitedMinecraft$lineBeforeKey = line;
	}

	@Inject(method = "keyPressed", at = @At("RETURN"))
	private void unitedMinecraft$narrateLineChange(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
		if (line != unitedMinecraft$lineBeforeKey) {
			String currentLine = messages[line];
			Component lineText = (currentLine == null || currentLine.isBlank())
					? Component.translatable("united_minecraft.narrate.sign_line_empty", line + 1)
					: Component.translatable("united_minecraft.narrate.sign_line", line + 1, currentLine);
			Minecraft.getInstance().getNarrator().saySystemNow(lineText);
		}
	}

	@Inject(method = "removed", at = @At("TAIL"))
	private void unitedMinecraft$narrateClosed(CallbackInfo ci) {
		Minecraft.getInstance().getNarrator().saySystemNow(
				Component.translatable("united_minecraft.narrate.sign_updated"));
	}
}
