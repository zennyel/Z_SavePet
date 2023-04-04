package com.zennyel.manager;

import com.zennyel.SavePets;
import com.zennyel.database.PetsDB;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PetManager {
    private PetsDB petsDB;
    private SavePets instance;

    private HashMap<Player, List<Pet>> petSets;

    public PetManager(PetsDB petsDB, SavePets instance) {
        this.petsDB = petsDB;
        this.instance = instance;
        petSets = new HashMap<>();
    }

    public void savePlayerPets(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            List<Pet> pets = petSets.get(player);
            for(Pet pet : pets){
                petsDB.insertPet(pet);
            }
        });
    }
    public void loadPlayerPets(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
        petSets.put(player, petsDB.getPlayerPets(player.getUniqueId()));
        });
    }

    public List<Pet> getPlayerPets(Player player){
        return petSets.get(player);
    }

    public void addPlayerPet(Player player, Pet pet){
        petSets.get(player).add(pet);
    }

    public void removePlayerPet(Player player, Pet pet){
        petSets.get(player).remove(pet);
    }

    public Pet getPlayerPetbyType(Player player, PetType type){
        List<Pet> petList = petSets.get(player);
        for(Pet pet : petList){
            if(pet.getType().equals(type)){
                return pet;
            }
        }
        return null;
    }


}
