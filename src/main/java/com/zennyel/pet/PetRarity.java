package com.zennyel.pet;

import org.bukkit.ChatColor;

public enum PetRarity {
    COMMON("common"),
    RARE("rare"),
    EPIC("epic"),
    LEGENDARY("legendary"),
    MYTHICAL("mythical");
    private String tierName;

    PetRarity(String tierName) {
        this.tierName = tierName;
    }

    public static PetRarity getRarityByTier(String tierLine) {
        PetRarity rarity;
        switch (ChatColor.stripColor(tierLine).toLowerCase()) {
            case "common":
                rarity = PetRarity.COMMON;
                break;
            case "rare":
                rarity = PetRarity.RARE;
                break;
            case "epic":
                rarity = PetRarity.EPIC;
                break;
            case "legendary":
                rarity = PetRarity.LEGENDARY;
                break;
            case "mythical":
                rarity = PetRarity.MYTHICAL;
                break;
            default:
                rarity = null;
        }
        return rarity;
    }



    public String getTierName() {
        return tierName;
    }
}
