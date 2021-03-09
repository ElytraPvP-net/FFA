package net.elytrapvp.ffa.scoreboards;

import me.clip.placeholderapi.PlaceholderAPI;
import net.elytrapvp.elytrapvp.scoreboard.CustomScoreboard;
import net.elytrapvp.elytrapvp.scoreboard.ScoreHelper;
import org.bukkit.entity.Player;

public class FFAScoreboard extends CustomScoreboard {
    public FFAScoreboard(Player p) {
        CustomScoreboard.getPlayers().put(p.getUniqueId(), this);
        update(p);
    }

    public void update(Player p) {
        ScoreHelper helper;

        if(ScoreHelper.hasScore(p)) {
            helper = ScoreHelper.getByPlayer(p);
        }
        else {
            helper = ScoreHelper.createScore(p);
        }

        helper.setTitle("&2&lElytraPvP");
        helper.setSlot(13, "&aCoins");
        helper.setSlot(12, PlaceholderAPI.setPlaceholders(p, "%ep_coins%"));
        helper.setSlot(11, "");
        helper.setSlot(10, "&aKills");
        helper.setSlot(9, PlaceholderAPI.setPlaceholders(p, "%ep_kills%"));
        helper.setSlot(8, "");
        helper.setSlot(7, "&aDeaths");
        helper.setSlot(6, PlaceholderAPI.setPlaceholders(p, "%ep_deaths%"));
        helper.setSlot(5, "");
        helper.setSlot(4, "&aKill Streak");
        helper.setSlot(3, PlaceholderAPI.setPlaceholders(p, "%ep_killstreak%"));
        helper.setSlot(2, "");
        helper.setSlot(1, "&2play.elytrapvp.net");
    }
}