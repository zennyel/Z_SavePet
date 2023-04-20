package com.zennyel.events;

import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;

public class PetFusionEvent extends CustomEvent{

    private Pet petOne;
    private Pet petTwo;
    private PetRarity previousRarity;
    private PetRarity newRarity;

    public PetFusionEvent(Pet petOne, Pet petTwo, PetRarity previousRarity, PetRarity newRarity) {
        this.petOne = petOne;
        this.petTwo = petTwo;
        this.previousRarity = previousRarity;
        this.newRarity = newRarity;
    }
}
