package com.zennyel.GUI;

import com.zennyel.SavePets;
import com.zennyel.manager.PetManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetItem;
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

    public void addItems() {
        List<Pet> petList = new ArrayList<>(petManager.getPlayerPets(getPlayer()));
        int[] slots = {9 + 1, 9 + 3, 9 + 5, 9 + 7};
        setBarrageIcon(slots[0]);
        setBarrageIcon(slots[1]);
        setBarrageIcon(slots[2]);
        setBarrageIcon(slots[3]);
        for (int i = 0; i < slots.length && i < petManager.getPlayerPets(getPlayer()).size(); i++) {
            getInventory().setItem(slots[i], petItem.getItemByType(petList.get(i).getType(), getConfig()));
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
