package de.bitcoinclient.twitchbot;

import java.util.HashMap;

public class CommandManager {
    public static HashMap<String, String> commands = new HashMap<>();

    public static CommandManager addCommand(String command, String message) {
        if(commands.containsKey(command)) {
            return null;
        }
        commands.put(command, message);
        return null;
    }

    public static CommandManager updateCommand(String command, String newMessage) {
        if(!commands.containsKey(command)) {
            return null;
        }
        commands.replace(command, newMessage);
        return null;
    }

    public static CommandManager removeCommand(String command) {
        if(!commands.containsKey(command)) {
            return null;
        }
        commands.remove(command);
        return null;
    }

    public static boolean containsCommand(String command) {
        return commands.containsKey(command);
    }

    public static String getCommandMessage(String command) {
        return commands.get(command);
    }
}
