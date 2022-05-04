package net.elytrapvp.ffa.objects;

import net.elytrapvp.ffa.utilities.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public abstract class Kit {
    private static List<Kit> kits = new ArrayList<>();
    private String name;
    private int price;
    private int health;
    private int id;
    private ItemStack arrow;
    private ItemStack elytra;
    private Map<Integer, ItemStack> items = new HashMap<>();
    private Collection<PotionEffect> effects = new HashSet<>();

    /**
     * Create a Kit.
     * @param name Name of the kit
     * @param id Id of the kit
     */
    public Kit(String name, int id) {
        this.name = name;
        this.id = id;
        price = 0;
        health = 20;

        arrow = new ItemBuilder(Material.ARROW, 64).setDisplayName("&aArrow").build();
        elytra = new ItemBuilder(Material.ELYTRA).setDisplayName("&aElytra").setUnbreakable(true).build();
        addItem(40, new ItemBuilder(Material.FIREWORK_ROCKET, 64).setDisplayName("&aFirework").build());

        getKits().add(this);
    }

    public ItemStack getArrow() {
        return arrow;
    }

    public static List<Kit> getKits() {
        return kits;
    }

    public void addEffect(PotionEffect effect) {
        getEffects().add(effect);
    }

    public void addItem(int slot, ItemStack item) {
        getItems().put(slot, item);
    }

    public Collection<PotionEffect> getEffects() {
        return effects;
    }

    public ItemStack getElytra() {
        return elytra;
    }

    public int getHealth() {
        return health;
    }

    public abstract ItemStack getIcon(Player p);

    public int getId() {
        return id;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void giveKit(Player p) {
        p.getInventory().clear();
        p.setMaxHealth(health);
        p.setHealth(health);

        getEffects().forEach(p::addPotionEffect);

        for(int slot : getItems().keySet()) {
            ItemStack item = getItems().get(slot);
            p.getInventory().setItem(slot, item);
        }
        p.getInventory().setItem(17, arrow);
        p.getInventory().setItem(38, elytra);

        CustomPlayer cp = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
        if(cp.getHat() != 0) {
            p.getInventory().setHelmet(Hat.getHats().get(cp.getHat()).getHat());
        }
    }

    public void setArrow(ItemStack arrow) {
        this.arrow = arrow;
    }

    public void setElytra(ItemStack elytra) {
        this.elytra = elytra;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}