package com.zennyel.listeners.extra;

import com.zennyel.manager.pet.PetManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetType;
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
        boostCoinGain(event);
    }

    public void boostCoinGain(MobCoinGainEvent event){
        Player player = event.getPlayer();
        long coins = event.getGained();
        for(Pet pet : petManager.getPlayerPets(player)){
            if(pet.getType() == PetType.COIN){
                event.setGained((long) (coins + coins* 0.01* pet.getLevel()));
            }
        }
    }


}
