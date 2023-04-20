package com.zennyel.listeners.player;

import com.zennyel.manager.pet.PetManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class PlayerDamageListener implements Listener {
    PetManager petManager;

    public PlayerDamageListener(PetManager petManager) {
        this.petManager = petManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            List<Pet> petList = petManager.getPlayerPets(player);
            Pet damagePet = null;
            for (Pet pet : petList) {
                if(pet.getType().equals(PetType.DAMAGE)){
                    damagePet = pet;
                }
            }
            boostDamage(damagePet, event);
        }
    }


    public void boostDamage(Pet pet, EntityDamageByEntityEvent event){
        if (pet != null && pet.getType().equals(PetType.DAMAGE)) {
            double multiplier = pet.getLevel() * 0.01;
            double damage = event.getDamage() + multiplier;
            event.setDamage(damage);
        }
    }


}
