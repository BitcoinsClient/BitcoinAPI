package de.bitcoinclient.bitcoinapiplugin.twitchbot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;

public class TwitchBot {

    private static TwitchClient twitchClient;
    private static String ACCESS_TOKEN;
    private static String getChannelId;
    private static boolean helix = true;
    private static boolean chat = true;

    private static boolean built = false;

    public static TwitchBot setACCESS_TOKEN(String ACCESS_TOKEN) {
        TwitchBot.ACCESS_TOKEN = ACCESS_TOKEN;
        return null;
    }

    public static TwitchBot setHelix(boolean enabled) {
        helix = enabled;
        return null;
    }

    public static TwitchBot setChat(boolean enabled) {
        chat = enabled;
        return null;
    }

    public static void setChannelId(String getChannelId) {
        TwitchBot.getChannelId = getChannelId;
    }

    public static TwitchBot build() {
        twitchClient = TwitchClientBuilder.builder()
                .withEnableHelix(helix)
                .withDefaultEventHandler(SimpleEventHandler.class)
                .withDefaultAuthToken(new OAuth2Credential("twitch", ACCESS_TOKEN))
                .withEnableChat(chat)
                .withChatAccount(new OAuth2Credential("twitch", ACCESS_TOKEN))
                .build();

        built = true;
        return null;
    }

    public static TwitchClient getClient() {
        return twitchClient;
    }

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static String getChannelId() {
        return getChannelId;
    }

    public static boolean isBuilt() {
        return built;
    }
}
