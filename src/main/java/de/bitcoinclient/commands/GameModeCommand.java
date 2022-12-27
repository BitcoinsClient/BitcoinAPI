package de.bitcoinclient.commands;

import de.bitcoinclient.BitcoinAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameModeCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("§cDieser Command geht nur als Spieler§7!");
            return true;
        }
        Player player = ((Player) sender).getPlayer();
        if(!player.hasPermission("bitcoinapi.command.gamemode")) {
            player.sendMessage(BitcoinAPI.getPrefix() + "§7Nicht ausreichen Permission!");
            return true;
        }
        if(args.length == 0) {
            player.sendMessage(BitcoinAPI.getPrefix() + "§7Nutzte /gamemode <0/1/2/3> [player]");
            return true;
        }
        GameMode gameMode = GameMode.SURVIVAL;
        if(args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
            gameMode = GameMode.SURVIVAL;
        }
        if(args[0].equalsIgnoreCase("CREATIVE") || args[0].equalsIgnoreCase("1")) {
            gameMode = GameMode.CREATIVE;
        }
        if(args[0].equalsIgnoreCase("ADVENTURE") || args[0].equalsIgnoreCase("2")) {
            gameMode = GameMode.ADVENTURE;
        }
        if(args[0].equalsIgnoreCase("SPECTATOR") || args[0].equalsIgnoreCase("3")) {
            gameMode = GameMode.SPECTATOR;
        }
        if(args.length == 1) {
            player.setGameMode(gameMode);
            player.sendMessage(BitcoinAPI.getPrefix() + "§7Dein Spielmodus wurde auf §b§n" + gameMode.name().toLowerCase() + "§r§7 gesetzt!");
        }
        if(args.length == 2) {
            try {
                Player target = Bukkit.getPlayer(args[1]);
                target.setGameMode(gameMode);
                target.sendMessage(BitcoinAPI.getPrefix() + "§7Dein Spielmodus wurde aktualisiert!");
                player.sendMessage(BitcoinAPI.getPrefix() + "§7Du hast den Spielmodus von §d" + target.getDisplayName() + "§7 auf §d" + gameMode.name().toLowerCase() + "§7 gesetzt!");
            } catch (NullPointerException e) {
                player.sendMessage(BitcoinAPI.getPrefix() + "§7Dieser Spieler ist nicht online!");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if(!commandSender.hasPermission("bitcoinapi.command.gamemode")) {
            return list;
        }
        if(args.length == 0) return list;
        if(args.length == 1) {
            for(GameMode gameMode : GameMode.values()) {
                list.add(gameMode.name().toLowerCase());
            }
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
        }
        if(args.length == 2) {
            for(Player targets : Bukkit.getOnlinePlayers()) {
                list.add(targets.getDisplayName());
            }
        }
        ArrayList<String> completer = new ArrayList<>();
        String arg = args[args.length-1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if(s1.startsWith(arg)) {
                completer.add(s);
            }
        }
        return completer;
    }
}
