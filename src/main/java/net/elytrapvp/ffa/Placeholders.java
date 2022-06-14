package net.elytrapvp.ffa;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.elytrapvp.ffa.managers.SettingsManager;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.cosmetics.Tag;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This class will be registered through the register-method in the
 * plugins onEnable-method.
 */
class Placeholders extends PlaceholderExpansion {

    private FFA plugin;
    SettingsManager settings = SettingsManager.getInstance();

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *        The instance of our plugin.
     */
    public Placeholders(FFA plugin){
        this.plugin = plugin;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public @NotNull String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public @NotNull String getIdentifier(){
        return "ep";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public @NotNull String getVersion(){
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if(player == null) {
            return "";
        }

        CustomPlayer customPlayer = CustomPlayer.getCustomPlayers().get(player.getUniqueId());

        // Coins
        if(identifier.equals("coins")) {
            return String.valueOf(customPlayer.getCoins());
        }

        // Kills
        if(identifier.equals("kills")) {
            return String.valueOf(customPlayer.getKills());
        }

        // Deaths
        if(identifier.equals("deaths")) {
            return String.valueOf(customPlayer.getDeaths());
        }

        // Killstreak
        if(identifier.equals("killstreak")) {
            return String.valueOf(customPlayer.getKillStreak());
        }

        // Kit
        if(identifier.equals("kit")) {

            return switch (customPlayer.getKit()) {
                case -1 -> "Spectator";
                case 1 -> "Sniper";
                case 2 -> "Knight";
                case 3 -> "Pyro";
                case 4 -> "Tank";
                case 5 -> "Chemist";
                case 6 -> "Stickman";
                case 7 -> "Bomber";
                case 8 -> "Healer";
                case 9 -> "Teleporter";
                case 10 -> "Spectral";
                case 11 -> "Puncher";
                default -> "None";
            };

        }

        // Get tag
        if(identifier.equals("tag")) {

            if(customPlayer.getTag() == 0) {
                return "";
            }
            else {
                for(Tag t : Tag.getTags().values()) {
                    if(t.getNumber() == customPlayer.getTag()) {
                        return t.getTag() + " ";
                    }
                }
            }
        }

        // Get raw tag
        if(identifier.equals("tag_raw")) {

            if(customPlayer.getTag() == 0) {
                return "";
            }
            else {
                for(Tag t : Tag.getTags().values()) {
                    if(t.getNumber() == customPlayer.getTag()) {
                        return t.getRawTag() + " ";
                    }
                }
            }
        }
        return null;
    }
}
