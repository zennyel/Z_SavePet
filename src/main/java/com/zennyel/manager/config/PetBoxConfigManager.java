package com.zennyel.manager.config;


import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import com.zennyel.utils.PetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;


public class PetBoxConfigManager{

    private FileConfiguration configuration;

    public PetBoxConfigManager(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getMenuTitle() {
        return getConfig().getString("Menu.Title");
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public  ItemStack getPetBoxItemStack() {
        ItemStack itemStack = new ItemStack(Material.valueOf(getConfig().getString("PetBoxItem.Material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("PetBoxItem.DisplayName")));

        List<String> lore = new ArrayList<>();
        for (String line : getConfig().getStringList("PetBoxItem.Lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public  ItemStack getPetBoxItemStack(int quantity) {
        ItemStack itemStack = new ItemStack(Material.valueOf(getConfig().getString("PetBoxItem.Material")), quantity);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString("PetBoxItem.DisplayName")));

        List<String> lore = new ArrayList<>();
        for (String line : getConfig().getStringList("PetBoxItem.Lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        Random random = new Random();
        short durability = (short) random.nextInt(Short.MAX_VALUE + 1);
        itemStack.setDurability(durability);
        return itemStack;
    }

    public  Inventory createInventory() {
        String title = getMenuTitle();
        ItemStack borderItem = getBorderDisplayItem();
        ItemStack middleItem = getMiddleDisplayItem();
        Inventory inventory = Bukkit.createInventory(null, 27, title);

        for (int i = 0; i < 27; i++) {
            if (i == 4 || i == 22) continue;
            inventory.setItem(i, borderItem);
        }

        inventory.setItem(4, middleItem);
        inventory.setItem(22, middleItem);

        return inventory;
    }

    public ItemStack getBorderDisplayItem() {
        Material material = Material.getMaterial(getConfiguration().getString("Menu.Items.Border.Display-Item.Material"));
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = getConfiguration().getString("Menu.Items.Border.Middle-Item.DisplayName");
        displayName = displayName.replace("&", "§");
        List<String> lore = getConfiguration().getStringList("Menu.Items.Border.Middle-Item.Lore");
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getMiddleDisplayItem() {
        Material material = Material.getMaterial(getConfiguration().getString("Menu.Items.Border.Middle-Item.Material"));
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = getConfiguration().getString("Menu.Items.Border.Middle-Item.DisplayName");
        displayName = displayName.replace("&", "§");
        List<String> lore = getConfiguration().getStringList("Menu.Items.Border.Middle-Item.Lore");
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    public double getMultiplier(PetRarity rarity, int level) {
        String formula = getConfig().getString("PetsSettings." + rarity.toString().toLowerCase() + ".multiplier");
        formula = formula.replaceAll("\\{level\\}", Integer.toString(level));
        return evalFormula(formula);
    }

    public  double getDropChance(PetRarity rarity) {
        return getConfig().getDouble("PetsSettings." + rarity.toString().toLowerCase() + ".dropChance");
    }

    private static double evalFormula(String formula) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            Object result = engine.eval(formula);
            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ItemStack getItemByType(PetType type) {
        ItemStack itemStack;
        ItemMeta isMeta;
        List<String> lore = getConfig().getStringList("PetItems.Lore");
        switch(type) {
            case MONEY:
                if (getConfig().getBoolean("PetItems.Money.skull_enabled")) {
                    itemStack = createSkullItemStack(getConfig().getString("PetItems.Money.skull"));
                } else {
                    itemStack = new ItemStack(Material.valueOf(getConfig().getString("PetItems.Money.material")));
                }
                isMeta = itemStack.getItemMeta();
                isMeta.setDisplayName(getConfig().getString("PetItems.Money.displayName"));
                isMeta.setLore(lore);
                itemStack.setItemMeta(isMeta);
            case EXP:
                if (getConfig().getBoolean("PetItems.Exp.skull_enabled")) {
                    return createSkullItemStack(getConfig().getString("PetItems.Exp.skull"));
                } else {
                    return new ItemStack(Material.valueOf(getConfig().getString("PetItems.Exp.material")));
                }
            case DAMAGE:
                if (getConfig().getBoolean("PetItems.Damage.skull_enabled")) {
                    return createSkullItemStack(getConfig().getString("PetItems.Damage.skull"));
                } else {
                    return new ItemStack(Material.valueOf(getConfig().getString("PetItems.Damage.material")));
                }
            case COIN:
                if (getConfig().getBoolean("PetItems.Coin.skull_enabled")) {
                    return createSkullItemStack(getConfig().getString("PetItems.Coin.skull"));
                } else {
                    return new ItemStack(Material.valueOf(getConfig().getString("PetItems.Coin.material")));
                }
            default:
                return null;
        }
    }

    public ItemStack getItemByPet(Pet pet)  {
        PetUtils.setPetMaxLevels(pet);
        ItemStack itemStack;
        ItemMeta isMeta;
        PetType type = pet.getType();
        List<String> lore = getLore(pet);
        String typeString = WordUtils.capitalizeFully(type.toString().toLowerCase());
        if (getConfig().getBoolean("PetItems."+ typeString + ".skull_enabled")) {
                    itemStack = createSkullItemStack(getConfig().getString("PetItems."+ typeString +".skull"));
                } else {
                    itemStack = new ItemStack(Material.valueOf(getConfig().getString("PetItems."+ typeString +"material")));
                }
        PetUtils.addNbTags(pet,itemStack);
                isMeta = itemStack.getItemMeta();
                isMeta.setDisplayName(getDisplayName("PetItems."+ typeString +".displayName", pet));
                isMeta.setLore(lore);
                itemStack.setItemMeta(isMeta);
        return itemStack;
    }

    public String getDisplayName(String path, Pet pet){
        String displayname = getConfig().getString(path);
        displayname = displayname.replace("&", "§").
                replace("{petLevel}", "" + pet.getLevel());
        return displayname;
    }
    public List<String> getLore(Pet pet) {
        List<String> lore = getConfig().getStringList("PetItems.Lore");
        List<String> newLore = new ArrayList<>();
        int petExp = (int) pet.getExperience();
        int maxExp = (int) pet.getMaxExperience();
        int level = pet.getLevel();
        int maxLevel = pet.getMaxLevel();

        for (String line : lore) {
            String newLine = line.replace("&", "§")
                    .replace("{tier}", tierFormatter(pet))
                    .replace("{boost}", String.valueOf(getBoost(pet)))
                    .replace("{petLevel}", String.valueOf(level))
                    .replace("{petMaxLevel}", String.valueOf(maxLevel))
                    .replace("{petExp}", String.valueOf(petExp))
                    .replace("{petMaxExp}", String.valueOf(maxExp))
                    .replace("{progressiveBar}", getProgressiveBar(petExp, maxExp, 50, "|", "§a", "§c"));
            newLore.add(newLine);
        }
        return newLore;
    }

    public String tierFormatter(Pet pet){
        PetRarity rarity = pet.getRarity();
        String typeString = rarity.toString();
        switch (rarity){
            case COMMON:
                typeString = "§a§l" + typeString;
                break;
            case RARE:
                typeString = "§b§l" + typeString;
                break;
            case EPIC:
                typeString = "§5§l" + typeString;
                break;
            case MYTHICAL:
                typeString = "§6§l" + typeString;
                break;
            case LEGENDARY:
                typeString = "§4§l" + typeString;
                break;
        }
        return typeString;
    }

    public String getBoost(Pet pet) {
        PetRarity rarity = pet.getRarity();
        int multiplier = getConfig().getInt("PetSettings."+String.valueOf(rarity).toLowerCase()+".dropChance");

        double boostedValue = 100 * (1 + (multiplier / 100.0));
        return String.valueOf(boostedValue);
    }

    public String getProgressiveBar(int current, int max, int totalBars, String barChar, String completedColor, String notCompletedColor){
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return StringUtils.repeat(completedColor + barChar, progressBars)
                + StringUtils.repeat(notCompletedColor + barChar, totalBars - progressBars) + getPercentage(current, max);
    }

    public static String getPercentage(int value, int maxvalue) {
        int percentage = (int) (((double) value / (double) maxvalue) * 100.0);
        return " §f" + percentage + "%";
    }


    private ItemStack createSkullItemStack(String texture) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        UUID hashAsId = new UUID(texture.hashCode(), texture.hashCode());
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(hashAsId));
        meta.setDisplayName(ChatColor.RESET + "");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        return Bukkit.getUnsafe().modifyItemStack(item, "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + texture + "\"}]}}}");
    }




    private FileConfiguration getConfig() {
        return configuration;
    }
}

