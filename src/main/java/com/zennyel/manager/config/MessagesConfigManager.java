package com.zennyel.manager.config;

import com.zennyel.config.MessagesConfig;
import org.bukkit.configuration.file.FileConfiguration;

public class MessagesConfigManager {

    private MessagesConfig messagesConfig;
    private FileConfiguration fileConfiguration;

    public MessagesConfigManager(MessagesConfig messagesConfig) {
        this.messagesConfig = messagesConfig;
        this.fileConfiguration = messagesConfig.getConfiguration();
    }

    public String getHelpCommandMessage(String path){
        return fileConfiguration.getString("Messages.helpCommand." + path).replace("&", "§");
    }
    public String getPetFusionMessage(String path){
        return fileConfiguration.getString("Messages.petFusion." + path).replace("&", "§");
    }

    public String getBoxCommandMessage(String path,int quantity){
        return fileConfiguration.getString("Messages.boxCommand." + path).replace("&", "§").replace("{quantity}", String.valueOf(quantity));
    }

    public String getBoxCommandMessage(String path){
        return fileConfiguration.getString("Messages.boxCommand." + path).replace("&", "§");
    }


}
