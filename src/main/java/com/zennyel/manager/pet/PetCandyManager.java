package com.zennyel.manager.pet;

import com.zennyel.item.PetCandy;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.pet.Pet;

public class PetCandyManager {

    private PetCandy petCandy;
    private PetConfigManager petConfigManager;

    public PetCandyManager(PetConfigManager petConfigManager) {
        this.petConfigManager = petConfigManager;
        this.petCandy = new PetCandy(petConfigManager.getPetCandyItem());
    }

    public PetCandyManager(PetCandy petCandy) {
        this.petCandy = petCandy;
    }

    public PetCandy getPetCandy() {
        return petCandy;
    }
}
