package com.zennyel.listeners.inventory;

import com.zennyel.events.PetUnequipEvent;
import com.zennyel.manager.config.MessagesConfigManager;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.manager.config.PetFusionConfigManager;
import com.zennyel.manager.pet.PetManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import com.zennyel.utils.PetUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private PetBoxConfigManager petBoxConfigManager;
    private PetConfigManager petConfigManager;
    private final PetFusionConfigManager petFusionConfigManager;
    private final PetManager petManager;
    private final MessagesConfigManager messagesConfigManager;

    public InventoryClickListener(PetBoxConfigManager petBoxConfigManager, PetConfigManager petConfigManager, PetFusionConfigManager petFusionConfigManager, PetManager petManager, MessagesConfigManager messagesConfigManager) {
        this.petBoxConfigManager = petBoxConfigManager;
        this.petConfigManager = petConfigManager;
        this.petFusionConfigManager = petFusionConfigManager;
        this.petManager = petManager;
        this.messagesConfigManager = messagesConfigManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        handlePetFusion(event, petFusionConfigManager.getTitle().replace("&", "§"));
        handlePet(event, petConfigManager.getMenuTitle().replace("&", "§"));
    }

    public void handlePet(InventoryClickEvent event, String inventoryTitle) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (!event.getView().getTitle().equalsIgnoreCase(inventoryTitle)) {
            return;
        }

        event.setCancelled(true);

        if(clickedItem == null){
            return;
        }

        Pet pet = PetUtils.getPetByTags(clickedItem);

        if (pet == null) {
            return;
        }

        if (event.getClickedInventory().equals(player.getInventory())) {
            player.getInventory().removeItem(clickedItem.asOne());
            petManager.equipPet(player, pet);
            inventory.setItem(getSlotFromType(pet.getType()), clickedItem.asOne());
        }else if (event.getClickedInventory().equals(inventory)) {

                PetType petType = PetUtils.getPetByTags(clickedItem).getType();
                pet = petManager.getPetByType(player, petType);
                if (pet != null) {
                    petManager.removePlayerPet(player, pet);
                    Bukkit.getPluginManager().callEvent(new PetUnequipEvent(pet));
                    player.closeInventory();
                    player.getInventory().addItem(clickedItem.asOne());
                }
            }

        }

    private int getSlotFromType(PetType type) {
        switch (type) {
            case DAMAGE:
                return 10;
            case MONEY:
                return 12;
            case COIN:
                return 14;
            case EXP:
                return 16;
            default:
                return -1;
        }
    }

    public void handlePetFusion(InventoryClickEvent event, String inventoryTitle) {
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getInventory();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack barrier = petFusionConfigManager.getBarrierItem();
        ItemStack beacon = petFusionConfigManager.getConfirmationItem();

        if (!event.getView().getTitle().equalsIgnoreCase(inventoryTitle)) {
            return;
        }

        event.setCancelled(true);

        if (clickedItem == null) {
            return;
        }
        Pet pet = PetUtils.getPetByTags(clickedItem);

        if (pet != null) {
            if(event.getRawSlot() == 13){
                player.getInventory().addItem(inventory.getItem(13));
                player.closeInventory();
                return;
            }
            if (inventory.getItem(11).getType().equals(Material.BARRIER)) {
                inventory.setItem(11, clickedItem.asOne());
                player.getInventory().removeItem(clickedItem.asOne());
                return;
            }
            if (inventory.getItem(15).getType().equals(Material.BARRIER)) {
                inventory.setItem(15, clickedItem.asOne());
                player.getInventory().removeItem(clickedItem.asOne());
            }
            return;
        }



            if(event.getRawSlot() == 13){
                if(clickedItem.getType() == Material.BEACON){
                    Pet one = PetUtils.getPetByTags(inventory.getItem(11));
                    Pet two = PetUtils.getPetByTags(inventory.getItem(15));
                    if(one == null){
                        return;
                    }
                    if(two == null){
                        return;
                    }
                    if(one.getType() != two.getType()){
                        player.closeInventory();
                        player.sendMessage(messagesConfigManager.getPetFusionMessage("different-types"));
                        return;
                    }
                    if(one.getRarity() != two.getRarity()){
                        player.sendMessage(messagesConfigManager.getPetFusionMessage("different-rarities"));
                        return;
                    }

                    Pet fusedPet = new Pet(one.getType(), PetRarity.COMMON, 1, 0);
                    PetUtils.setPetMaxLevels(fusedPet);

                    switch (one.getRarity()) {
                        case LEGENDARY:
                            fusedPet.setRarity(PetRarity.LEGENDARY);
                            break;
                        case MYTHICAL:
                            fusedPet.setRarity(PetRarity.LEGENDARY);
                            break;
                        case EPIC:
                            fusedPet.setRarity(PetRarity.MYTHICAL);
                            break;
                        case RARE:
                            fusedPet.setRarity(PetRarity.EPIC);
                            break;
                        case COMMON:
                            fusedPet.setRarity(PetRarity.RARE);
                            break;
                    }
                    ItemStack fusedIs = PetUtils.getItemByPet(fusedPet);

                    inventory.setItem(11, barrier);
                    inventory.setItem(13, fusedIs);
                    inventory.setItem(15, barrier);
                }
            }
    }


}




