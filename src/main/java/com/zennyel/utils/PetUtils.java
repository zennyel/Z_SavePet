package com.zennyel.utils;

import com.zennyel.SavePets;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import fun.lewisdev.savemobcoins.SaveMobCoinsAPI;
import fun.lewisdev.savemobcoins.event.MobCoinGainEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class PetUtils {

    private static final String PET_TYPE_TAG = "pet_type";
    private static final String PET_RARITY_TAG = "pet_rarity";
    private static final String PET_LEVEL_TAG = "pet_level";
    private static final String PET_EXPERIENCE_TAG = "pet_experience";
    private static final SavePets plugin = JavaPlugin.getPlugin(SavePets.class);
    private static final FileConfiguration configuration = plugin.getManager().getPetBoxConfig().getConfiguration();
    private static final FileConfiguration petConfig = plugin.getManager().getPetConfig().getConfiguration();

    public static ItemStack getItemByPet(Pet pet)  {
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
        setPetMaxLevels(pet);
        return itemStack;
    }

    public static String getDisplayName(String path, Pet pet){
        String displayname = getConfig().getString(path);
        displayname = displayname.replace("&", "§").
                replace("{petLevel}", "" + pet.getLevel());
        return displayname;
    }
    public static List<String> getLore(Pet pet) {
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






    public static String getBoost(Pet pet) {
        PetRarity rarity = pet.getRarity();
        int multiplier = getConfig().getInt("PetSettings."+String.valueOf(rarity).toLowerCase()+".dropChance");

        double boostedValue = 100 * (1 + (multiplier / 100.0));
        return String.valueOf(boostedValue);
    }

    public static String getProgressiveBar(int current, int max, int totalBars, String barChar, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < progressBars; i++) {
            sb.append(completedColor).append(barChar);
        }
        for (int i = progressBars; i < totalBars; i++) {
            sb.append(notCompletedColor).append(barChar);
        }
        sb.append(getPercentage(current, max));

        return sb.toString();
    }


    public static String getPercentage(int value, int maxvalue) {
        int percentage = (int) (((double) value / (double) maxvalue) * 100.0);
        return " §f" + percentage + "%";
    }


    public static void setPetMaxLevels(Pet pet){
        PetRarity rarity = pet.getRarity();
        String petRarity = rarity.toString().toLowerCase();
        int maxLevel = petConfig.getInt("Pets.Rarity." + petRarity + ".maxLevel");
        double xpFinal = getMaxExp(pet);
        pet.setMaxLevel(maxLevel);
        pet.setMaxExperience(xpFinal);
    }

    public static double getMaxExp(Pet pet){
        PetRarity rarity = pet.getRarity();
        switch (rarity){
            case COMMON:
                return 1 + pet.getLevel()*100;
            case RARE:
                return 2 + pet.getLevel()*100;
            case EPIC:
                return 3 + pet.getLevel()*100;
            case MYTHICAL:
                return 4 + pet.getLevel()*100;
            case LEGENDARY:
                return 5 + pet.getLevel()*100;
        }
        return 0.0;
    }

    private static double evaluateXpFormula(String xpFormula, int level) {
        xpFormula = xpFormula.replace("{level}", String.valueOf(level));
        ScriptEngineManager manager = new ScriptEngineManager(null);
        ScriptEngine engine = manager.getEngineByName("Nashorn");

        try {
            Object result = engine.eval(xpFormula);
            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static String tierFormatter(Pet pet){
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

    private static ItemStack createSkullItemStack(String texture) {
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
    public static void addNbTags(Pet pet, ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        itemMeta.getPersistentDataContainer().set(
                PetUtils.getKey(PET_TYPE_TAG),
                PersistentDataType.STRING,
                pet.getType().name()
        );
        itemMeta.getPersistentDataContainer().set(
                PetUtils.getKey(PET_RARITY_TAG),
                PersistentDataType.STRING,
                pet.getRarity().name()
        );
        itemMeta.getPersistentDataContainer().set(
                PetUtils.getKey(PET_LEVEL_TAG),
                PersistentDataType.INTEGER,
                pet.getLevel()
        );
        itemMeta.getPersistentDataContainer().set(
                PetUtils.getKey(PET_EXPERIENCE_TAG),
                PersistentDataType.DOUBLE,
                pet.getExperience()
        );
        itemStack.setItemMeta(itemMeta);
    }

    public static Pet getPetByTags(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (!container.has(PetUtils.getKey(PET_TYPE_TAG), PersistentDataType.STRING)
                || !container.has(PetUtils.getKey(PET_RARITY_TAG), PersistentDataType.STRING)
                || !container.has(PetUtils.getKey(PET_LEVEL_TAG), PersistentDataType.INTEGER)
                || !container.has(PetUtils.getKey(PET_EXPERIENCE_TAG), PersistentDataType.DOUBLE))
            return null;
        try {
            Pet pet = new Pet(PetType.valueOf(container.get(PetUtils.getKey(PET_TYPE_TAG), PersistentDataType.STRING)),
                    PetRarity.valueOf(container.get(PetUtils.getKey(PET_RARITY_TAG), PersistentDataType.STRING)),
                    container.get(PetUtils.getKey(PET_LEVEL_TAG), PersistentDataType.INTEGER),
                    container.get(PetUtils.getKey(PET_EXPERIENCE_TAG), PersistentDataType.DOUBLE));
            setPetMaxLevels(pet);
            return pet;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static FileConfiguration getConfig(){
        return configuration;
    }





    private static NamespacedKey getKey(String tag) {
        return new NamespacedKey(plugin, tag);
    }
}
