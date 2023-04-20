package com.zennyel.GUI;

import com.zennyel.SavePets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PetFusionGUI extends CustomGUI{
    public PetFusionGUI(Inventory inventory, Player player, FileConfiguration config, SavePets instance) {
        super(inventory, player, config, instance);
    }

}
