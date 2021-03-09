package net.elytrapvp.ffa.objects;

import net.elytrapvp.ffa.FFA;
import net.elytrapvp.ffa.MySQL;
import net.elytrapvp.ffa.enums.Status;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CustomPlayer {
    private int arrowsHit = 0;
    private int arrowsShot = 0;
    private int arrowTrail = 0;
    private int bounty = 0;
    private int coins = 0;
    private double damageDealt = 0;
    private double damageTaken = 0;
    private int deaths = 0;
    private int drops = 0;
    private boolean firstTime = false;
    private int hat = 0;
    private int highestKillStreak = 0;
    private int killMessage = 0;
    private int kills = 0;
    private int killStreak = 0;
    private int kit = 0;
    private int lifetimeCoins = 0;
    private int tag = 0;
    private List<Integer> unlockedArrowTrails = new ArrayList<>();
    private List<Integer> unlockedHats = new ArrayList<>();
    private List<Integer> unlockedKillMessages = new ArrayList<>();
    private List<Integer> unlockedKits = new ArrayList<>();
    private List<Integer> unlockedTags = new ArrayList<>();
    private UUID uuid;
    private int windowsBroken = 0;

    private static final HashMap<UUID, CustomPlayer> players = new HashMap<>();

    public CustomPlayer(UUID uuid) {
        this.uuid = uuid;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement s1 = MySQL.getConnection().prepareStatement("SELECT * from ep_players WHERE uuid = ?");
                s1.setString(1, uuid.toString());
                ResultSet r1 = s1.executeQuery();

                if(r1.next()) {
                    coins = r1.getInt(2);
                    bounty = r1.getInt(3);
                    kit = r1.getInt(4);

                    String[] kits = r1.getString(5).split(",");
                    Arrays.asList(kits).forEach(str -> unlockedKits.add(Integer.parseInt(str)));
                }
                else {
                    firstTime = true;
                    PreparedStatement statement = MySQL.getConnection().prepareStatement("INSERT INTO ep_players (uuid) VALUES (?)");
                    statement.setString(1, uuid.toString());
                    statement.executeUpdate();
                }

                PreparedStatement s2 = MySQL.getConnection().prepareStatement("SELECT * from ep_cosmetics WHERE uuid = ?");
                s2.setString(1, uuid.toString());
                ResultSet r2 = s2.executeQuery();

                if(r2.next()) {
                    arrowTrail = r2.getInt(2);
                    hat = r2.getInt(3);
                    killMessage = r2.getInt(4);
                    tag = r2.getInt(5);

                    String[] arrowTrails = r2.getString(6).split(",");
                    Arrays.asList(arrowTrails).forEach(str -> unlockedArrowTrails.add(Integer.parseInt(str)));

                    String[] hats = r2.getString(7).split(",");
                    Arrays.asList(hats).forEach(str -> unlockedHats.add(Integer.parseInt(str)));

                    String[] killMessages = r2.getString(8).split(",");
                    Arrays.asList(killMessages).forEach(str -> unlockedKillMessages.add(Integer.parseInt(str)));

                    String[] tags = r2.getString(9).split(",");
                    Arrays.asList(tags).forEach(str -> unlockedTags.add(Integer.parseInt(str)));
                }
                else {
                    PreparedStatement statement2 = MySQL.getConnection().prepareStatement("INSERT INTO ep_cosmetics (uuid) VALUES (?)");
                    statement2.setString(1, uuid.toString());
                    statement2.executeUpdate();
                }

                PreparedStatement s3 = MySQL.getConnection().prepareStatement("SELECT * from ep_statistics WHERE uuid = ?");
                s3.setString(1, uuid.toString());
                ResultSet r3 = s3.executeQuery();

                if(r3.next()) {
                    kills = r3.getInt(2);
                    deaths = r3.getInt(3);
                    killStreak = r3.getInt(4);
                    highestKillStreak = r3.getInt(5);
                    damageDealt = r3.getDouble(6);
                    damageTaken = r3.getDouble(7);
                    arrowsShot = r3.getInt(8);
                    arrowsHit = r3.getInt(9);
                    lifetimeCoins = r3.getInt(10);
                    drops = r3.getInt(11);
                    windowsBroken = r3.getInt(12);
                }
                else {
                    PreparedStatement statement3 = MySQL.getConnection().prepareStatement("INSERT INTO ep_statistics (uuid) VALUES (?)");
                    statement3.setString(1, uuid.toString());
                    statement3.executeUpdate();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean exists(UUID uuid) {
        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("SELECT * from ep_players WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();

            return results.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static HashMap<UUID, CustomPlayer> getCustomPlayers() {
        return players;
    }

    /**
     * Add coins to the player's bounty.
     * @param bounty Coins to be added.
     */
    public void addBounty(int bounty) {
        setBounty(getBounty() + bounty);
    }

    /**
     * Add coins to the player.
     * @param coins Coins to be added.
     */
    public void addCoins(int coins) {
        setCoins(getCoins() + coins);
    }

    /**
     * Add deaths to the player.
     * @param deaths Deaths to be added.
     */
    public void addDeaths(int deaths) {
        setDeaths(getDeaths() + deaths);
    }

    /**
     * Add kills to the player.
     * @param kills Kills to be added.
     */
    public void addKills(int kills) {
        setKills(getKills() + kills);
    }

    /**
     * Add kills to a player's kill streak.
     * @param killStreak Kills to be added.
     */
    public void addKillStreak(int killStreak) {
        setKillStreak(getKillStreak() + killStreak);
    }

    /**
     * Get the number of arrows hit.
     * @return Arrows Hit
     */
    public int getArrowsHit() {
        return arrowsHit;
    }

    /**
     * Get the number of arrows shot.
     * @return Arrows shot.
     */
    public int getArrowsShot() {
        return arrowsShot;
    }

    /**
     * Get the player's arrow trail.
     * @return Arrow trail.
     */
    public int getArrowTrail() {
        return arrowTrail;
    }

    /**
     * Get the player's current bounty.
     * @return Bounty
     */
    public int getBounty() {
        return bounty;
    }

    /**
     * Get the damage the player has dealt.
     * @return Damage Dealt
     */
    public double getDamageDealt() {
        return damageDealt;
    }

    /**
     * Get the damage the player has taken.
     * @return Damage Taken.
     */
    public double getDamageTaken() {
        return damageTaken;
    }

    /**
     * Get the player's coins.
     * @return Coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Get the player's deaths.
     * @return Deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Get the number of drops.
     * @return Drops
     */
    public int getDrops() {
        return drops;
    }

    public boolean getFirstTime() {
        return firstTime;
    }

    /**
     * Get the player's hat.
     * @return Hat
     */
    public int getHat() {
        return hat;
    }

    /**
     * Get the player's current kill streak.
     * @return Kill Streak
     */
    public int getHighestKillStreak() {
        return highestKillStreak;
    }

    /**
     * Get the player's current kill message.
     * @return Kill Message
     */
    public int getKillMessage() {
        return killMessage;
    }

    /**
     * Get the player's number of kills.
     * @return Kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * Get the player's current kill streak.
     * @return
     */
    public int getKillStreak() {
        return killStreak;
    }

    /**
     * Get the player's current kit.
     * @return Kit
     */
    public int getKit() {
        return kit;
    }

    /**
     * Get the player's lifetime coins.
     * @return Coins
     */
    public int getLifetimeCoins() {
        return lifetimeCoins;
    }

    public Status getStatus() {
        return Status.getPlayers().get(uuid);
    }

    /**
     * Get the player's current tag
     * @return Tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * Get number of windows broken.
     * @return Windows broken
     */
    public int getWindowsBroken() {
        return windowsBroken;
    }

    public List<Integer> getUnlockedArrowTrails() {
        return unlockedArrowTrails;
    }

    public List<Integer> getUnlockedHats() {
        return unlockedHats;
    }

    public List<Integer> getUnlockedKillMessages() {
        return unlockedKillMessages;
    }

    public List<Integer> getUnlockedKits() {
        return unlockedKits;
    }

    public List<Integer> getUnlockedTags() {
        return unlockedTags;
    }

    /**
     * Remove coins from the player.
     * @param coins Coins to be removed.
     */
    public void removeCoins(int coins) {
        setCoins(getCoins() - coins);
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param arrowsHit Arrows Hit
     */
    public void setArrowsHit(int arrowsHit) {
        this.arrowsHit = arrowsHit;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET arrowsHit = ? WHERE uuid = ?");
                statement.setInt(1, arrowsHit);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows the player has shot.
     * @param arrowsShot Arrows Hit
     */
    public void setArrowsShot(int arrowsShot) {
        this.arrowsShot = arrowsShot;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET arrowsShot = ? WHERE uuid = ?");
                statement.setInt(1, arrowsShot);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set the player's arrow trail.
     * @param arrowTrail Arrows Hit
     */
    public void setArrowTrail(int arrowTrail) {
        this.arrowTrail = arrowTrail;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET arrowTrail = ? WHERE uuid = ?");
                statement.setInt(1, arrowTrail);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();

                this.arrowTrail = arrowTrail;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set the player's current bounty.
     * @param bounty Arrows Hit
     */
    public void setBounty(int bounty) {
        this.bounty = bounty;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_players SET bounty = ? WHERE uuid = ?");
                statement.setInt(1, bounty);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set the total damage dealt by a player.
     * @param damageDealt Arrows Hit
     */
    public void setDamageDealt(double damageDealt) {
        this.damageDealt = damageDealt;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET damageDealt = ? WHERE uuid = ?");
                statement.setDouble(1, damageDealt);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param damageTaken Arrows Hit
     */
    public void setDamageTaken(double damageTaken) {
        this.damageTaken = damageTaken;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET damageTaken = ? WHERE uuid = ?");
                statement.setDouble(1, damageTaken);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many coins the player has.
     * @param coins Arrows Hit
     */
    public void setCoins(int coins) {
        this.coins = coins;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_players SET coins = ? WHERE uuid = ?");
                statement.setInt(1, coins);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many deaths a player has.
     * @param deaths Arrows Hit
     */
    public void setDeaths(int deaths) {
        this.deaths = deaths;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET deaths = ? WHERE uuid = ?");
                statement.setInt(1, deaths);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many drops a player has.
     * @param drops Arrows Hit
     */
    public void setDrops(int drops) {
        this.drops = drops;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET drops = ? WHERE uuid = ?");
                statement.setInt(1, drops);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param hat Arrows Hit
     */
    public void setHat(int hat) {
        this.hat = hat;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET hat = ? WHERE uuid = ?");
                statement.setInt(1, hat);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param highestKillStreak Arrows Hit
     */
    public void setHighestKillStreak(int highestKillStreak) {
        this.highestKillStreak = highestKillStreak;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET highestKillStreak = ? WHERE uuid = ?");
                statement.setInt(1, highestKillStreak);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();

                this.highestKillStreak = highestKillStreak;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param killMessage Arrows Hit
     */
    public void setKillMessage(int killMessage) {
        this.killMessage = killMessage;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET killMessage = ? WHERE uuid = ?");
                statement.setInt(1, killMessage);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param kills Arrows Hit
     */
    public void setKills(int kills) {
        this.kills = kills;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET kills = ? WHERE uuid = ?");
                statement.setInt(1, kills);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param killStreak Arrows Hit
     */
    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET killStreak = ? WHERE uuid = ?");
                statement.setInt(1, killStreak);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param kit Arrows Hit
     */
    public void setKit(int kit)
    {
        this.kit = kit;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_players SET kit = ? WHERE uuid = ?");
                statement.setInt(1, kit);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param lifetimeCoins Arrows Hit
     */
    public void setLifetimeCoins(int lifetimeCoins) {
        this.lifetimeCoins = coins;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET lifetimeCoins = ? WHERE uuid = ?");
                statement.setInt(1, lifetimeCoins);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStatus(Status status) {
        Status.getPlayers().put(uuid, status);
    }

    /**
     * Set how many arrows have hit an opponent.
     * @param tag Arrows Hit
     */
    public void setTag(int tag) {
        this.tag = tag;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET tag = ? WHERE uuid = ?");
                statement.setInt(1, tag);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Set number of windows broken.
     * @param windowsBroken Arrows Hit
     */
    public void setWindowsBroken(int windowsBroken) {
        this.windowsBroken = windowsBroken;

        Bukkit.getScheduler().runTaskAsynchronously(FFA.getPlugin(), () -> {
            try {
                PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_statistics SET windowsBroken = ? WHERE uuid = ?");
                statement.setInt(1, windowsBroken);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void unlockArrowTrail(int arrowTrail) {
        unlockedArrowTrails.add(arrowTrail);

        StringBuilder builder = new StringBuilder();
        unlockedArrowTrails.forEach(i -> {
            builder.append(i.toString());
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET arrowTrails=? WHERE uuid = ?");
            statement.setString(1, builder.toString());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlockHat(int hat) {
        unlockedHats.add(hat);

        StringBuilder builder = new StringBuilder();
        unlockedHats.forEach(i -> {
            builder.append(i.toString());
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET hats=? WHERE uuid = ?");
            statement.setString(1, builder.toString());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlockKillMessage(int killMessage) {
        unlockedKillMessages.add(killMessage);

        StringBuilder builder = new StringBuilder();
        unlockedKillMessages.forEach(i -> {
            builder.append(i.toString());
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET killMessages=? WHERE uuid = ?");
            statement.setString(1, builder.toString());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlockKit(int kit) {
        unlockedKits.add(kit);

        StringBuilder builder = new StringBuilder();
        unlockedKits.forEach(i -> {
            builder.append(i.toString());
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_players SET kits=? WHERE uuid = ?");
            statement.setString(1, builder.toString());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void unlockTag(int tag) {
        unlockedTags.add(tag);

        StringBuilder builder = new StringBuilder();
        unlockedTags.forEach(i -> {
            builder.append(i.toString());
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);

        try {
            PreparedStatement statement = MySQL.getConnection().prepareStatement("UPDATE ep_cosmetics SET tags=? WHERE uuid = ?");
            statement.setString(1, builder.toString());
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}