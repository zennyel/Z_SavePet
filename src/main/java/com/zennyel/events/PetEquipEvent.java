package com.zennyel.events;

import com.zennyel.GUI.CustomGUI;
import com.zennyel.pet.Pet;

public class PetEquipEvent extends CustomEvent {

    private Pet pet;

    public PetEquipEvent(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }
}
