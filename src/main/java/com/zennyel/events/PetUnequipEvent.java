package com.zennyel.events;

import com.zennyel.pet.Pet;

public class PetUnequipEvent extends CustomEvent{

    private Pet pet;

    public PetUnequipEvent(Pet pet) {
        this.pet = pet;
    }


    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
