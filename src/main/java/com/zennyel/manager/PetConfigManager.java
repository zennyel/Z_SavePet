package com.zennyel.manager;

import com.zennyel.SavePets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PetConfigManager {

    private FileConfiguration configuration;

    public PetConfigManager(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public Inventory createPetMenu() {
        // Criar um inventário com o tamanho e título especificados na configuração
        int size = configuration.getInt("Menu.Size");
        String title = ChatColor.translateAlternateColorCodes('&', configuration.getString("Menu.Title"));
        Inventory inventory = Bukkit.createInventory(null, size, title);

        // Adicionar os itens de borda na primeira e última linha do inventário
        for (int i = 0; i < size; i++) {
            if (i < 9 || i >= size - 9 || i % 9 == 0 || i % 9 == 8) {
                ItemStack borderItem = null;
                List<Map<?, ?>> displayItems = configuration.getMapList("Menu.Items.Border.Display-Item");
                if (displayItems != null && !displayItems.isEmpty()) {
                    Map<?, ?> item = displayItems.get(0);
                    if (item != null) {
                        Material material = Material.getMaterial((String) item.get("Material"));
                        if (material != null) {
                            String displayName = ChatColor.translateAlternateColorCodes('&', (String) item.get("DisplayName"));
                            List<String> lore = new ArrayList<>();
                            List<String> itemLore = (List<String>) item.get("Lore");
                            if (itemLore != null && !itemLore.isEmpty()) {
                                for (String line : itemLore) {
                                    lore.add(ChatColor.translateAlternateColorCodes('&', line));
                                }
                            }
                            borderItem = new ItemStack(material);
                            ItemMeta meta = borderItem.getItemMeta();
                            meta.setDisplayName(displayName);
                            meta.setLore(lore);
                            borderItem.setItemMeta(meta);
                        }
                    }
                }
                if (borderItem != null) {
                    inventory.setItem(i, borderItem);
                }
            }
        }

        // Adicionar os itens de pet na segunda linha do inventário
        List<Map<?, ?>> petItems = configuration.getMapList("Menu.Items.Pets");
        if (petItems != null) {
            for (int i = 0; i < petItems.size(); i++) {
                String rarity = (String) petItems.get(i).keySet().toArray()[0];
                Map<?, ?> rarityValues = (Map<?, ?>) petItems.get(i).get(rarity);

                Material material = null;
                String materialStr = (String) rarityValues.get("Material");
                if (materialStr != null) {
                    material = Material.getMaterial(materialStr);
                }

                if (material != null) {
                    String displayName = ChatColor.translateAlternateColorCodes('&', (String) rarityValues.get("DisplayName"));
                    List<String> lore = new ArrayList<>();
                    List<String> itemLore = (List<String>) rarityValues.get("Lore");
                    if (itemLore != null && !itemLore.isEmpty()) {
                        for (String line : itemLore) {
                            lore.add(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    }
                    ItemStack petItem = new ItemStack(material);
                    ItemMeta meta = petItem.getItemMeta();
                    meta.setDisplayName(displayName);
                    meta.setLore(lore);

                    int slot = i + 9;
                    inventory.setItem(slot, petItem);
                }
            }
        }

        return inventory;
    }



    public String getMenuTitle() {
        return configuration.getString("Menu.Title");
    }

    public int getMenuSize() {
        return configuration.getInt("Menu.Size");
    }

    public List<String> getMenuBorderLore() {
        return configuration.getStringList("Menu.Items.Border.Lore");
    }

    public String getCommonPetMaxLevel() {
        return configuration.getString("Pets.Rarity.common.maxLevel");
    }

    public String getRarePetMaxLevel() {
        return configuration.getString("Pets.Rarity.rare.maxLevel");
    }

    public String getEpicPetMaxLevel() {
        return configuration.getString("Pets.Rarity.epic.maxLevel");
    }

    public String getLegendaryPetMaxLevel() {
        return configuration.getString("Pets.Rarity.legendary.maxLevel");
    }

    public String getMythicalPetMaxLevel() {
        return configuration.getString("Pets.Rarity.mythical.maxLevel");
    }

    public String getCommonPetXP() {
        return configuration.getString("Pets.Rarity.common.xp");
    }

    public String getRarePetXP() {
        return configuration.getString("Pets.Rarity.rare.xp");
    }

    public String getEpicPetXP() {
        return configuration.getString("Pets.Rarity.epic.xp");
    }

    public String getLegendaryPetXP() {
        return configuration.getString("Pets.Rarity.legendary.xp");
    }

    public String getMythicalPetXP() {
        return configuration.getString("Pets.Rarity.mythical.xp");
    }

    public boolean getMoneySkullEnabled() {
        return configuration.getBoolean("PetItems.Money.skull_enabled");
    }

    public String getMoneySkull() {
        return configuration.getString("PetItems.Money.skull");
    }

    public String getMoneyMaterial() {
        if (getMoneySkullEnabled()) {
            return null;
        }
        return configuration.getString("PetItems.Money.material");
    }

    public boolean getExpSkullEnabled() {
        return configuration.getBoolean("PetItems.Exp.skull_enabled");
    }

    public String getExpSkull() {
        return configuration.getString("PetItems.Exp.skull");
    }

    public String getExpMaterial() {
        if (getExpSkullEnabled()) {
            return null;
        }
        return configuration.getString("PetItems.Exp.material");
    }

    public boolean getDamageSkullEnabled() {
        return configuration.getBoolean("PetItems.Damage.skull_enabled");
    }

    public String getDamageSkull() {
        return configuration.getString("PetItems.Damage.skull");
    }

    public String getDamageMaterial() {
        if (getDamageSkullEnabled()) {
            return null;
        }
        return configuration.getString("PetItems.Damage.material");
    }

    public boolean getCoinSkullEnabled() {
        return configuration.getBoolean("PetItems.Coin.skull_enabled");
    }

    public String getCoinSkull() {
        return configuration.getString("PetItems.Coin.skull");
    }

    public String getCoinMaterial() {
        if (getCoinSkullEnabled()) {
            return null;
        }
        return configuration.getString("PetItems.Coin.material");
    }

    public String get(String path) {
        return configuration.getString(path);
    }

}


