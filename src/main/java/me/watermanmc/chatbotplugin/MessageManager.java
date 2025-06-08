package me.watermanmc.chatbotplugin;

import org.bukkit.ChatColor;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class MessageManager {

    private final Plugin plugin;
    private final Properties properties = new Properties();

    public MessageManager(Plugin plugin) {
        this.plugin = plugin;
        loadProperties();
    }

    private void loadProperties() {
        try {
            File file = new File(plugin.getDataFolder(), "messages.properties");
            if (!file.exists()) {
                Files.copy(plugin.getResource("messages.properties"), file.toPath());
            }
            properties.load(new FileReader(file));
        } catch (IOException e) {
            plugin.getLogger().severe("Could not load messages.properties!");
            e.printStackTrace();
        }
    }

    private String getProperty(String key) {
        return properties.getProperty(key, "&cMessage not found: " + key);
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getProperty("prefix"));
    }

    public String getUsageMessage() {
        String message = getProperty("usage_message");
        return getPrefix() + ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getFormattedResponse(String response) {
        return getPrefix() + response;
    }
}
