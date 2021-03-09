package net.elytrapvp.ffa.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SettingsManager {
    private static SettingsManager instance = new SettingsManager();
    private FileConfiguration arrowTrails;
    private File arrowTrailsFile;
    private FileConfiguration config;
    private File configFile;
    private FileConfiguration killMessages;
    private File killMessagesFile;
    private FileConfiguration hats;
    private File hatsFile;
    private FileConfiguration tags;
    private File tagsFile;

    /**
     * Get the instance of SettingsManager.
     * @return instance.
     */
    public static SettingsManager getInstance() {
        return instance;
    }

    /**
     * Set up configuration files.
     * @param pl Plugin
     */
    public void setup(Plugin pl) {
        config = pl.getConfig();
        config.options().copyDefaults(true);
        configFile = new File(pl.getDataFolder(), "config.yml");
        pl.saveConfig();

        arrowTrailsFile = new File(pl.getDataFolder(), "arrowtrails.yml");
        arrowTrails = YamlConfiguration.loadConfiguration(arrowTrailsFile);

        hatsFile = new File(pl.getDataFolder(), "hats.yml");
        hats = YamlConfiguration.loadConfiguration(hatsFile);

        killMessagesFile = new File(pl.getDataFolder(), "killmessages.yml");
        killMessages = YamlConfiguration.loadConfiguration(killMessagesFile);

        tagsFile = new File(pl.getDataFolder(), "tags.yml");
        tags = YamlConfiguration.loadConfiguration(tagsFile);

        if(!arrowTrailsFile.exists())
            pl.saveResource("arrowtrails.yml", false);

        if(!hatsFile.exists())
            pl.saveResource("hats.yml", false);

        if(!tagsFile.exists())
            pl.saveResource("tags.yml", false);

        if(!killMessagesFile.exists())
            pl.saveResource("killmessages.yml", false);
    }

    /**
     * Get the arrow trails config.
     * @return Config.
     */
    public FileConfiguration getArrowTrails() {
        return arrowTrails;
    }

    /**
     * Get the config.
     * @return Config.
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Get the hats.
     * @return Hats.
     */
    public FileConfiguration getHats() {
        return hats;
    }

    public FileConfiguration getKillMessages() {
        return killMessages;
    }

    /**
     * Get the tags.
     * @return Tags
     */
    public FileConfiguration getTags() {
        return tags;
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void saveConfig() {
        try {
            config.save(configFile);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This updates the config in case changes are made.
     */
    public void reloadConfig() {
        saveConfig();
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}