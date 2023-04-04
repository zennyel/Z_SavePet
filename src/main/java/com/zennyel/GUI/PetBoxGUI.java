package com.zennyel.GUI;

import com.zennyel.SavePets;
import com.zennyel.item.PetBox;
import com.zennyel.manager.PetBoxConfigManager;

import org.bukkit.Material;

import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PetBoxGUI extends CustomGUI{

    private PetBoxConfigManager petBoxConfigManager;

    public PetBoxGUI(Inventory inventory, Player player, FileConfiguration config, SavePets instance,PetBoxConfigManager petBoxConfigManager) {
        super(inventory, player, config, instance);
        this.petBoxConfigManager = petBoxConfigManager;
    }

    @Override
    public void addItems() {
        addGlassPane();
        PetBox petBox = new PetBox(petBoxConfigManager);
    }

    public void addGlassPane(){
        for(int i = 0; i < getInventory().getSize(); i++){
            addItemsFromConfig(i);
        }
    }

    public void addItemsFromConfig(int slot) {
        FileConfiguration config = getConfig();
        List<String> materials = config.getStringList("Menu.Display-Item.Material");
        Inventory inventory = getInventory();

        for (String materialName : materials) {
            Material material = Material.getMaterial(materialName);

            if (material == null) {
                return;
            }

            ItemStack itemStack = new ItemStack(material);
            inventory.setItem(slot, itemStack);
        }
    }

    @Override
    public ItemStack Item(Material material, String displayName, String description, int slotPosition) {
        return super.Item(material, displayName, description, slotPosition);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);
    }
}


