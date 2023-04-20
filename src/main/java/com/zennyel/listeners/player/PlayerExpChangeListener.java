package com.zennyel.listeners.player;

import com.zennyel.manager.pet.PetManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetType;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.List;

public class PlayerExpChangeListener implements Listener {

    private PetManager petManager;

    public PlayerExpChangeListener(PetManager petManager) {
        this.petManager = petManager;
    }

    @EventHandler
    public void onPlayerChangeExp(PlayerExpChangeEvent event){

        Player player = event.getPlayer();

        if(event.getSource() instanceof EntityDeathEvent){
            return;
        }

        EntityDeathEvent deathEvent = (EntityDeathEvent) event.getSource();
        if (deathEvent.getEntity() instanceof Monster && deathEvent.getEntity() instanceof Animals) {
            return;
        }

        List<Pet> petList = petManager.getPlayerPets(player);
        Pet expPet = null;
        for(Pet pet : petList) {
            handlePetExperience(event, pet);
            if (pet.getType().equals(PetType.EXP)) {
                expPet = pet;
            }
        }
        boostPlayerExp(event, expPet);

    }

    public void handlePetExperience(PlayerExpChangeEvent event, Pet pet){
            pet.setExperience(pet.getExperience() + event.getAmount());
        }

    public void boostPlayerExp(PlayerExpChangeEvent event, Pet pet){
        PetType type = pet.getType();
        if(type.equals(PetType.EXP)){
            return;
        }
        double multiplier = event.getAmount() * pet.getLevel() * 0.01;
        event.setAmount((int) (event.getAmount() + multiplier));
    }


}
