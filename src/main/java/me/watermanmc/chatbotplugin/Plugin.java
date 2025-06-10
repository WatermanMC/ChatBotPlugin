package me.watermanmc.chatbotplugin;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        int pluginId = 26143;
        Metrics metrics = new Metrics(this, pluginId);

        this.getCommand("chatbot").setExecutor(new ChatbotCommand(this));
        getLogger().info("ChatBotPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatBotPlugin has been disabled.");
    }
}
