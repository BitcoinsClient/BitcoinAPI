package de.bitcoinclient.apis;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CoinAPI {

    private String prefix;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private static MongoCollection<Document> players;
    public CoinAPI(String prefix) {
        this.prefix = prefix;
        createConnection();
    }
    
    public CoinAPI addCoin(OfflinePlayer player, int coins) {
        if(getPlayerDoc(player.getPlayer()) == null) {
            return null;
        }
        int aktCoins = (int) getPlayerDoc(player.getPlayer()).get("coins");
        int tier = getPlayerDoc(player.getPlayer()).getInteger("tier");
        Document query = new Document().append("UUID",  player.getUniqueId().toString().toLowerCase());

        Bson updates = null;

        if(tier == 0) {
            updates = Updates.combine(
                    Updates.set("coins", aktCoins+coins),
                    Updates.currentTimestamp("lastUpdated"));
        }
        if(tier == 1) {
            coins = coins + 10;
            updates = Updates.combine(
                    Updates.set("coins", aktCoins+coins),
                    Updates.currentTimestamp("lastUpdated"));
        }
        if(tier == 2) {
            coins = coins + 20;
            updates = Updates.combine(
                    Updates.set("coins", aktCoins+coins),
                    Updates.currentTimestamp("lastUpdated"));
        }
        if(tier == 3) {
            coins = coins + 30;
            updates = Updates.combine(
                    Updates.set("coins", aktCoins+coins),
                    Updates.currentTimestamp("lastUpdated"));
        }

        UpdateOptions options = new UpdateOptions().upsert(true);

        UpdateResult result = players.updateOne(query, updates, options);
        if(player.isOnline()) {
            player.getPlayer().sendMessage(prefix + "§7Dir wurden §e"+coins+" Coins§7 aufs Konto §ahinzugefügt§7!");
        }
        return null;
    }

    public CoinAPI removeCoins(OfflinePlayer player, int coins) {
        int aktCoins = (int) getPlayerDoc(player.getPlayer()).get("coins");
        Document query = new Document().append("UUID",  player.getUniqueId().toString().toLowerCase());

        Bson updates = Updates.combine(
                Updates.set("coins", aktCoins-coins),
                Updates.currentTimestamp("lastUpdated"));

        UpdateOptions options = new UpdateOptions().upsert(true);

        UpdateResult result = players.updateOne(query, updates, options);
        if(player.isOnline()) {
            player.getPlayer().sendMessage(prefix + "§7Dir wurden §e"+coins+" Coins§7 von deinem Konto §centfernt§7!");
        }
        return null;
    }

    public int getCoins(OfflinePlayer player) {
        if(getPlayerDoc(player.getPlayer()) == null) {
            return 0;
        }
        return getPlayerDoc(player.getPlayer()).getInteger("coins");
    }

    private void createConnection() {
        this.mongoClient = MongoClients.create("mongodb+srv://StamerHDServer:Stamer187@bitcoincloud.qu89a.mongodb.net/?retryWrites=true&w=majority");
        this.database = this.mongoClient.getDatabase("HeadServer");
        this.players = this.database.getCollection("players");
    }

    private Document getPlayerDoc(Player player) {
        if(!containsPlayer(player)) {
            return null;
        }
        AtomicReference<Document> doc = new AtomicReference<>();
        this.players.find().forEach(document -> {
            if(document.get("UUID").toString().equalsIgnoreCase(player.getUniqueId().toString().toLowerCase())) {
                doc.set(document);
            }
        });
        return doc.get();
    }

    private boolean containsPlayer(Player player) {
        if(player == null) {
            return false;
        }
        AtomicBoolean finded = new AtomicBoolean(false);
        this.players.find().forEach(document -> {
            if(document.get("UUID").toString().equalsIgnoreCase(player.getUniqueId().toString().toLowerCase())) {
                finded.set(true);
            }
        });
        return finded.get();
    }
}
