package com.zennyel.listeners;

import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.manager.config.PetConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    private PetBoxConfigManager petBoxConfigManager;
    private PetConfigManager petConfigManager;
    public InventoryClick(PetBoxConfigManager petBoxConfigManager, PetConfigManager petConfigManager) {
        this.petBoxConfigManager = petBoxConfigManager;
        this.petConfigManager = petConfigManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getView().getTitle().equalsIgnoreCase(petConfigManager.getMenuTitle().replace("&", "§")) || event.getView().getTitle().equalsIgnoreCase(petBoxConfigManager.getMenuTitle().replace("&", "§"))) {
            event.setCancelled(true);
        }
    }

}
