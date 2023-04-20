package com.zennyel.item;

import com.zennyel.pet.Pet;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class PetCandy extends PetItem{

    private ItemStack itemStack;

    public PetCandy(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }

    public void use(Pet pet){
        pet.setLevel(pet.getLevel() + 1);
    }

}
