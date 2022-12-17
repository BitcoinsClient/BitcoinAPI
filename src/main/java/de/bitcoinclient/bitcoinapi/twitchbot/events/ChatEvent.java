package de.bitcoinclient.bitcoinapi.twitchbot.events;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.bitcoinclient.bitcoinapi.BitcoinAPI;
import de.bitcoinclient.bitcoinapi.twitchbot.TwitchBot;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatEvent {
    private static boolean cooldown = false;

    public static void loadChat(TwitchBot twitchBot) {
        twitchBot.getClient().getEventManager().onEvent(ChannelMessageEvent.class, event -> {
            if(cooldown) {
                return;
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    cooldown = false;
                }
            }.runTaskLater(BitcoinAPI.getPlugin(BitcoinAPI.class),20*3);
        });
    }
}
