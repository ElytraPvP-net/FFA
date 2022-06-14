package net.elytrapvp.ffa.inventories;

import net.elytrapvp.ffa.events.KitUnlockEvent;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.game.kits.Kit;
import net.elytrapvp.ffa.utilities.chat.ChatUtils;
import net.elytrapvp.ffa.utilities.gui.CustomGUI;
import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class KitsGUI extends CustomGUI {
    private static final int[] slots = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34};

    public KitsGUI(Player p) {
        super(54, "Kits");
        fillers();

        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());

        for(int i = 0; i < Kit.getKits().size(); i++) {
            Kit kit = Kit.getKits().get(i);

            setItem(slots[i], kit.getIcon(p), (player, action) -> {
                if(action == ClickType.RIGHT) {
                    new PreviewKitGUI(kit).open(player);
                }
                else {
                    if(cp.getUnlockedKits().contains(kit.getId())) {
                        player.closeInventory();
                        cp.setKit(kit.getId());
                        ChatUtils.chat(player, "&a&lKit &8» &aYou have selected Kit &f" + kit.getName() + "&a.");
                    }
                    else {
                        if(cp.getCoins() >= kit.getPrice()) {
                            cp.removeCoins(kit.getPrice());
                            ChatUtils.chat(player, "&a&lKit &8» &aYou have purchased and equipped Kit &f" + kit.getName() + "&a.");
                            cp.setKit(kit.getId());
                            cp.unlockKit(kit.getId());
                            p.closeInventory();
                            Bukkit.getPluginManager().callEvent(new KitUnlockEvent(cp, kit));
                        }
                        else {
                            ChatUtils.chat(player, "&c&lError &8» &cYou do not have enough coins.");
                            player.closeInventory();
                        }
                    }
                }
            });
        }

        setItem(40, new ItemBuilder(Material.COMPASS).setDisplayName("&aSpectator Mode").build(), (player, action) -> {
            cp.setKit(-1);
            ChatUtils.chat(p, "&a&lSpectator &8» &aEntered Spectator Mode");
        });
    }

    private void fillers() {
        List<Integer> slots = Arrays.asList(0,1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}