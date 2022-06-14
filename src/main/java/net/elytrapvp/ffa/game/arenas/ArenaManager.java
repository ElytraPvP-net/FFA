package net.elytrapvp.ffa.game.arenas;

import net.elytrapvp.ffa.FFA;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    private Arena selectedArena;
    private final List<Arena> arenas = new ArrayList<>();

    public ArenaManager(FFA plugin) {
        FileConfiguration arenasConfig = plugin.getSettingsManager().getArenas();
        ConfigurationSection section = arenasConfig.getConfigurationSection("Arenas");

        for(String arena : section.getKeys(false)) {
            arenas.add(new Arena(plugin, arena));
        }

        selectedArena = getArena(plugin.getSettingsManager().getConfig().getString("SelectedArena"));
    }

    /**
     * Get an arena by its name.
     * @param name Name of the arena.
     * @return Arena object.
     */
    public Arena getArena(String name) {
        for(Arena arena : arenas) {
            if(arena.getName().equalsIgnoreCase(name)) {
                return arena;
            }
        }

        return null;
    }

    /**
     * Get all registered arenas.
     * @return All arenas.
     */
    public List<Arena> getArenas() {
        return arenas;
    }

    /**
     * Get the currently selected arena.
     * @return Selected Arena.
     */
    public Arena getSelectedArena() {
        return selectedArena;
    }

    /**
     * Changes the currently selected arena.
     * @param selectedArena new selected arena.
     */
    public void setSelectedArena(Arena selectedArena) {
        this.selectedArena = selectedArena;
    }
}