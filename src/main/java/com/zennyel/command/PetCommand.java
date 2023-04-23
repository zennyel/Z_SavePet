package com.zennyel.command;

import com.zennyel.GUI.PetFusionGUI;
import com.zennyel.GUI.PetsGUI;
import com.zennyel.SavePets;
import com.zennyel.manager.config.MessagesConfigManager;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.manager.pet.PetCandyManager;
import com.zennyel.manager.pet.PetManager;
import com.zennyel.manager.config.PetFusionConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import com.zennyel.utils.PetUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PetCommand implements CommandExecutor {

    private final PetConfigManager petConfigManager;
    private final SavePets instance;
    private final PetManager petManager;
    private final MessagesConfigManager messagesConfigManager;
    private final PetBoxConfigManager petBoxConfigManager;
    private final PetFusionConfigManager petFusionConfigManager;
    private final PetCandyManager petCandyManager;

    public PetCommand(PetConfigManager petConfigManager, SavePets instance, PetManager petManager, MessagesConfigManager messagesConfigManager, PetBoxConfigManager petBoxConfigManager, PetFusionConfigManager petFusionConfigManager, PetCandyManager petCandyManager) {
        this.petConfigManager = petConfigManager;
        this.instance = instance;
        this.petManager = petManager;
        this.messagesConfigManager = messagesConfigManager;
        this.petBoxConfigManager = petBoxConfigManager;
        this.petFusionConfigManager = petFusionConfigManager;
        this.petCandyManager = petCandyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;

        if(!player.hasPermission("pets.use")){
            player.sendMessage("");
            return false;
        }

        if (strings.length == 0){
            PetsGUI petsGUI = new PetsGUI(petConfigManager.createPetMenu(), player, petConfigManager.getConfiguration(), instance, petManager, petConfigManager);
            petsGUI.addItems(player);
            petsGUI.addPetItems(player);
            player.openInventory(petsGUI.getInventory());
            return true;
        }

        switch (strings[0]){
            case "givecandy":
                if(strings.length < 2){
                    player.sendMessage(messagesConfigManager.getGiveCandyCommandMessage("incorrect-command"));
                }
                int quantity = Integer.parseInt(strings[1]);
                for(int i = 0; i < quantity; i++) {
                    player.getInventory().addItem(petCandyManager.getPetCandy().getItemStack());
                }
                player.sendMessage(messagesConfigManager.getGiveCandyCommandMessage("candy-gived"));
                break;
            case "give":
                if (strings.length < 3) {
                    player.sendMessage(messagesConfigManager.getBoxCommandMessage("incorrect-command"));
                    return false;
                }
                PetRarity rarity = null;
                try {
                   rarity = PetRarity.valueOf(strings[1].toUpperCase());
                }catch (IllegalArgumentException e){
                    player.sendMessage(messagesConfigManager.getGiveCommandMessage("invalid-rarity"));

                }
                PetType type = null;
                try {
                  type = PetType.valueOf(strings[0].toUpperCase());
                }catch (IllegalArgumentException e){
                    player.sendMessage(messagesConfigManager.getGiveCommandMessage("invalid-type"));
                }

                int level = Integer.parseInt(strings[2]);
                Pet pet = new Pet(type, rarity, level, 0);
                ItemStack itemStack = PetUtils.getItemByPet(pet);
                player.getInventory().addItem(itemStack);
                break;

            case "help":
                player.sendMessage(messagesConfigManager.getHelpCommandMessage("help"));
                if(player.hasPermission("pets.admin")){
                    player.sendMessage(messagesConfigManager.getHelpCommandMessage("staff"));
                    return true;
                }
                break;
            case "givebox":
                if(!player.hasPermission("pets.admin")){
                    return false;
                }
            if (strings.length < 2) {
                player.sendMessage(messagesConfigManager.getBoxCommandMessage("incorrect-command"));
                return false;
            }
            try {
                quantity = Integer.parseInt(strings[1]);
                for(int i = 0; i < quantity; i++){
                    player.getInventory().addItem(petBoxConfigManager.getPetBoxItemStack());
                }
                player.sendMessage(messagesConfigManager.getBoxCommandMessage("box-gived", quantity));
            } catch (NumberFormatException e) {
                player.sendMessage(messagesConfigManager.getBoxCommandMessage("invalid-number"));
            }
            break;
            case "fusion":
                PetFusionGUI petFusionGUI = new
                PetFusionGUI(petFusionConfigManager.createInventory(), player, petFusionConfigManager.getFileConfiguration(), instance);
                petFusionGUI.addItems();
                player.openInventory(petFusionGUI.getInventory());
                break;
        }
        return false;
    }


}
