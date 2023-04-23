package com.zennyel.listeners.player;

import com.zennyel.GUI.PetBoxGUI;
import com.zennyel.SavePets;
import com.zennyel.events.BoxOpenEvent;
import com.zennyel.manager.config.PetBoxConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerItemInteractListener implements Listener {

    private PetBoxConfigManager petBoxConfigManager;
    private SavePets instance;

    public PlayerItemInteractListener(PetBoxConfigManager petBoxConfigManager, SavePets instance) {
        this.petBoxConfigManager = petBoxConfigManager;
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerItemInteract(PlayerInteractEvent event){

        Player player = event.getPlayer();
        handlePetBoxInteract(event,player);
    }

    public void handleCandyInteract()

    public void handlePetBoxInteract(PlayerInteractEvent event, Player player){
        ItemStack is = petBoxConfigManager.getPetBoxItemStack();

        if(player.getItemInHand().getType() != is.getType()) {
            return;
        }

        if(player.getItemInHand().getItemMeta().getLore() == null){
            return;
        }

        if (!player.getItemInHand().getItemMeta().getLore().equals(is.getItemMeta().getLore())){
            return;
        }
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            PetBoxGUI petBoxGUI = new PetBoxGUI(petBoxConfigManager.createInventory(), player, petBoxConfigManager.getConfiguration(), instance, petBoxConfigManager);
            petBoxGUI.addItems();
            Bukkit.getPluginManager().callEvent(new BoxOpenEvent(player, petBoxGUI));
            player.openInventory(petBoxGUI.getInventory());
        }

    }


}
