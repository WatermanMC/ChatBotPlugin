# ChatBotPlugin 🤖
**A Minecraft plugin that uses AI to chat with players!**


ChatBotPlugin brings the power of generative AI to your Minecraft server. Players can have intelligent, dynamic conversations with an AI directly in the game chat, powered by Google's Gemini models.

* **Developer:** [WatermanMC](https://watermanmc.42web.io/)
* **Current Version:** 1.0-SNAPSHOT
* **You can download it at:** SpigotMC, Modrinth
# ✨ Features
 * 🤖 Intelligent AI Chat: Engage with a powerful AI that can answer questions, tell stories, and help you build!
 * 🤫 Private Responses: The AI responds directly and privately to the player who used the command, keeping your server chat clean.
 * ⚙️ Fully Configurable: Customize all plugin messages, including the chatbot's prefix, using the messages.properties file.
 * ⚡ Lightweight: Communicates directly with the Gemini API with minimal dependencies, ensuring your server performance is not impacted.
# 🚀 Installation and Setup
Follow these steps carefully to get the chatbot running on your server.
1. Download the Plugin
 * You can download the latest stable version from the Releases page.
 * Alternatively, get the latest build from the Actions tab (download the "ChatBotPlugin" artifact).
2. Install the Plugin
 * Place the downloaded .jar file into your server's plugins/ folder.
3. Get Your Gemini API Key
> Important: This plugin requires a free API key from Google AI Studio. You do not need a credit card for this.
>  * Go to Google AI Studio.
>  * Log in with your standard Google Account.
>  * Click "Get API key" and then "Create API key in new project".
>  * Copy the generated key. Keep it safe!
> 
4. Configure the Plugin
 * Start your server once to generate the plugins/ChatBotPlugin/ folder.
 * Open the config.yml file inside that folder.
 * Paste your Gemini API key into the file.
   # Please provide your Gemini API key from Google AI Studio
api-key: "YOUR_GEMINI_API_KEY_HERE"

5. Restart Your Server
 * Restart your Minecraft server completely. The chatbot is now ready to use!
💬 Usage
To talk to the chatbot, use the /chatbot command followed by your message.
Example:
> You type:
> /chatbot What is the sun made of?
> 
> ChatBot responds privately to you:
> [CHATBOT] The sun is primarily composed of about 74% hydrogen and 24% helium...
> 
> 
# ⚙️ Configuration
All messages and settings can be easily changed in the files located inside your plugins/ChatBotPlugin/ directory.
config.yml
This file holds your secret API key.
api-key: "YOUR_GEMINI_API_KEY_HERE"

messages.properties
This file lets you customize every message the plugin sends.
prefix=&7&l[&d&lCHATBOT&7&l] &r
usage_message=Please type &b/chatbot {your response}
only_players_message=&cThis command can only be used by players.
not_configured_message=&cThe chatbot is not configured. Please contact a server administrator.
api_error_message=&cError: Could not get a response from the AI.

⌨️ Commands
| Command | Description | Permission |
|---|---|---|
| /chatbot <message> | Sends a message to the AI and receives a private response. | (All Players) |
📄 License
This project is covered by a custom license. Please see the LICENSE file for full details on permissions and restrictions.
