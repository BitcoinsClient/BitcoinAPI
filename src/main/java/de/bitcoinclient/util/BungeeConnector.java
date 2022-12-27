package de.bitcoinclient.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.bitcoinclient.BitcoinAPI;
import org.bukkit.entity.Player;

public class BungeeConnector {

    public static void connectPlayer(Player player, String serviceName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serviceName);

        player.sendPluginMessage(BitcoinAPI.getPlugin(BitcoinAPI.class), "BungeeCord", out.toByteArray());
    }
}
