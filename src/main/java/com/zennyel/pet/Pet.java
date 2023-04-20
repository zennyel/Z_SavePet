package com.zennyel.pet;

import java.util.UUID;

public class Pet {

    private PetType type;
    private PetRarity rarity;
    private int level;
    private int maxLevel;
    private double experience;
    private double maxExperience;

    public Pet(PetType type, PetRarity rarity, int level, double experience) {
        this.type = type;
        this.rarity = rarity;
        this.level = level;
        this.experience = experience;
    }

    public Pet(PetType type, int level, double experience) {
        this.type = type;
        this.level = level;
        this.experience = experience;
    }

    public Pet(PetType type, int level, double experience, UUID petId) {
        this.type = type;
        this.level = level;
        this.experience = experience;
    }

    public Pet(PetType type, PetRarity rarity, int level, double experience, UUID petId) {
        this.type = type;
        this.rarity = rarity;
        this.level = level;
        this.experience = experience;
    }

    public void setMaxExperience(double maxExperience) {
        this.maxExperience = maxExperience;
    }
    public double getMaxExperience() {
        return maxExperience;
    }

    public void setRarity(PetRarity rarity) {
        this.rarity = rarity;
    }

    public PetRarity getRarity() {
        return rarity;
    }

    public PetType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public double getExperience() {
        return experience;
    }
}
