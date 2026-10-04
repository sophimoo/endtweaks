package com.endtweaks.mixin;

import com.endtweaks.EndTweaks;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin {
	@Redirect(
		method = "checkWalls",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;")
	)
	private Object respectDragonBlockDamageRule(GameRules rules, GameRule<?> rule) {
		if (rule == GameRules.MOB_GRIEFING && !rules.get(EndTweaks.DRAGON_BLOCK_DAMAGE)) {
			return false;
		}
		return rules.get(rule);
	}
}
