package com.zennyel.events;

import com.zennyel.pet.Pet;

public class PetExpChangeEvent extends CustomEvent{

    private Pet pet;
    private double previousExp;
    private double newExp;

    public PetExpChangeEvent(Pet pet, double previousExp, double newExp) {
        this.pet = pet;
        this.previousExp = previousExp;
        this.newExp = newExp;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public double getPreviousExp() {
        return previousExp;
    }

    public void setPreviousExp(double previousExp) {
        this.previousExp = previousExp;
    }

    public double getNewExp() {
        return newExp;
    }

    public void setNewExp(double newExp) {
        this.newExp = newExp;
    }
}
