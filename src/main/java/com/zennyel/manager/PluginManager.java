package com.zennyel.manager;

import com.zennyel.SavePets;
import com.zennyel.command.PetCommand;
import com.zennyel.config.MessagesConfig;
import com.zennyel.config.PetBoxConfig;
import com.zennyel.config.PetConfig;
import com.zennyel.database.PetsDB;
import com.zennyel.listeners.InventoryClick;
import com.zennyel.listeners.PlayerItemInteract;
import com.zennyel.listeners.PlayerJoin;
import com.zennyel.manager.config.MessagesConfigManager;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.manager.config.PetConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class PluginManager {

    private final SavePets plugin;
    private PetsDB petsDB;
    private MessagesConfig messagesConfig;
    private PetConfig petConfig;
    private PetBoxConfig petBoxConfig;
    private PetManager petManager;
    private PetConfigManager petConfigManager;
    private PetBoxConfigManager petBoxConfigManager;
    private MessagesConfigManager messagesConfigManager;
    private PetInventoryManager petInventoryManager;

    public PluginManager(SavePets plugin) {
        this.plugin = plugin;
        this.petsDB = new PetsDB(plugin);
        this.petManager = new PetManager(petsDB, plugin);
        this.messagesConfig = new MessagesConfig(plugin, "messages");
        this.petConfig = new PetConfig(plugin, "pets");
        this.petBoxConfig = new PetBoxConfig(plugin, "petbox");
        this.petConfigManager = new PetConfigManager(petConfig.getConfiguration());
        this.messagesConfigManager = new MessagesConfigManager(messagesConfig);
        this.petBoxConfigManager = new PetBoxConfigManager(petBoxConfig.getConfiguration());
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
        plugin.getCommand("pets").setExecutor(new PetCommand(petConfigManager, plugin, petManager, messagesConfigManager, petBoxConfigManager));
    }

    public void registerEvents(){
        org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoin(petManager), plugin);
        pm.registerEvents(new InventoryClick(petBoxConfigManager, petConfigManager), plugin);
        pm.registerEvents(new PlayerItemInteract(petBoxConfigManager,plugin), plugin);
    }

    public void loadPets(){
        for(Player player : Bukkit.getOnlinePlayers()){
            petManager.loadPlayerPets(player);
        }
    }

    public void setPetsDB(PetsDB petsDB) {
        this.petsDB = petsDB;
    }

    public void setMessagesConfig(MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
    }

    public void setPetConfig(PetConfig petConfig) {
        this.petConfig = petConfig;
    }

    public void setPetBoxConfig(PetBoxConfig petBoxConfig) {
        this.petBoxConfig = petBoxConfig;
    }

    public void setPetManager(PetManager petManager) {
        this.petManager = petManager;
    }

    public void setPetConfigManager(PetConfigManager petConfigManager) {
        this.petConfigManager = petConfigManager;
    }

    public PetBoxConfigManager getPetBoxConfigManager() {
        return petBoxConfigManager;
    }

    public void setPetBoxConfigManager(PetBoxConfigManager petBoxConfigManager) {
        this.petBoxConfigManager = petBoxConfigManager;
    }

    public MessagesConfigManager getMessagesConfigManager() {
        return messagesConfigManager;
    }

    public void setMessagesConfigManager(MessagesConfigManager messagesConfigManager) {
        this.messagesConfigManager = messagesConfigManager;
    }

    public void setPetInventoryManager(PetInventoryManager petInventoryManager) {
        this.petInventoryManager = petInventoryManager;
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
