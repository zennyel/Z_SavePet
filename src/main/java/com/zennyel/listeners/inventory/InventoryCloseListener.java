package com.zennyel.listeners.inventory;

import com.zennyel.manager.config.PetFusionConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.utils.PetUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Objects;

public class InventoryCloseListener implements Listener {

    PetFusionConfigManager petFusionConfigManager;

    public InventoryCloseListener(PetFusionConfigManager petFusionConfigManager) {
        this.petFusionConfigManager = petFusionConfigManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        handlePetFusion(event);
    }

    public void handlePetFusion(InventoryCloseEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(petFusionConfigManager.getTitle().replace("&", "§"))){
            Pet one = PetUtils.getPetByTags(Objects.requireNonNull(event.getInventory().getItem(11)));
            if(one != null){
                event.getPlayer().getInventory().addItem(event.getInventory().getItem(11).asOne());
            }

            Pet two = PetUtils.getPetByTags(Objects.requireNonNull(event.getInventory().getItem(15)));
            if(two != null){
                event.getPlayer().getInventory().addItem(event.getInventory().getItem(15).asOne());
            }
        }
    }

}
