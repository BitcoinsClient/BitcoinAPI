package de.bitcoinclient.bitcoinapiplugin.twitchbot.events;

import com.github.twitch4j.helix.domain.SubscriptionList;
import de.bitcoinclient.bitcoinapiplugin.twitchbot.TwitchBot;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class SubscriptionManagerEvent {

    public static void getAll(Player player, TwitchBot twitchBot) {
        SubscriptionList resultList = twitchBot.getClient().getHelix().getSubscriptions(twitchBot.getAccessToken(),
                twitchBot.getChannelId(), null, null,100).execute();

        SubscriptionList list = twitchBot.getClient().getHelix().getSubscriptionsByUser(twitchBot.getAccessToken(), twitchBot.getChannelId(), Collections.singletonList("495218785")).execute();

        resultList.getSubscriptions().forEach(subscription -> {
            System.out.println("Subscriber: " + subscription.getUserName() + " | " + subscription.getTier());
        });

        list.getSubscriptions().forEach(subscription -> {
            player.sendMessage("Subscriber Found: " + subscription.getUserName() + " | " + subscription.getTier());
        });
    }

    public static String getTier(String userID, TwitchBot twitchBot) {
        SubscriptionList list = twitchBot.getClient().getHelix().getSubscriptionsByUser(twitchBot.getAccessToken(), twitchBot.getChannelId(), Collections.singletonList(userID)).execute();

        AtomicReference<String> tier = new AtomicReference<>("0");
        list.getSubscriptions().forEach(subscription -> {
            tier.set(subscription.getTier());
        });
        if(tier.get().equalsIgnoreCase("1000")) {
            return "1";
        }
        if(tier.get().equalsIgnoreCase("2000")) {
            return "2";
        }
        if(tier.get().equalsIgnoreCase("3000")) {
            return "3";
        }
        return "0";
    }

    public static String getUsername(String userID, TwitchBot twitchBot) {
        SubscriptionList list = twitchBot.getClient().getHelix().getSubscriptionsByUser(twitchBot.getAccessToken(), twitchBot.getChannelId(), Collections.singletonList(userID)).execute();

        AtomicReference<String> tier = new AtomicReference<>("0");
        list.getSubscriptions().forEach(subscription -> {
            tier.set(subscription.getUserName());
        });
        return tier.get();
    }
}
