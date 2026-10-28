package io.github.arkosammy12.creeperhealing.config;

import io.github.arkosammy12.creeperhealing.explosions.ExplosionHealingMode;

import java.util.List;
import java.util.Map;

/**
 * Temporary stand-in for the monkey-config backed configuration, which has no 26.x release yet.
 * Every setting returns its default value.
 */
public final class ConfigUtils {

    public static final String EXPLOSION_HEAL_DELAY = "explosion_heal_delay";
    public static final String BLOCK_PLACEMENT_DELAY = "block_placement_delay";

    public static final String DROP_ITEMS_ON_MOB_EXPLOSIONS = "drop_items_on_mob_explosions";
    public static final String DROP_ITEMS_ON_BLOCK_EXPLOSIONS = "drop_items_on_block_explosions";
    public static final String DROP_ITEMS_ON_TNT_EXPLOSIONS = "drop_items_on_tnt_explosions";
    public static final String DROP_ITEMS_ON_TRIGGERED_EXPLOSIONS = "drop_items_on_triggered_explosions";
    public static final String DROP_ITEMS_ON_OTHER_EXPLOSIONS = "drop_items_on_other_explosions";
    public static final String DROP_ITEMS_ON_MOB_EXPLOSIONS_BLACKLIST = "drop_items_on_mob_explosions_blacklist";

    public static final String HEAL_MOB_EXPLOSIONS = "heal_mob_explosions";
    public static final String HEAL_BLOCK_EXPLOSIONS = "heal_block_explosions";
    public static final String HEAL_TNT_EXPLOSIONS = "heal_tnt_explosions";
    public static final String HEAL_TRIGGERED_EXPLOSIONS = "heal_triggered_explosions";
    public static final String HEAL_OTHER_EXPLOSIONS = "heal_other_explosions";
    public static final String HEAL_MOB_EXPLOSIONS_BLACKLIST = "heal_mob_explosions_blacklist";

    public static final String MODE = "mode";

    public static final String RESTORE_BLOCK_NBT = "restore_block_nbt";
    public static final String FORCE_BLOCKS_WITH_NBT_TO_ALWAYS_HEAL = "force_blocks_with_nbt_to_always_heal";
    public static final String MAKE_FALLING_BLOCKS_FALL = "make_falling_blocks_fall";
    public static final String BLOCK_PLACEMENT_SOUND_EFFECT = "block_placement_sound_effect";
    public static final String BLOCK_PLACEMENT_PARTICLES = "block_placement_particles";
    public static final String HEAL_ON_HEALING_POTION_SPLASH = "heal_on_healing_potion_splash";
    public static final String HEAL_ON_REGENERATION_POTION_SPLASH = "heal_on_regeneration_potion_splash";
    public static final String ENABLE_WHITELIST = "enable_whitelist";

    public static final String WHITELIST = "whitelist";

    public static final double DEFAULT_EXPLOSION_HEAL_DELAY = 3;
    public static final double DEFAULT_BLOCK_PLACEMENT_DELAY = 1;

    private static final Map<String, Boolean> BOOLEAN_DEFAULTS = Map.ofEntries(
            Map.entry(DROP_ITEMS_ON_MOB_EXPLOSIONS, false),
            Map.entry(DROP_ITEMS_ON_BLOCK_EXPLOSIONS, true),
            Map.entry(DROP_ITEMS_ON_TNT_EXPLOSIONS, true),
            Map.entry(DROP_ITEMS_ON_TRIGGERED_EXPLOSIONS, true),
            Map.entry(DROP_ITEMS_ON_OTHER_EXPLOSIONS, true),
            Map.entry(HEAL_MOB_EXPLOSIONS, true),
            Map.entry(HEAL_BLOCK_EXPLOSIONS, false),
            Map.entry(HEAL_TNT_EXPLOSIONS, false),
            Map.entry(HEAL_TRIGGERED_EXPLOSIONS, false),
            Map.entry(HEAL_OTHER_EXPLOSIONS, false),
            Map.entry(RESTORE_BLOCK_NBT, false),
            Map.entry(FORCE_BLOCKS_WITH_NBT_TO_ALWAYS_HEAL, false),
            Map.entry(MAKE_FALLING_BLOCKS_FALL, true),
            Map.entry(BLOCK_PLACEMENT_SOUND_EFFECT, true),
            Map.entry(BLOCK_PLACEMENT_PARTICLES, true),
            Map.entry(HEAL_ON_HEALING_POTION_SPLASH, true),
            Map.entry(HEAL_ON_REGENERATION_POTION_SPLASH, true),
            Map.entry(ENABLE_WHITELIST, false)
    );

    private static final Map<String, String> REPLACE_MAP = Map.of("minecraft:diamond_block", "minecraft:stone");

    private ConfigUtils() {
    }

    public static boolean getRawBooleanSetting(String key) {
        Boolean value = BOOLEAN_DEFAULTS.get(key);
        if (value == null) {
            throw new IllegalArgumentException("No boolean setting found with key: " + key);
        }
        return value;
    }

    public static List<? extends String> getRawStringListSetting(String key) {
        return List.of("minecraft:placeholder");
    }

    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getRawEnumSetting(String key) {
        if (!MODE.equals(key)) {
            throw new IllegalArgumentException("No enum setting found with key: " + key);
        }
        return (E) ExplosionHealingMode.DEFAULT_MODE;
    }

    public static String getReplaceMapEntry(String blockIdentifier) {
        return REPLACE_MAP.get(blockIdentifier);
    }

    public static long getExplosionHealDelay() {
        long rounded = Math.round(Math.max(0, DEFAULT_EXPLOSION_HEAL_DELAY) * 20);
        return rounded == 0 ? 20 : rounded;
    }

    public static long getBlockPlacementDelay() {
        long rounded = Math.round(Math.max(0, DEFAULT_BLOCK_PLACEMENT_DELAY) * 20);
        return rounded == 0 ? 20 : rounded;
    }

}
