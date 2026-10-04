package com.endtweaks.mixin;

import com.endtweaks.EndTweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// SUMMONING_PILLARS is the third DragonRespawnStage constant.
@Mixin(targets = "net.minecraft.world.level.dimension.end.DragonRespawnStage$3")
public abstract class SummoningPillarsStageMixin {
	@Redirect(
		method = "tick",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z")
	)
	private boolean skipBlockClearing(ServerLevel level, BlockPos pos, boolean flag) {
		if (!level.getGameRules().get(EndTweaks.DRAGON_RESPAWN_PILLARS)) {
			return false;
		}
		return level.removeBlock(pos, flag);
	}

	@Redirect(
		method = "tick",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V")
	)
	private void skipExplosion(ServerLevel level, Entity source, double x, double y, double z, float radius, Level.ExplosionInteraction interaction) {
		if (!level.getGameRules().get(EndTweaks.DRAGON_RESPAWN_PILLARS)) {
			return;
		}
		level.explode(source, x, y, z, radius, interaction);
	}
}
