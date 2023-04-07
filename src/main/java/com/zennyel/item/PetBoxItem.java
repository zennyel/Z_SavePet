package com.zennyel.item;

import com.zennyel.pet.PetRarity;
import org.bukkit.inventory.ItemStack;

public class PetBoxItem extends PetItem{

    private ItemStack itemStack;
    private PetRarity rarity;

    public PetBoxItem(ItemStack itemStack, PetRarity rarity) {
        this.itemStack = itemStack;
        this.rarity = rarity;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
