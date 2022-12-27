package de.bitcoinclient.twitchbot.events;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.bitcoinclient.BitcoinAPI;
import de.bitcoinclient.twitchbot.CommandManager;
import de.bitcoinclient.twitchbot.TwitchBot;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatEvent {
    private static boolean cooldown = false;

    public static void loadChat(TwitchBot twitchBot) {
        twitchBot.getClient().getEventManager().onEvent(ChannelMessageEvent.class, event -> {
            if(cooldown) {
                return;
            }

            String message = event.getMessage();

            if(!CommandManager.commands.containsKey(twitchBot.getCommandPrefix() + message)) {
                return;
            }

            event.reply(event.getTwitchChat(), CommandManager.getCommandMessage(message));

            new BukkitRunnable() {
                @Override
                public void run() {
                    cooldown = false;
                }
            }.runTaskLater(BitcoinAPI.getPlugin(BitcoinAPI.class),20*3);
        });
    }
}
