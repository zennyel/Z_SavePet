package com.zennyel.manager.pet;

import com.zennyel.SavePets;
import com.zennyel.database.PetsDB;
import com.zennyel.events.PetEquipEvent;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetType;
import com.zennyel.utils.PetUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PetManager {
    private PetsDB petsDB;
    private SavePets instance;
    private HashMap<Player, List<Pet>> petSets;

    public PetManager(PetsDB petsDB, SavePets instance) {
        this.petsDB = petsDB;
        this.instance = instance;
        petSets = new HashMap<>();
    }

    public void equipPet(Player player, Pet pet) {
        List<Pet> petList = petSets.computeIfAbsent(player, k -> new ArrayList<>());
        Iterator<Pet> petIterator = petList.iterator();

        while (petIterator.hasNext()) {
            Pet currentPet = petIterator.next();
            if (pet.getType().equals(currentPet.getType())) {
                ItemStack itemStack = PetUtils.getItemByPet(currentPet);
                petIterator.remove();
                player.getInventory().addItem(itemStack.asOne());
            }
        }

        if (petList.size() < 4) {
            petList.add(pet);
        }

        Bukkit.getPluginManager().callEvent(new PetEquipEvent(pet));
        petSets.put(player, petList);
    }


    public void savePlayerPets(Player player){
            List<Pet> pets = petSets.get(player);
            for(Pet pet : pets){
                petsDB.insertPet(player.getUniqueId(), pet);
            }
    }

    public Pet getPetByType(Player player, PetType petType){
        for(Pet pet: getPlayerPets(player)) {
            if(pet.getType().equals(petType)){
                return pet;
            }
        }
        return null;
    }

    public void loadPlayerPets(Player player){
        List<Pet> pets = petsDB.getPets(player.getUniqueId());
        if (petSets.containsKey(player)) {
            petSets.remove(player);
        }
        petSets.put(player, pets);
    }


    public List<Pet> getPlayerPets(Player player){
        return petSets.get(player);
    }

    public void addPlayerPet(Player player, Pet pet){
        petSets.get(player).add(pet);
    }

    public void removePlayerPet(Player player, Pet pet){
        List<Pet> petList = petSets.get(player);
        petList.remove(pet);
        petSets.put(player, petList);
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
