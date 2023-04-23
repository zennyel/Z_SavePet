package com.zennyel.listeners.pet;

import com.zennyel.events.PetExpChangeEvent;
import com.zennyel.pet.Pet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PetExpChangeListener implements Listener {


    @EventHandler
    public void onPetExpChange(PetExpChangeEvent event){
        handlePetLevelUp(event);
    }

    public void handlePetLevelUp(PetExpChangeEvent event){
        Pet pet = event.getPet();
        double rest = pet.getExperience() - pet.getMaxExperience();
        double total = pet.getExperience() + event.getNewExp();
        if(total < pet.getMaxExperience()){
            pet.setLevel(pet.getLevel() + 1);
            pet.setExperience(event.getNewExp() - rest);
        }
    }



}
