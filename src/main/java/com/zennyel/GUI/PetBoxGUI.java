package com.zennyel.GUI;

import com.zennyel.SavePets;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.pet.PetType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PetBoxGUI extends CustomGUI {

    private final PetBoxConfigManager petBoxConfigManager;

    public PetBoxGUI(Inventory inventory, Player player, FileConfiguration config, SavePets instance, PetBoxConfigManager petBoxConfigManager) {
        super(inventory, player, config, instance);
        this.petBoxConfigManager = petBoxConfigManager;
    }

    public void addItems() {
        List<ItemStack> itemStackList = new ArrayList<>();
        itemStackList.add(petBoxConfigManager.getItemByType(PetType.COIN));
        itemStackList.add(petBoxConfigManager.getItemByType(PetType.DAMAGE));
        itemStackList.add(petBoxConfigManager.getItemByType(PetType.MONEY));
        itemStackList.add(petBoxConfigManager.getItemByType(PetType.DAMAGE));
        int i = 9;
        for (ItemStack is : itemStackList) {
            animateItem(is, i);
            i++;
        }
    }

    public void animateItem(ItemStack itemStack, int initialSlot) {
        int numSlots = 9;
        int numLoops = 3;
        int delayTicks = 5;

        int[] slots = new int[numSlots];
        for (int i = 0; i < numSlots; i++) {
            slots[i] = i + initialSlot;
        }
        for (int j = 0; j < numLoops; j++) {
            for (int i = 0; i < numSlots; i++) {
                int slot = slots[i];
                int nextSlot = (slot + 1) % (initialSlot + numSlots);
                Bukkit.getScheduler().runTaskLater(getInstance(), () -> {
                    ItemStack currentItem = getInventory().getItem(slot);
                    if (currentItem != null && currentItem.isSimilar(itemStack)) {
                        getInventory().setItem(slot, null);
                        if (nextSlot == initialSlot) {

                            animateItem(itemStack, initialSlot);
                        }
                    }
                    if (nextSlot >= initialSlot && nextSlot < initialSlot + numSlots) {
                        getInventory().setItem(nextSlot, itemStack);
                    }
                }, delayTicks * (i + j * numSlots));
            }
            for (int i = numSlots - 1; i >= 0; i--) {
                int slot = slots[i];
                int prevSlot = (slot - 1 + initialSlot + numSlots) % (initialSlot + numSlots);
                Bukkit.getScheduler().runTaskLater(getInstance(), () -> {
                    ItemStack currentItem = getInventory().getItem(slot);
                    if (currentItem != null && currentItem.isSimilar(itemStack)) {
                        getInventory().setItem(slot, null);
                        if (prevSlot == initialSlot + numSlots - 1) {
                            animateItem(itemStack, initialSlot);
                        }
                    }
                    if (prevSlot >= initialSlot && prevSlot < initialSlot + numSlots) {
                        getInventory().setItem(prevSlot, itemStack);
                    }
                }, delayTicks * ((numSlots - 1 - i) + (j + 1) * numSlots));
            }
        }


    }
}
