package com.endtweaks.mixin;

import com.endtweaks.EndTweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.EndSpikeFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(EndSpikeFeature.class)
public abstract class EndSpikeFeatureMixin {
	@Shadow
	@Final
	private boolean crystalInvulnerable;

	@Shadow
	@Final
	private Optional<BlockPos> crystalBeamTarget;

	@Inject(method = "placeSpike", at = @At("HEAD"), cancellable = true)
	private void summonCrystalOnly(ServerLevelAccessor level, RandomSource random, EndSpikeFeature.EndSpike spike, CallbackInfo ci) {
		// The dragon respawn sequence is the only caller that uses invulnerable crystals with a beam target.
		boolean isDragonRespawn = this.crystalInvulnerable && this.crystalBeamTarget.isPresent();
		if (!isDragonRespawn || level.getLevel().getGameRules().get(EndTweaks.DRAGON_RESPAWN_PILLARS)) {
			return;
		}

		ci.cancel();
		// Spawn only the crystal, without regenerating the obsidian pillar, bedrock or cage.
		EndCrystal crystal = EntityTypes.END_CRYSTAL.create(level.getLevel(), EntitySpawnReason.STRUCTURE);
		if (crystal != null) {
			crystal.setBeamTarget(this.crystalBeamTarget.get());
			crystal.setPermanentlyInvulnerable(true);
			crystal.snapTo(spike.getCenterX() + 0.5, spike.getHeight() + 1, spike.getCenterZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
			level.addFreshEntity(crystal);
		}
	}
}
