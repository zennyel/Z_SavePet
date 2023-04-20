package com.zennyel.GUI;

import com.zennyel.SavePets;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public abstract class CustomGUI implements Listener {

    private Inventory inventory;
    private FileConfiguration config;
    private SavePets instance;
    private Player player;
    public CustomGUI(Inventory inventory, Player player, FileConfiguration config, SavePets instance) {
        this.instance = instance;
        this.inventory = inventory;
        this.config = config;
        this.player = player;
    }

    public void addItems(){

    }

    public ItemStack Item(Material material, String displayName, String description, int slotPosition){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        if (slotPosition >= 0 && slotPosition <= 8) {
            return item;
        } else if (slotPosition >= 9 && slotPosition <= 17) {
            return item;
        } else if (slotPosition >= 18 && slotPosition <= 26) {
            return item;
        }
        return null;
    }

    public ItemStack Item(Material material, String displayName,List<String> description, int slotPosition){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(description);
        item.setItemMeta(meta);
        if (slotPosition >= 0 && slotPosition <= 8) {
            return item;
        } else if (slotPosition >= 9 && slotPosition <= 17) {
            return item;
        } else if (slotPosition >= 18 && slotPosition <= 26) {
            return item;
        }
        return null;
    }


    public Player getPlayer() {
        return player;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public SavePets getInstance() {
        return instance;
    }

}
