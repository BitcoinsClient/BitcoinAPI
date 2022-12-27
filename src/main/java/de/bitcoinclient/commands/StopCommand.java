package de.bitcoinclient.commands;

import de.bitcoinclient.BitcoinAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("§cDieser Command geht nur als Spieler§7!");
            return true;
        }
        Player player = ((Player) sender).getPlayer();
        if(!player.hasPermission("bitcoinapi.command.stop")) {
            player.sendMessage(BitcoinAPI.getPrefix() + "§7Nicht ausreichen Permission!");
            return true;
        }
        Bukkit.getOnlinePlayers().forEach(target -> {
            target.kickPlayer(BitcoinAPI.getPrefix() + "§7Der Server stoppt!");
        });
        Bukkit.shutdown();
        return false;
    }

}
