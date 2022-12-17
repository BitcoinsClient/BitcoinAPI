package de.bitcoinclient.bitcoinapi.twitchbot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;

public abstract class TwitchBot {

    protected TwitchClient twitchClient;
    protected final String ACCESS_TOKEN;
    protected final String getChannelId;
    protected boolean helix = true;
    protected boolean chat = true;

    protected boolean built = false;

    protected String commandPrefix = "!";

    public TwitchBot(String accessToken, String getChannelId) {
        ACCESS_TOKEN = accessToken;
        this.getChannelId = getChannelId;
        things();
    }

    public TwitchBot setACCESS_TOKEN(String ACCESS_TOKEN) {
        ACCESS_TOKEN = ACCESS_TOKEN;
        return null;
    }

    public TwitchBot setCommandPrefix(String commandPrefix) {
        commandPrefix = commandPrefix;
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
        getChannelId = getChannelId;
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
