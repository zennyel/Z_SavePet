package com.zennyel.events;

import com.zennyel.pet.Pet;

public class PetExpChangeEvent extends CustomEvent{

    private Pet pet;
    private int amount;

    public PetExpChangeEvent(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
