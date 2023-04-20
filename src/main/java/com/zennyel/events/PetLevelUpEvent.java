package com.zennyel.events;

import com.zennyel.pet.Pet;

public class PetLevelUpEvent extends CustomEvent{

    private Pet pet;

    private int previousLevel;
    private int newLevel;

    public PetLevelUpEvent(Pet pet, int previousLevel, int newLevel) {
        this.pet = pet;
        this.previousLevel = previousLevel;
        this.newLevel = newLevel;
    }


    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public int getPreviousLevel() {
        return previousLevel;
    }

    public void setPreviousLevel(int previousLevel) {
        this.previousLevel = previousLevel;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(int newLevel) {
        this.newLevel = newLevel;
    }
}
