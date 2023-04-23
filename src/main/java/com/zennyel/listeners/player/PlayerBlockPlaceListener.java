package com.zennyel.listeners.player;

import com.zennyel.manager.config.PetBoxConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerBlockPlaceListener implements Listener {

    private PetBoxConfigManager petBoxConfigManager;

    public PlayerBlockPlaceListener(PetBoxConfigManager petBoxConfigManager) {
        this.petBoxConfigManager = petBoxConfigManager;
    }

    @EventHandler
    public void onPlayerBlockPlace(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack block = event.getPlayer().getItemInHand();

            if(block.getItemMeta() == null){
                return;
            }

            if(block.getItemMeta().getLore() == null){
                return;
            }

            if(block.getItemMeta().getLore().equals(petBoxConfigManager.getPetBoxItemStack().getLore())){
                event.setCancelled(true);
            }
        }


    }


}
