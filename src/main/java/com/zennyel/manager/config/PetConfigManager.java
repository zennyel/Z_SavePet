package com.zennyel.manager.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetConfigManager {

    private FileConfiguration configuration;

    public PetConfigManager(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public Inventory createPetMenu() {
        int size = configuration.getInt("Menu.Size");
        String title = ChatColor.translateAlternateColorCodes('&', configuration.getString("Menu.Title"));
        Inventory inventory = Bukkit.createInventory(null, size, title);

        List<Map<?, ?>> displayItems = configuration.getMapList("Menu.Items.Border.Display-Item");
        if (displayItems == null || displayItems.isEmpty()) {
            return inventory;
        }

        ItemStack topItem = null;
        ItemStack bottomItem = null;
        for (Map<?, ?> item : displayItems) {
            Material material = Material.getMaterial((String) item.get("Material"));
            if (material == null) {
                continue;
            }
            String displayName = ChatColor.translateAlternateColorCodes('&', (String) item.get("DisplayName"));
            List<String> lore = new ArrayList<>();
            List<String> itemLore = (List<String>) item.get("Lore");
            if (itemLore != null && !itemLore.isEmpty()) {
                for (String line : itemLore) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', line));
                }
            }
            ItemStack borderItem = new ItemStack(material);
            ItemMeta meta = borderItem.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            borderItem.setItemMeta(meta);

            if (topItem == null) {
                topItem = borderItem;
            }
            bottomItem = borderItem;
        }

        for(int i = 0; i < inventory.getSize() - 9; i++){
            inventory.setItem(i, topItem);
        }
        for(int i = inventory.getSize() - 9; i < inventory.getSize(); i++){
            inventory.setItem(i, bottomItem);
        }
        return inventory;
    }




    public String getBarrierDisplayName(){return configuration.getString("Menu.Items.Barrier.DisplayName").replace("&", "§");}
    public List<String> getBarrierLore(){
        List<String> lore = new ArrayList<>();
        for(String s : configuration.getStringList("Menu.Items.Barrier.Lore")){
            lore.add(s.replace("&", "§"));
        }
        return lore;
    }

    public String getMenuTitle() {
        return configuration.getString("Menu.Title");
    }

    public ItemStack getPetCandyItem(){
        Material material = Material.getMaterial(getConfiguration().getString("PetCandy.material"));
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = getConfiguration().getString("PetCandy.displayName");
        displayName = displayName.replace("&", "§");
        List<String> lore = getConfiguration().getStringList("PetCandy.lore");
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}


