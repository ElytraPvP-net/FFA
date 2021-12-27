package net.elytrapvp.ffa.inventories;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.elytrapvp.items.SkullBuilder;
import net.elytrapvp.ffa.enums.HatType;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Hat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class HatsGUI extends CustomGUI {
    public HatsGUI() {
        super(54, "Hats");
        filler(Arrays.asList(1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53));

        setItem(0, new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6").setDisplayName("&cBack").build(), (p,a) -> new CosmeticsGUI().open(p));

        ItemStack animals = new SkullBuilder("5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5").setDisplayName("&aAnimals").build();
        setItem(19, animals, (p,a) -> new HatsGUI(p, 1, HatType.ANIMAL).open(p));

        ItemStack blocks = new SkullBuilder("219e36a87baf0ac76314352f59a7f63bdb3f4c86bd9bba6927772c01d4d1").setDisplayName("&aBlocks").build();
        setItem(21, blocks, (p,a) -> new HatsGUI(p, 1, HatType.BLOCK).open(p));

        ItemStack food = new SkullBuilder("36c01bffecfdab6d3c0f1a7c6df6aa1936f2aa7a51b54a4d323e1cacbc539").setDisplayName("&aFood").build();
        setItem(23, food, (p,a) -> new HatsGUI(p, 1, HatType.FOOD).open(p));

        ItemStack christmas = new SkullBuilder("14e424b1676feec3a3f8ebade9e7d6a6f71f7756a869f36f7df0fc182d436e").setDisplayName("&aChristmas").build();
        setItem(25, christmas, (p,a) -> new HatsGUI(p, 1, HatType.CHRISTMAS).open(p));

        ItemStack unknown = new SkullBuilder("46ba63344f49dd1c4f5488e926bf3d9e2b29916a6c50d610bb40a5273dc8c82").setDisplayName("&cComing Soon").build();
        setItem(29, unknown);
        setItem(31, unknown);
        setItem(33, unknown);
    }

    public HatsGUI(Player player, int page, HatType type) {
        super(54, "Hats - Page " + page);

        int[] slots = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34};

        int s = 0;
        for(int i = ((page - 1) * 21) + 1; i <= type.getHats().size(); i++) {
            if(i > (page * slots.length) || s > slots.length - 1) {
                break;
            }

            Hat h = type.getHats().get(i - 1);
            setItem(slots[s], h.getIcon(player), (p, a) -> h.unlock(player));
            s++;
        }

        filler(Arrays.asList(1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53));

        if(page == 1) {
            setItem(38, new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6").setDisplayName("&cNo more pages!").build());
        }
        else {
            setItem(38, new SkullBuilder("754aaab39764641ff8627932d2fe1a4ccced96537508d4abc6cd5fbbb87913").setDisplayName("&aPage " + (page - 1)).build(), (p,a) -> new HatsGUI(player, page - 1, type).open(player));
        }

        if(type.getHats().size() > (page * 21)) {
            setItem(42, new SkullBuilder("61d0f82a2a4cdd85f79f4d9d9798f9c3a5bccbe9c7f2e27c5fc836651a8f3f45").setDisplayName("&aPage " + (page + 1)).build(), (p,a) -> new HatsGUI(player, page + 1, type).open(player));
        }
        else {
            setItem(42, new SkullBuilder("abae89e92ac362635ba3e9fb7c12b7ddd9b38adb11df8aa1aff3e51ac428a4").setDisplayName("&cNo more pages!").build());
        }

        setItem(0, new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6").setDisplayName("&cBack").build(), (p,a) -> new HatsGUI().open(player));

        setItem(40, new ItemBuilder(Material.BARRIER).setDisplayName("&cReset").build(), (p,a ) -> {
            CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
            ep.setHat(0);
            p.getInventory().clear(39);
            p.closeInventory();
            ChatUtils.chat(p, "&2&lCosmetics - &aHat has been reset.");
        });
    }

    private void filler(List<Integer> slots) {
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}
