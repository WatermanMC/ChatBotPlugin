package me.watermanmc.chatbotplugin;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private GenerativeModelFutures generativeModel;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String apiKey = getConfig().getString("gemini-api-key");

        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("YOUR_GEMINI_API_KEY")) {
            getLogger().severe("Gemini API key not found in config.yml. Please add it and restart the server.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        GenerativeModel gm = new GenerativeModel("gemini-pro", apiKey);
        generativeModel = GenerativeModelFutures.from(gm);

        this.messageManager = new MessageManager(this);

        this.getCommand("chatbot").setExecutor(new ChatbotCommand(this, generativeModel, messageManager));

        getLogger().info("ChatBotPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatBotPlugin has been disabled.");
    }
}
