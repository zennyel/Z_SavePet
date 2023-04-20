package com.zennyel.item;

import com.zennyel.SavePets;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PetBox{

    private PetBoxConfigManager petBoxConfigManager;
    private SavePets instance;
    private Player player;
    private Inventory inventory;

    public PetBox(Player player, Inventory inventory, PetBoxConfigManager petBoxConfigManager) {
        this.instance = SavePets.getPlugin(SavePets.class);
        this.inventory = inventory;
        this.player = player;
        this.petBoxConfigManager = petBoxConfigManager;
    }

    public void addPetBox(){
        getPlayer().getInventory().removeItem(petBoxConfigManager.getPetBoxItemStack());

        List<ItemStack> itemStackList = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            itemStackList.add(petBoxConfigManager.getItemByPet(createRandomPet()));
        }

        Collections.shuffle(itemStackList);
        int[] slots = {9, 10, 11, 12, 13, 14, 15, 16, 17};
        animateItems(itemStackList, slots);

        Bukkit.getScheduler().runTaskLater(getInstance(), () -> {
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
            ItemStack itemSlot13 = getInventory().getItem(13);
            if (itemSlot13 != null) {
                getPlayer().getInventory().addItem(itemSlot13.clone());
            }
        }, 3 * 9 * 2);
    }

    public Pet createRandomPet() {
        int[] raritiesPercentages = {60, 30, 8, 1, 1};

        int randomRarity = new Random().nextInt(100);

        PetRarity rarity;
        if (randomRarity < raritiesPercentages[0]) {
            rarity = PetRarity.COMMON;
        } else if (randomRarity < raritiesPercentages[0] + raritiesPercentages[1]) {
            rarity = PetRarity.RARE;
        } else if (randomRarity < raritiesPercentages[0] + raritiesPercentages[1] + raritiesPercentages[2]) {
            rarity = PetRarity.EPIC;
        } else if (randomRarity < raritiesPercentages[0] + raritiesPercentages[1] + raritiesPercentages[2] + raritiesPercentages[3]) {
            rarity = PetRarity.LEGENDARY;
        } else {
            rarity = PetRarity.MYTHICAL;
        }

        int randomType = new Random().nextInt(4);

        PetType type;
        switch (randomType) {
            case 0:
                type = PetType.MONEY;
                break;
            case 1:
                type = PetType.EXP;
                break;
            case 2:
                type = PetType.DAMAGE;
                break;
            default:
                type = PetType.COIN;
                break;
        }

        return new Pet(type, rarity, 1, 0);
    }

    public void animateItems(List<ItemStack> itemStacks, int[] initialSlots) {
        int numSlots = 9;
        int numLoops = 2;
        int delayTicks = 3;

        Player player = getPlayer();

        for (int j = 0; j < numLoops; j++) {
            for (int k = 0; k < itemStacks.size(); k++) {
                ItemStack itemStack = itemStacks.get(k);
                int initialSlot = initialSlots[k];
                AtomicInteger nextSlot = new AtomicInteger(initialSlot);

                for (int i = initialSlot; i < initialSlot + numSlots; i++) {
                    int slot = i;
                    int finalI = i;

                    Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), () -> {
                        ItemStack currentItem = getInventory().getItem(slot);
                        if (currentItem != null && currentItem.getItemMeta().equals(itemStack.getItemMeta())) {
                            getInventory().setItem(slot, null);
                        }

                        if (finalI < 17) {
                            getInventory().setItem(nextSlot.get(), itemStack);
                            player.playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            nextSlot.getAndIncrement();
                        } else {
                            getInventory().setItem(initialSlot, itemStack);
                        }
                    }, delayTicks * (i - initialSlot + j * numSlots));
                }
            }
        }
    }

    public SavePets getInstance() {
        return instance;
    }

    public void setInstance(SavePets instance) {
        this.instance = instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}







