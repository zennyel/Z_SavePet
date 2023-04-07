package com.zennyel.command;

import com.zennyel.GUI.PetsGUI;
import com.zennyel.SavePets;
import com.zennyel.manager.config.MessagesConfigManager;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.manager.PetManager;
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

    public PetCommand(PetConfigManager petConfigManager, SavePets instance, PetManager petManager, MessagesConfigManager messagesConfigManager, PetBoxConfigManager petBoxConfigManager) {
        this.petConfigManager = petConfigManager;
        this.instance = instance;
        this.petManager = petManager;
        this.messagesConfigManager = messagesConfigManager;
        this.petBoxConfigManager = petBoxConfigManager;
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
            petsGUI.addItems();
            player.openInventory(petsGUI.getInventory());
            return true;
        }

        if (strings[0].equals("givebox")) {
            if (strings.length < 2) {
                player.sendMessage(messagesConfigManager.getBoxCommandMessage("incorrect-command"));
                return false;
            }
            try {
                int quantity = Integer.parseInt(strings[1]);
                for(int i = 0; i < quantity; i++){
                    player.getInventory().addItem(petBoxConfigManager.getPetBoxItemStack());
                }
                player.sendMessage("???");
            } catch (NumberFormatException e) {
                player.sendMessage(messagesConfigManager.getBoxCommandMessage("invalid-number"));
            }
        }


        return false;
    }
}
