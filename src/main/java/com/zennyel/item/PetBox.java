package com.zennyel.item;

import com.zennyel.manager.PetBoxConfigManager;
import com.zennyel.pet.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PetBox{

    private PetBoxConfigManager configuration;

    public PetBox(PetBoxConfigManager configuration) {
        this.configuration = configuration;
    }

    public ItemStack createPetItem(Pet pet, ItemStack itemStack) {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        String displayName = ChatColor.YELLOW + pet.getType().toString() + ChatColor.GOLD + "Pet" + " [" + pet.getLevel() + "]";
        meta.setDisplayName(displayName);
        meta.setLore(petLore(pet));
        item.setItemMeta(meta);
        return item;
    }

    private List<String> petLore(Pet pet){
        int rarity;
        switch (String.valueOf(pet.getRarity())){
            case "COMMON":
                rarity = configuration.getRarirtyPercentage("common");
            case "RARE":
                rarity = configuration.getRarirtyPercentage("rare");
            case "EPIC":
                rarity = configuration.getRarirtyPercentage("epic");
            case "LEGENDARY":
                rarity = configuration.getRarirtyPercentage("legendary");
            case "MYTHICAL:":
                rarity = configuration.getRarirtyPercentage("mythical");
            default:
                rarity = 0;
        }
        int boostmultiplier = (int) (pet.getLevel()*0.02*rarity);
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GRAY + "ABILITY");
        lore.add(ChatColor.GRAY + "The booster at your level is +" + pet.getLevel());
        lore.add(ChatColor.GRAY + "The max booster at LV" + pet.getMaxLevel() + " pet is +" + "%");
        lore.add("");
        lore.add(ChatColor.GRAY + "LEVEL: " + pet.getLevel() + " / " + pet.getMaxLevel());
        lore.add(ChatColor.GRAY + "EXP: " + pet.getExperience() + " / 9,000");
        return lore;
    }



}
