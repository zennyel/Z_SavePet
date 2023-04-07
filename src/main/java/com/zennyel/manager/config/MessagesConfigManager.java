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

    public String getBoxCommandMessage(String message){
        return fileConfiguration.getString("Messages.boxCommand." + message);
    }


}
