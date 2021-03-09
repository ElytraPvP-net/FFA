package net.elytrapvp.ffa.utils;

import net.elytrapvp.ffa.managers.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationUtils {
    static SettingsManager settings = SettingsManager.getInstance();

    /**
     * Check if a player is in the arena.
     * @param p Player to check
     * @return Whether or not they are in the arena.
     */
    public static boolean inArena(Player p) {
        int x1 = settings.getConfig().getInt("Arena.Pos1.x");
        int y1 = settings.getConfig().getInt("Arena.Pos1.y");;
        int z1 = settings.getConfig().getInt("Arena.Pos1.z");;
        int x2 = settings.getConfig().getInt("Arena.Pos2.x");;
        int y2 = settings.getConfig().getInt("Arena.Pos2.y");;
        int z2 = settings.getConfig().getInt("Arena.Pos2.z");;

        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);
        int maxZ = Math.max(z1, z2);
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);

        double px = p.getLocation().getX();
        double py = p.getLocation().getY();
        double pz = p.getLocation().getZ();


        if((px > x1 && px < x2) && (py > y1 && py < y2) && (pz > z1 && pz < z2)) {
            return true;
        }

        return px >= minX && px <= maxX &&
                py >= minY && py <= maxY &&
                pz >= minZ && pz <= maxZ;
    }

    /**
     * Get the spawn Location from the Config
     * @return Spawn Location
     */
    public static Location getSpawn() {
        double x = settings.getConfig().getDouble("Spawn.X");
        double y = settings.getConfig().getDouble("Spawn.Y");
        double z = settings.getConfig().getDouble("Spawn.Z");
        float pitch = (float) settings.getConfig().getDouble("Spawn.Pitch");
        float yaw = (float) settings.getConfig().getDouble("Spawn.Yaw");

        Location spawn = new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch);

        return spawn;
    }
}
