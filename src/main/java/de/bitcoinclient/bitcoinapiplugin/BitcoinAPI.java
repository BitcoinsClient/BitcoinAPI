package de.bitcoinclient.bitcoinapiplugin;

import de.bitcoinclient.bitcoinapiplugin.util.HttpUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BitcoinAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage("Bitcoin API wurde aktiviert!");
        Bukkit.broadcastMessage("Version: " + getVersion());
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(" ");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage("Bitcoin API wurde deaktiviert!");
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(" ");
    }

    public static String getVersion() {
        return "0.0.1-alpha";
    }

    public static String getPrefix() {
        return "§8| §9Bitcoin§7API §8●§r ";
    }
}
