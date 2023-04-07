package com.zennyel.manager;

import com.zennyel.SavePets;
import com.zennyel.command.PetCommand;
import com.zennyel.command.ReloadCommand;
import com.zennyel.config.MessagesConfig;
import com.zennyel.config.PetBoxConfig;
import com.zennyel.config.PetConfig;
import com.zennyel.database.PetsDB;
import com.zennyel.database.type.SQLite;
import org.bukkit.Bukkit;

import java.sql.Connection;

public class PluginManager {

    private final SavePets plugin;
    private PetsDB petsDB;
    private MessagesConfig messagesConfig;
    private PetConfig petConfig;
    private PetBoxConfig petBoxConfig;
    private PetManager petManager;
    private PetConfigManager petConfigManager;
    private PetInventoryManager petInventoryManager;

    public PluginManager(SavePets plugin) {
        this.plugin = plugin;
        instantiateClasses();
    }

    public void instantiateClasses(){
        this.petsDB = new PetsDB(plugin);
        this.petManager = new PetManager(petsDB, plugin);
        this.messagesConfig = new MessagesConfig(plugin, "messages");
        this.petConfig = new PetConfig(plugin, "pets");
        this.petBoxConfig = new PetBoxConfig(plugin, "petbox");
        this.petConfigManager = new PetConfigManager(petConfig.getConfiguration());
        this.petInventoryManager = new PetInventoryManager(plugin);
    }

    public void setupSQL(){
        createTables();
    }

    public void createTables(){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            petsDB.createPlayerPetTable();
            petsDB.createPetTable();
        });
    }

    public void registerCommands(){
        plugin.getCommand("pets").setExecutor(new PetCommand(petConfigManager, plugin, petManager));
        plugin.getCommand("reload").setExecutor(new ReloadCommand());
    }

    public SavePets getPlugin() {
        return plugin;
    }

    public PetsDB getPetsDB() {
        return petsDB;
    }

    public MessagesConfig getMessagesConfig() {
        return messagesConfig;
    }

    public PetConfig getPetConfig() {
        return petConfig;
    }

    public PetBoxConfig getPetBoxConfig() {
        return petBoxConfig;
    }

    public PetConfigManager getPetConfigManager() {
        return petConfigManager;
    }

    public PetInventoryManager getPetInventoryManager() {
        return petInventoryManager;
    }

    public PetManager getPetManager() {
        return petManager;
    }
}
