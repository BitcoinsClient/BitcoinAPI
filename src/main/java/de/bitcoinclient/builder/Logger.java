package de.bitcoinclient.builder;

import org.bukkit.Bukkit;

public class Logger {

    public static void sendInfo(String pluginName, String message) {
        Bukkit.getConsoleSender().sendMessage(pluginName + " | Info: " + message);
    }

    public static void sendError(String pluginName, String message) {
        Bukkit.getConsoleSender().sendMessage(pluginName + " | Error: " + message);
    }
}
