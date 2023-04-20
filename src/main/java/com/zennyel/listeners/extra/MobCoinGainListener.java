package com.zennyel.listeners.extra;

import com.zennyel.manager.pet.PetManager;
import fun.lewisdev.savemobcoins.event.MobCoinGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MobCoinGainListener implements Listener {

    private PetManager petManager;

    public MobCoinGainListener(PetManager petManager) {
        this.petManager = petManager;
    }

    @EventHandler
    public void onMobCoinGain(MobCoinGainEvent event){
        Player player = event.getPlayer();
        long coins = event.getGained();

    }


}
