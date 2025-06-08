package me.watermanmc.chatbotplugin;

import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.Executor;

public class ChatbotCommand implements CommandExecutor {

    private final Plugin plugin;
    private final GenerativeModelFutures generativeModel;
    private final MessageManager messageManager;

    public ChatbotCommand(Plugin plugin, GenerativeModelFutures generativeModel, MessageManager messageManager) {
        this.plugin = plugin;
        this.generativeModel = generativeModel;
        this.messageManager = messageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(messageManager.getUsageMessage());
            return true;
        }

        String prompt = String.join(" ", args);

        Executor mainThreadExecutor = (runnable) -> Bukkit.getScheduler().runTask(plugin, runnable);

        Content content = new Content.Builder().addText(prompt).build();
        ListenableFuture<GenerateContentResponse> response = generativeModel.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String responseText = result.getText();
                player.sendMessage(messageManager.getFormattedResponse(responseText));
            }

            @Override
            public void onFailure(Throwable t) {
                player.sendMessage(messageManager.getFormattedResponse("§cError: Could not get a response."));
                plugin.getLogger().severe("Failed to get response from Gemini: " + t.getMessage());
                t.printStackTrace();
            }
        }, mainThreadExecutor);

        return true;
    }
}
