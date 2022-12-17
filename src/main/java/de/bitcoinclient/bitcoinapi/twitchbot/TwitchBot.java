package de.bitcoinclient.bitcoinapi.twitchbot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import de.bitcoinclient.bitcoinapi.twitchbot.events.ChatEvent;

public abstract class TwitchBot {

    private TwitchClient twitchClient;
    private String ACCESS_TOKEN;
    private String getChannelId;
    private boolean helix = true;
    private boolean chat = true;

    private boolean built = false;

    private String commandPrefix = "!";

    public TwitchBot() {
        things();
    }

    public TwitchBot setACCESS_TOKEN(String ACCESS_TOKEN) {
        this.ACCESS_TOKEN = ACCESS_TOKEN;
        return null;
    }

    public TwitchBot setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
        return null;
    }

    public TwitchBot setHelix(boolean enabled) {
        helix = enabled;
        return null;
    }

    public TwitchBot setChat(boolean enabled) {
        chat = enabled;
        return null;
    }

    public void setChannelId(String getChannelId) {
        this.getChannelId = getChannelId;
    }

    public abstract void things();

    public TwitchBot build() {
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

    public TwitchClient getClient() {
        return twitchClient;
    }

    public String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public String getChannelId() {
        return getChannelId;
    }

    public boolean isBuilt() {
        return built;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }
}
