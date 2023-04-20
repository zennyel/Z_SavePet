package com.zennyel.command;

import com.zennyel.GUI.PetFusionGUI;
import com.zennyel.GUI.PetsGUI;
import com.zennyel.SavePets;
import com.zennyel.manager.config.MessagesConfigManager;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.manager.pet.PetManager;
import com.zennyel.manager.config.PetFusionConfigManager;
import com.zennyel.pet.Pet;
import com.zennyel.pet.PetRarity;
import com.zennyel.pet.PetType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PetCommand implements CommandExecutor {

    private final PetConfigManager petConfigManager;
    private final SavePets instance;
    private final PetManager petManager;
    private final MessagesConfigManager messagesConfigManager;
    private final PetBoxConfigManager petBoxConfigManager;
    private final PetFusionConfigManager petFusionConfigManager;

    public PetCommand(PetConfigManager petConfigManager, SavePets instance, PetManager petManager, MessagesConfigManager messagesConfigManager, PetBoxConfigManager petBoxConfigManager, PetFusionConfigManager petFusionConfigManager) {
        this.petConfigManager = petConfigManager;
        this.instance = instance;
        this.petManager = petManager;
        this.messagesConfigManager = messagesConfigManager;
        this.petBoxConfigManager = petBoxConfigManager;
        this.petFusionConfigManager = petFusionConfigManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            return false;
        }
        Player player = (Player) commandSender;

        if(!player.hasPermission("pets.use")){
            player.sendMessage("");
            return false;
        }

        if (strings.length == 0){
            PetsGUI petsGUI = new PetsGUI(petConfigManager.createPetMenu(), player, petConfigManager.getConfiguration(), instance, petManager, petConfigManager);
            petsGUI.addItems(player);
            player.openInventory(petsGUI.getInventory());
            return true;
        }

        switch (strings[0]){
            case "give":
                if (strings.length < 3) {
                    player.sendMessage(messagesConfigManager.getBoxCommandMessage("incorrect-command"));
                    return false;
                }
                PetRarity rarity = PetRarity.getRarityByTier(strings[1]);
                PetType type = PetType.valueOf(strings[0]);
                int level = Integer.parseInt(strings[2]);


            case "help":
                player.sendMessage(messagesConfigManager.getHelpCommandMessage("help"));
                if(player.hasPermission("pets.admin")){
                    player.sendMessage(messagesConfigManager.getHelpCommandMessage("staff"));
                    return true;
                }
                break;
            case "givebox":
            if (strings.length < 2) {
                player.sendMessage(messagesConfigManager.getBoxCommandMessage("incorrect-command"));
                return false;
            }
            try {
                int quantity = Integer.parseInt(strings[1]);
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
