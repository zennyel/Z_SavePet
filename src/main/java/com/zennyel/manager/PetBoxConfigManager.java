package com.zennyel.manager;

import com.zennyel.config.PetBoxConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

public class PetBoxConfigManager {
    private PetBoxConfig petBoxConfig;


    public Inventory getPetBoxInventory(){
        int size = getConfiguration().getInt("Menu.Size");
        String title = getConfiguration().getString("Menu.Title");
        Inventory inventory = Bukkit.createInventory(null, size, title);;
        return inventory;
    }
    public PetBoxConfigManager(PetBoxConfig petBoxConfig) {
        this.petBoxConfig = petBoxConfig;
    }

    public int getRarirtyPercentage(String tier){
        return getConfiguration().getInt("PetSettings.dropChance." + tier);
    }

    public FileConfiguration getConfiguration(){
        return petBoxConfig.getConfiguration();
    }
    public PetBoxConfig getPetBoxConfig() {
        return petBoxConfig;
    }
}
