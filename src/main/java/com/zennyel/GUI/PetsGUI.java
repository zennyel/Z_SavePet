package com.zennyel.GUI;

import com.zennyel.SavePets;
import com.zennyel.manager.pet.PetManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetItem;
import com.zennyel.pet.PetType;
import com.zennyel.utils.PetUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PetsGUI extends CustomGUI {
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
        setBarrageIcon(10);
        setBarrageIcon(12);
        setBarrageIcon(14);
        setBarrageIcon(16);
    }

    public void addPetItems(Player player) {
        List<Pet> petList = petManager.getPlayerPets(player);
        AtomicReference<ItemStack> itemStack = null;
        Bukkit.getScheduler().runTaskAsynchronously(getInstance(), ()->{
        for (Pet pet : petList) {
            itemStack.set(PetUtils.getItemByPet(pet));
            getInventory().setItem(getPetTypeSlot(pet.getType()), itemStack.get());
        }
        });
    }

    public int getPetTypeSlot(PetType petType) {
        switch (petType) {
            case EXP:
                return 16;
            case COIN:
                return 14;
            case MONEY:
                return 12;
            case DAMAGE:
                return 10;
        }
        return 0;
    }




    public void setBarrageIcon(int slot){
        ItemStack barrier = Item(Material.BARRIER, petConfigManager.getBarrierDisplayName(), petConfigManager.getBarrierLore(), slot);
        getInventory().setItem(slot, barrier);
    }


    public boolean hasPets(Player player){
        return petManager.getPlayerPets(player) != null;
    }
}
