package com.zennyel.command;

import com.zennyel.GUI.PetsGUI;
import com.zennyel.SavePets;
import com.zennyel.manager.PetBoxConfigManager;
import com.zennyel.manager.PetConfigManager;
import com.zennyel.manager.PetManager;
import com.zennyel.manager.PluginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PetCommand implements CommandExecutor {

    private final PetConfigManager petConfigManager;
    private final SavePets instance;
    private final PetManager petManager;

    public PetCommand(PetConfigManager petConfigManager, SavePets instance, PetManager petManager) {
        this.petConfigManager = petConfigManager;
        this.instance = instance;
        this.petManager = petManager;
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
            new PetsGUI(petConfigManager.createPetMenu(), player, petConfigManager.getConfiguration(), instance, petManager);
        }


        return false;
    }
}
