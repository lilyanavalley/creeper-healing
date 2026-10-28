package io.github.arkosammy12.creeperhealing.config;

import io.github.arkosammy12.creeperhealing.CreeperHealing;
import io.github.arkosammy12.creeperhealing.explosions.ExplosionHealingMode;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.api.FileType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.event.api.ServerUpdateContext;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedStringMap;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;

public class CreeperHealingConfig extends Config {

    public CreeperHealingConfig() {
        super(Identifier.fromNamespaceAndPath(CreeperHealing.MOD_ID, "config"), "", "", "creeper-healing");
    }

    @Override
    public FileType fileType() {
        return FileType.TOML;
    }

    @Override
    public void onUpdateServer(ServerUpdateContext context) {
        CreeperHealing.EXPLOSION_MANAGER.updateAffectedBlocksTimers();
    }

    @Override
    public void onUpdateClient() {
        CreeperHealing.EXPLOSION_MANAGER.updateAffectedBlocksTimers();
    }

    @Comment("Configure the delays related to the healing of explosions.")
    public Delays delays = new Delays();

    @Comment("Toggle whether certain explosion should drop items. Does not include items stored in container blocks.")
    public ExplosionItemDrops explosion_item_drops = new ExplosionItemDrops();

    @Comment("Configure which explosions are allowed to heal.")
    public ExplosionSources explosion_sources = new ExplosionSources();

    @Comment("Choose between different special modes for explosion healing. Note that certain healing modes will not follow the explosion delay and block delay settings.")
    public ExplosionHealingModeSection explosion_healing_mode = new ExplosionHealingModeSection();

    @Comment("Toggleable settings for extra features.")
    public Preferences preferences = new Preferences();

    @Comment("Use an optional whitelist to customize which blocks are allowed to heal. Entries are block identifiers such as minecraft:stone.")
    public Whitelist whitelist = new Whitelist();

    @Comment("The block on the right will be used to heal the block on the left. Both are block identifiers, for example minecraft:gold_block = minecraft:stone.")
    public ValidatedStringMap<String> replace_map = new ValidatedStringMap<>(
            Map.of("minecraft:diamond_block", "minecraft:stone"),
            new ValidatedString(),
            new ValidatedString()
    );

    public static class Delays extends ConfigSection {

        @Comment("(Default = 3.0) How much time in seconds should an explosion wait for to begin healing.")
        public ValidatedDouble explosion_heal_delay = new ValidatedDouble(3.0, Double.MAX_VALUE, 0.05);

        @Comment("(Default = 1.0) The time in seconds that a block takes to heal.")
        public ValidatedDouble block_placement_delay = new ValidatedDouble(1.0, Double.MAX_VALUE, 0.05);

    }

    public static class ExplosionItemDrops extends ConfigSection {

        @Comment("(Default = false) Whether to drop items on explosions caused by mobs such as Creepers.")
        public ValidatedBoolean drop_items_on_mob_explosions = new ValidatedBoolean(false);

        @Comment("(Default = true) Whether to drop items on explosions caused by blocks such as beds or end crystal blocks.")
        public ValidatedBoolean drop_items_on_block_explosions = new ValidatedBoolean(true);

        @Comment("(Default = true) Whether to drop items on explosions caused by TNT blocks and TNT minecarts.")
        public ValidatedBoolean drop_items_on_tnt_explosions = new ValidatedBoolean(true);

        @Comment("(Default = true) Whether to drop items on explosions such as those caused by wind bursts.")
        public ValidatedBoolean drop_items_on_triggered_explosions = new ValidatedBoolean(true);

        @Comment("(Default = true) Whether to drop items on explosions whose source is not any of the ones provided in this setting category.")
        public ValidatedBoolean drop_items_on_other_explosions = new ValidatedBoolean(true);

        @Comment("Add mob identifiers to this blacklist to prevent explosions caused by the added mobs from dropping items if drop_items_on_mob_explosions is enabled.")
        public ValidatedList<String> drop_items_on_mob_explosions_blacklist = ValidatedList.ofString(List.of("minecraft:placeholder"));

    }

    public static class ExplosionSources extends ConfigSection {

        @Comment("(Default = true) Heal explosions caused by mobs such as Creepers.")
        public ValidatedBoolean heal_mob_explosions = new ValidatedBoolean(true);

        @Comment("(Default = false) Heal explosions caused by blocks such as beds or end crystal blocks.")
        public ValidatedBoolean heal_block_explosions = new ValidatedBoolean(false);

        @Comment("(Default = false) Heal explosions caused by TNT blocks and TNT minecarts.")
        public ValidatedBoolean heal_tnt_explosions = new ValidatedBoolean(false);

        @Comment("(Default = false) Heal explosions such as those caused by wind bursts.")
        public ValidatedBoolean heal_triggered_explosions = new ValidatedBoolean(false);

        @Comment("(Default = false) Heal explosions caused by sources which aren't any of the ones provided in this setting category.")
        public ValidatedBoolean heal_other_explosions = new ValidatedBoolean(false);

        @Comment("Add mob identifiers to this blacklist to prevent explosions caused by the added mobs from healing if heal_mob_explosions is enabled.")
        public ValidatedList<String> heal_mob_explosions_blacklist = ValidatedList.ofString(List.of("minecraft:placeholder"));

    }

    public static class ExplosionHealingModeSection extends ConfigSection {

        @Comment("(Default = DEFAULT_MODE) The healing mode to use.")
        public ValidatedEnum<ExplosionHealingMode> mode = new ValidatedEnum<>(ExplosionHealingMode.DEFAULT_MODE);

    }

    public static class Preferences extends ConfigSection {

        @Comment("(Default = false) Whether to restore block nbt data upon healing. This option prevents container blocks like chests from dropping their inventories. Does not apply when the healed block is different from the destroyed block due to a replace map entry.")
        public ValidatedBoolean restore_block_nbt = new ValidatedBoolean(false);

        @Comment("(Default = false) Whether to force blocks with nbt data to always heal, even if the replace map specifies a replacement for that block, and regardless of the block that may be occupying that position at the moment of healing.")
        public ValidatedBoolean force_blocks_with_nbt_to_always_heal = new ValidatedBoolean(false);

        @Comment("(Default = true) Allows for a falling block, like sand or gravel, to fall when healed. Disabling this option makes the falling block have to receive a neighbor update before falling.")
        public ValidatedBoolean make_falling_blocks_fall = new ValidatedBoolean(true);

        @Comment("(Default = true) Whether a block placement sound effect should be played when a block is healed.")
        public ValidatedBoolean block_placement_sound_effect = new ValidatedBoolean(true);

        @Comment("(Default = true) Whether a block placement sound effect should produce some cloud particles.")
        public ValidatedBoolean block_placement_particles = new ValidatedBoolean(true);

        @Comment("(Default = true) Makes explosion heal immediately when a potion of Healing is thrown on them.")
        public ValidatedBoolean heal_on_healing_potion_splash = new ValidatedBoolean(true);

        @Comment("(Default = true) Makes explosions begin their healing process when a potion of Regeneration is thrown on them.")
        public ValidatedBoolean heal_on_regeneration_potion_splash = new ValidatedBoolean(true);

        @Comment("(Default = false) Toggle the usage of the whitelist.")
        public ValidatedBoolean enable_whitelist = new ValidatedBoolean(false);

    }

    public static class Whitelist extends ConfigSection {

        public ValidatedList<String> whitelist = ValidatedList.ofString(List.of("minecraft:placeholder"));

    }

}
