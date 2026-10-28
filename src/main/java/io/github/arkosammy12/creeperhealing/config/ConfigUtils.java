package io.github.arkosammy12.creeperhealing.config;

import io.github.arkosammy12.creeperhealing.CreeperHealing;

public final class ConfigUtils {

    private ConfigUtils() {
    }

    public static String getReplaceMapEntry(String blockIdentifier) {
        return CreeperHealing.CONFIG.replace_map.get(blockIdentifier);
    }

    public static long getExplosionHealDelay() {
        return toTicks(CreeperHealing.CONFIG.delays.explosion_heal_delay.get());
    }

    public static long getBlockPlacementDelay() {
        return toTicks(CreeperHealing.CONFIG.delays.block_placement_delay.get());
    }

    private static long toTicks(double seconds) {
        long rounded = Math.round(Math.max(0, seconds) * 20);
        return rounded == 0 ? 20 : rounded;
    }

}
