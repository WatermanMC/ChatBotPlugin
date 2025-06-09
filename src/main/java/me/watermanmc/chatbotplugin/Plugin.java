package me.watermanmc.chatbotplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getCommand("chatbot").setExecutor(new ChatbotCommand(this));
        getLogger().info("ChatBotPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatBotPlugin has been disabled.");
    }
}
