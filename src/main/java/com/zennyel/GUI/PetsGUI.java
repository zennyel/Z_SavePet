package com.zennyel.GUI;

import com.zennyel.SavePets;
import com.zennyel.manager.PetManager;
import com.zennyel.manager.PluginManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetItem;
import com.zennyel.pet.PetType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PetsGUI extends CustomGUI{
    private PetManager petManager;
    private final PetItem petItem;

    public PetsGUI(Inventory inventory, Player player, FileConfiguration config, SavePets instance, PetManager petManager) {
        super(inventory, player, config, instance);
        if (petManager == null) {
            throw new IllegalArgumentException("PetManager cannot be null.");
        }
        this.petManager = petManager;
        this.petItem = new PetItem();
    }
    @Override
    public void addItems() {
        List<Pet> petList = new ArrayList<>(petManager.getPlayerPets(getPlayer()));
        int[] slots = {9 + 2, 9 + 4, 9 + 6, 9 + 8};
        for (int i = 0; i < slots.length && i < petManager.getPlayerPets(getPlayer()).size(); i++) {
            setBarrageIcon(slots[i]);
            getInventory().setItem(slots[i], petItem.getItemByType(petList.get(i).getType(), getConfig()));
        }
    }



    public void setBarrageIcon(int slot){
        getInventory().setItem(slot, new ItemStack(Material.BARRIER));
    }


    public boolean hasPets(Player player){
        return petManager.getPlayerPets(player) != null;
    }

    @Override
    public ItemStack Item(Material material, String displayName, String description, int slotPosition) {
        return super.Item(material, displayName, description, slotPosition);
    }

}
