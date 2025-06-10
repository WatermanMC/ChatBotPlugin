package me.watermanmc.chatbotplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ChatbotCommand implements CommandExecutor {

    private final Plugin plugin;
    private final MessageManager messageManager;
    private final String apiKey;
    private final String model = "gemini-1.5-flash-001";

    public ChatbotCommand(Plugin plugin) {
        this.plugin = plugin;
        this.messageManager = new MessageManager(plugin);
        this.apiKey = plugin.getConfig().getString("api-key");

        if (this.apiKey == null || this.apiKey.isEmpty() || this.apiKey.equals("YOUR_GEMINI_API_KEY")) {
            plugin.getLogger().severe("API key is not configured in config.yml. Plugin will not work.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getOnlyPlayersMessage());
            return true;
        }

        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("YOUR_GEMINI_API_KEY")) {
            sender.sendMessage(messageManager.getNotConfiguredMessage());
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(messageManager.getUsageMessage());
            return true;
        }

        String prompt = String.join(" ", args);

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String jsonBody = new JSONObject()
                    .put("contents", new JSONObject[] {
                        new JSONObject().put("parts", new JSONObject[] {
                            new JSONObject().put("text", prompt)
                        })
                    }).toString();

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                String responseText = jsonResponse.getJSONArray("candidates")
                                                  .getJSONObject(0)
                                                  .getJSONObject("content")
                                                  .getJSONArray("parts")
                                                  .getJSONObject(0)
                                                  .getString("text");
                
                String cleanedText = responseText
                        .replace("**", "")
                        .replace("*", "")
                        .replace("`", "")
                        .replace("#", "");

                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    player.sendMessage(messageManager.getFormattedResponse(cleanedText));
                });

            } catch (Exception e) {
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                     player.sendMessage(messageManager.getApiErrorMessage());
                });
                plugin.getLogger().severe("An error occurred while contacting the Gemini API: " + e.getMessage());
                e.printStackTrace();
            }
        });

        return true;
    }
}
