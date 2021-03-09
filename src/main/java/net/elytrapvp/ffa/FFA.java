package net.elytrapvp.ffa;

import net.elytrapvp.ffa.commands.*;
import net.elytrapvp.ffa.enums.Event;
import net.elytrapvp.ffa.enums.HatType;
import net.elytrapvp.ffa.enums.TagType;
import net.elytrapvp.ffa.listeners.*;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.objects.*;
import net.elytrapvp.ffa.objects.kits.*;
import net.elytrapvp.ffa.runnables.ArrowTrailSpawn;
import net.elytrapvp.ffa.runnables.LeaderboardUpdate;
import net.elytrapvp.ffa.runnables.MySQLHeartBeat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class FFA extends JavaPlugin {
    private static final SettingsManager settings = SettingsManager.getInstance();
    private static FFA plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Load configuration files.
        settings.setup(this);

        // If PlaceholderAPI is installed, enables placeholders
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }

        // Connect to MySQL
        MySQL.openConnection();

        // Load plugin essentials.
        registerCommands();
        registerKits();
        registerListeners();
        registerRunnables();

        // Load Cosmetics
        registerArrowTrails();
        registerHats();
        registerKillMessages();
        registerTags();

        // Set Event
        Event.setCurrentEvent(Event.valueOf(settings.getConfig().getString("Event")));
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public static FFA getPlugin() {
        return plugin;
    }

    private void registerAchievements() {
        new Achievement("Novice", "Reach 100 kills", 1, 0, 0);
        new Achievement("Student", "Reach 500 kills", 2, 0, 0);
        new Achievement("Master", "Reach 1000 kills", 3, 0, 0);
        new Achievement("God", "Reach 2500 kills", 4, 0, 0);
        new Achievement("Obsessed", "Reach 5000 kills", 5, 0, 0);
        new Achievement("Dominator", "Reach a kill streak of 15", 6, 0, 0);
        new Achievement("The Assassin", "Reach a kill streak of 30", 7, 0, 0);

        new Achievement("Ca-Ching", "Earn a total of 2,500 coins", 8, 0, 0);
        new Achievement("Ooh, Money", "Earn a total of 5,000 coins", 9, 0, 0);
        new Achievement("Money Maker", "Earn a total of 10,000 coins", 10, 0, 0);
        new Achievement("Banker", "Have 2,000 coins at one time", 11, 0, 0);

        new Achievement("Parkour Beginner", "Beat the Green Parkour", 12, 0, 0);
        new Achievement("Parkour Protege", "Beat the Yellow Parkour", 13, 0, 0);
        new Achievement("Parkour Master", "Beat the Red Parkour", 14, 0, 0);

        new Achievement("Looking Sharp", "Buy your first hat", 20, 0, 0);
        new Achievement("Fashionista", "Buy every hat.", 15, 0, 0);
        new Achievement("Who Am I?", "Buy a tag", 16, 0, 0);
        new Achievement("Trail Mix", "Buy every arrow trail", 17, 0, 0);

        new Achievement("Anger Issues", "Destroy 1000 windows", 18, 0, 0);
        new Achievement("Sharp Shooter", "Hit players with an arrow 2,500 times", 19, 0, 0);
    }

    /**
     * Register the arrow trail cosmetics.
     */
    private void registerArrowTrails() {
        ConfigurationSection section = settings.getArrowTrails().getConfigurationSection("ArrowTrails");

        if(section == null) {
            return;
        }

        section.getKeys(false).forEach(str -> new ArrowTrail(Integer.parseInt(str)));
    }

    private void registerCommands() {
        getCommand("admin").setExecutor(new AdminCMD());
        getCommand("cosmetics").setExecutor(new CosmeticsCMD());
        getCommand("kits").setExecutor(new KitsCMD());
        getCommand("leaderboards").setExecutor(new LeaderboardCMD());
        getCommand("spawn").setExecutor(new SpawnCMD());
        getCommand("stats").setExecutor(new StatsCMD());
        getCommand("bounty").setExecutor(new BountyCMD());
        getCommand("eventshop").setExecutor(new EventShopCMD());
    }

    /**
     * Register the hat cosmetics.
     */
    private void registerHats() {
        ConfigurationSection section = settings.getHats().getConfigurationSection("Hats");

        if(section == null) {
            return;
        }

        for(String str : section.getKeys(false)) {
            int id = Integer.parseInt(str);
            String name = settings.getHats().getString("Hats." + str + ".Name");
            int price = settings.getHats().getInt("Hats." + str + ".Price");
            String skin = settings.getHats().getString("Hats." + str + ".Skin");
            HatType type = HatType.valueOf(settings.getHats().getString("Hats." + str + ".Type"));
            new Hat(name, price, id, skin, type);
        }
    }

    private void registerKillMessages() {
        ConfigurationSection section = settings.getKillMessages().getConfigurationSection("KillMessages");

        if(section == null) {
            return;
        }

        for(String str : section.getKeys(false)) {
            int id = Integer.parseInt(str);
            int price = settings.getKillMessages().getInt("KillMessages." + str + ".Price");
            String message = settings.getKillMessages().getString("KillMessages." + str + ".Message");

            new KillMessage(message, id, price);
        }
    }

    private void registerKits() {
        new SniperKit();
        new KnightKit();
        new PyroKit();
        new TankKit();
        new ChemistKit();
        new StickmanKit();
        new BomberKit();
        new HealerKit();
        new TeleporterKit();
        new SpectralKit();
        new PuncherKit();
        new PoseidonKit();
        new CactusKit();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        getServer().getPluginManager().registerEvents(new EntityPickupItem(), this);
        getServer().getPluginManager().registerEvents(new EntityShootBow(), this);
        getServer().getPluginManager().registerEvents(new ExpGain(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new ParkourComplete(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        getServer().getPluginManager().registerEvents(new PlayerDrown(), this);
        getServer().getPluginManager().registerEvents(new PlayerEscape(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLand(), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
        getServer().getPluginManager().registerEvents(new PlayerEscape(), this);
    }

    private void registerRunnables() {
        new MySQLHeartBeat().runTaskTimerAsynchronously(this, 0, 20*60*7);
        new LeaderboardUpdate().runTaskTimerAsynchronously(this, 0, 20*60*20);
        new ArrowTrailSpawn().runTaskTimerAsynchronously(this, 0, 1);
    }

    /**
     * Register the tags cosmetics.
     */
    private void registerTags() {
        ConfigurationSection section = settings.getTags().getConfigurationSection("Tags");

        if(section == null) {
            return;
        }

        for(String str : section.getKeys(false)) {
            int id = Integer.parseInt(str);
            String tag = settings.getTags().getString("Tags." + str + ".Tag");
            int price = settings.getTags().getInt("Tags." + str + ".Price");
            TagType type = TagType.valueOf(settings.getTags().getString("Tags." + str + ".Type"));

            new Tag(tag, id, price, type);
        }
    }
}