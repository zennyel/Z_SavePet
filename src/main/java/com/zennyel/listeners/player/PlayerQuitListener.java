package com.zennyel.listeners.player;

import com.zennyel.manager.pet.PetManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private PetManager petManager;


    public PlayerQuitListener(PetManager petManager) {
        this.petManager = petManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        petManager.savePlayerPets(player);

    }

}
