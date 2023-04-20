package com.zennyel.GUI;

import com.zennyel.SavePets;
import com.zennyel.manager.pet.PetManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetItem;
import com.zennyel.pet.PetType;
import com.zennyel.utils.PetUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PetsGUI extends CustomGUI{
    private PetManager petManager;
    private final PetItem petItem;
    private final PetConfigManager petConfigManager;

    public PetsGUI(Inventory inventory, Player player, FileConfiguration config, SavePets instance, PetManager petManager, PetConfigManager petConfigManager) {
        super(inventory, player, config, instance);
        if (petManager == null) {
            throw new IllegalArgumentException("PetManager cannot be null.");
        }
        this.petManager = petManager;
        this.petConfigManager = petConfigManager;
        this.petItem = new PetItem();
    }

    public void addItems(Player player) {
        List<Pet> petList = new ArrayList<>(petManager.getPlayerPets(getPlayer()));
        int[] slots = {9 + 1, 9 + 3, 9 + 5, 9 + 7};
        setBarrageIcon(slots[0]);
        setBarrageIcon(slots[1]);
        setBarrageIcon(slots[2]);
        setBarrageIcon(slots[3]);
        addPetItems(player);
    }

    public void addPetItems(Player player){
       for(Pet pet : petManager.getPlayerPets(player)){
           PetType type = pet.getType();
           ItemStack is = PetUtils.getItemByPet(pet);
           switch (type){
               case EXP:
                   getInventory().setItem(16, is);
                   break;
               case COIN:
                   getInventory().setItem(14, is);
                   break;
               case MONEY:
                   getInventory().setItem(12, is);
                   break;
               case DAMAGE:
                   getInventory().setItem(10, is);
                   break;
           }
       }
    }



    public void setBarrageIcon(int slot){
        ItemStack barrier = Item(Material.BARRIER, petConfigManager.getBarrierDisplayName(), petConfigManager.getBarrierLore(), slot);
        getInventory().setItem(slot, barrier);
    }


    public boolean hasPets(Player player){
        return petManager.getPlayerPets(player) != null;
    }
}
