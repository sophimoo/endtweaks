package com.endtweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class EndTweaks implements ModInitializer {
	public static final String MOD_ID = "endtweaks";

	public static final GameRule<Boolean> DRAGON_RESPAWN_PILLARS = GameRuleBuilder
		.forBoolean(false)
		.category(GameRuleCategory.MOBS)
		.buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "dragon_respawn_pillars"));

	public static final GameRule<Boolean> DRAGON_BLOCK_DAMAGE = GameRuleBuilder
		.forBoolean(false)
		.category(GameRuleCategory.MOBS)
		.buildAndRegister(Identifier.fromNamespaceAndPath(MOD_ID, "dragon_block_damage"));

	@Override
	public void onInitialize() {
	}
}
