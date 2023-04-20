package com.zennyel.GUI;

import com.zennyel.SavePets;
import com.zennyel.item.PetBox;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PetBoxGUI extends CustomGUI {

    private final PetBoxConfigManager petBoxConfigManager;

    public PetBoxGUI(Inventory inventory, Player player, FileConfiguration config, SavePets instance, PetBoxConfigManager petBoxConfigManager) {
        super(inventory, player, config, instance);
        this.petBoxConfigManager = petBoxConfigManager;
    }

    public void addItems() {
        PetBox petBox = new PetBox(getPlayer(), getInventory(), petBoxConfigManager);
        petBox.addPetBox();
    }

}



