package com.zennyel;

import com.zennyel.manager.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SavePets extends JavaPlugin {

    private PluginManager manager;

    @Override
    public void onEnable() {
        manager = new PluginManager(this);
        manager.instantiateClasses();
        manager.setupSQL();
        manager.createTables();
        manager.registerCommands();
    }

    @Override
    public void onDisable() {
    }


}
