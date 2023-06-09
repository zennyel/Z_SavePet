package com.zennyel.manager;

import com.zennyel.SavePets;
import com.zennyel.command.PetCommand;
import com.zennyel.config.MessagesConfig;
import com.zennyel.config.pet.PetBoxConfig;
import com.zennyel.config.pet.PetConfig;
import com.zennyel.config.pet.PetFusionConfig;
import com.zennyel.database.PetsDB;
import com.zennyel.listeners.extra.MobCoinGainListener;
import com.zennyel.listeners.extra.MoneyChangeListener;
import com.zennyel.listeners.inventory.InventoryClickListener;
import com.zennyel.listeners.inventory.InventoryCloseListener;
import com.zennyel.listeners.player.*;
import com.zennyel.manager.config.MessagesConfigManager;
import com.zennyel.manager.config.PetBoxConfigManager;
import com.zennyel.manager.config.PetConfigManager;
import com.zennyel.manager.config.PetFusionConfigManager;
import com.zennyel.manager.pet.PetCandyManager;
import com.zennyel.manager.pet.PetInventoryManager;
import com.zennyel.manager.pet.PetManager;
import fun.lewisdev.savemobcoins.SaveMobCoinsPlugin;
import net.brcdev.shopgui.ShopGuiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class PluginManager {

    private final SavePets plugin;
    private PetsDB petsDB;
    private MessagesConfig messagesConfig;
    private PetConfig petConfig;
    private PetBoxConfig petBoxConfig;
    private PetFusionConfig petFusionConfig;
    private PetManager petManager;
    private PetConfigManager petConfigManager;
    private PetFusionConfigManager petFusionConfigManager;
    private PetBoxConfigManager petBoxConfigManager;
    private MessagesConfigManager messagesConfigManager;
    private PetInventoryManager petInventoryManager;
    private SaveMobCoinsPlugin saveMobCoinsAPI;
    private ShopGuiPlugin shopGuiPlusApi;
    private PetCandyManager petCandyManager;
    public PluginManager(SavePets plugin) {
        this.plugin = plugin;
        this.petsDB = new PetsDB(plugin);
        this.petManager = new PetManager(petsDB, plugin);
        this.messagesConfig = new MessagesConfig(plugin, "messages");
        this.petFusionConfig = new PetFusionConfig(plugin, "petfusion");
        this.petConfig = new PetConfig(plugin, "pets");
        this.petBoxConfig = new PetBoxConfig(plugin, "petbox");
        this.petConfigManager = new PetConfigManager(petConfig.getConfiguration());
        this.petFusionConfigManager = new PetFusionConfigManager(petFusionConfig.getConfiguration());
        this.messagesConfigManager = new MessagesConfigManager(messagesConfig);
        this.petBoxConfigManager = new PetBoxConfigManager(petBoxConfig.getConfiguration());
        this.petInventoryManager = new PetInventoryManager(plugin);
        this.saveMobCoinsAPI = (SaveMobCoinsPlugin) Bukkit.getPluginManager().getPlugin("SaveMobCoins");
        this.shopGuiPlusApi = (ShopGuiPlugin) Bukkit.getPluginManager().getPlugin("ShopGuiPlus");
        this.petCandyManager = new PetCandyManager(petConfigManager);
    }

    public void createTables(){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {;
            petsDB.createPetTable();
        });
    }

    public void registerCommands(){
        plugin.getCommand("pets").setExecutor(new PetCommand(petConfigManager, plugin, petManager, messagesConfigManager, petBoxConfigManager, petFusionConfigManager, petCandyManager));
    }

    public void registerEvents(){
        org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(petManager), plugin);
        pm.registerEvents(new InventoryClickListener(petBoxConfigManager, petConfigManager, petFusionConfigManager, petManager, messagesConfigManager), plugin);
        pm.registerEvents(new PlayerItemInteractListener(petBoxConfigManager,plugin), plugin);
        pm.registerEvents(new PlayerQuitListener(petManager), plugin);
        pm.registerEvents(new InventoryCloseListener(petFusionConfigManager), plugin);
        pm.registerEvents(new PlayerDamageListener(petManager), plugin);
        pm.registerEvents(new PlayerExpChangeListener(petManager), plugin);
        pm.registerEvents(new PlayerBlockPlaceListener(petBoxConfigManager), plugin);
        pm.registerEvents(new MobCoinGainListener(petManager), (Plugin) saveMobCoinsAPI);
        pm.registerEvents(new MoneyChangeListener(petManager),shopGuiPlusApi);
    }

    public void loadPets(){
        for(Player player : Bukkit.getOnlinePlayers()){
            petManager.loadPlayerPets(player);
        }
    }

    public void savePets(){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(petManager.getPlayerPets(player) == null){
                return;
            }
            petManager.savePlayerPets(player);
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
