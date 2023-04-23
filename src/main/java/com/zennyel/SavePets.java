package com.zennyel;

import com.zennyel.manager.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SavePets extends JavaPlugin {

    private PluginManager manager;

    @Override
    public void onEnable() {
        manager = new PluginManager(this);
        manager.createTables();
        manager.registerCommands();
        manager.registerEvents();
        manager.loadPets();
    }

    @Override
    public void onDisable() {
        manager.savePets();
    }

    public PluginManager getManager() {
        return manager;
    }
}
