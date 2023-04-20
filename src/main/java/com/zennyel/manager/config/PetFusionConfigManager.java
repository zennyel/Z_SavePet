package com.zennyel.manager.config;

import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class PetFusionConfigManager {

    private FileConfiguration fileConfiguration;
    private final String title;
    private ItemStack borderItem;
    private ItemStack middleItem;
    private ItemStack confirmationItem;
    private ItemStack barrierItem;

    public PetFusionConfigManager(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
        this.title = ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("Menu.Title"));
    }


    public Inventory createInventory() {
        FileConfiguration config = getFileConfiguration();

        Inventory inventory = Bukkit.createInventory(null, 36, title);

        borderItem = new ItemStack(Material.valueOf(config.getString("Menu.Items.Border.Display-Item.Material")));
        ItemMeta borderMeta = borderItem.getItemMeta();
        borderMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Menu.Items.Border.Display-Item.DisplayName")));
        borderMeta.setLore(translateColorCodes(config.getStringList("Menu.Items.Border.Display-Item.Lore")));
        borderItem.setItemMeta(borderMeta);
        for (int i = 0; i < 36; i += 9) {
            for (int j = i; j < i + 9; j++) {
                inventory.setItem(j, borderItem);
            }
        }

        middleItem = new ItemStack(Material.valueOf(config.getString("Menu.Items.Border.Middle-Item.Material")));
        ItemMeta middleMeta = middleItem.getItemMeta();
        middleMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Menu.Items.Border.Middle-Item.DisplayName")));
        middleMeta.setLore(translateColorCodes(config.getStringList("Menu.Items.Border.Middle-Item.Lore")));
        middleItem.setItemMeta(middleMeta);
        inventory.setItem(13, middleItem);

        confirmationItem = new ItemStack(Material.valueOf(config.getString("Menu.Items.Border.Confirmation-Item.Material")));
        ItemMeta confirmationMeta = confirmationItem.getItemMeta();
        confirmationMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Menu.Items.Border.Confirmation-Item.DisplayName")));
        confirmationMeta.setLore(translateColorCodes(config.getStringList("Menu.Items.Border.Confirmation-Item.Lore")));
        confirmationItem.setItemMeta(confirmationMeta);
        inventory.setItem(31, confirmationItem);

        barrierItem = new ItemStack(Material.valueOf(config.getString("Menu.Items.Border.Barrier-Item.Material")));
        ItemMeta barrierMeta = barrierItem.getItemMeta();
        barrierMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Menu.Items.Border.Barrier-Item.DisplayName")));
        barrierMeta.setLore(translateColorCodes(config.getStringList("Menu.Items.Border.Barrier-Item.Lore")));
        barrierItem.setItemMeta(barrierMeta);
        for (int slot : config.getIntegerList("Menu.Items.Border.Barrier-Item.Slot")) {
            inventory.setItem(slot, barrierItem);
        }

        return inventory;
    }

    public Pet getPetByItem(ItemStack itemstack) {
        if (itemstack == null) {
            return null;
        }

        Material material = itemstack.getType();
        if (material == null) {
            return null;
        }

        String materialName = material.name();
        if (!materialName.endsWith("_PET")) {
            return null;
        }

        String petTypeString = materialName.substring(0, materialName.indexOf("_PET"));
        PetType petType = PetType.valueOf(petTypeString.toUpperCase());

        PetRarity petRarity = null;
        int petLevel = 1;
        double petExp = 0.0;

        ItemMeta itemMeta = itemstack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = itemMeta.getLore();
            if (lore != null && lore.size() >= 7) {
                String tierLine = ChatColor.stripColor(lore.get(0));
                petRarity = PetRarity.getRarityByTier(tierLine);
                String[] expAndLevel = ChatColor.stripColor(lore.get(5)).split(" ");
                petExp = Double.parseDouble(expAndLevel[1]);
                petLevel = Integer.parseInt(expAndLevel[3]);
            }
        }

        return new Pet(petType, petRarity, petLevel, petExp);
    }

    public PetRarity getRarityByTier(String tier) {
        for (PetRarity rarity : PetRarity.values()) {
            if (rarity.getTierName().equals(tier)) {
                return rarity;
            }
        }
        return null;
    }



    private List<String> translateColorCodes(List<String> list) {
            return list.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
        }

    public String getTitle() {
        return title;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public ItemStack getBorderItem() {
        return borderItem;
    }

    public ItemStack getMiddleItem() {
        return middleItem;
    }

    public ItemStack getConfirmationItem() {
        return confirmationItem;
    }

    public ItemStack getBarrierItem() {
        return barrierItem;
    }
}
