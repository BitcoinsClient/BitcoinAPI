package de.bitcoinclient.bitcoinapi.apis;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerAPI {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private static MongoCollection<Document> players;

    public PlayerAPI() {
        createConnection();
    }

    public String getTwitchID(Player player) {
        String ID = getPlayerDoc(player).getString("twitchid");
        if(ID == null) {
            return null;
        }
        return ID;
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

    private void createConnection() {
        this.mongoClient = MongoClients.create("mongodb+srv://StamerHDServer:Stamer187@bitcoincloud.qu89a.mongodb.net/?retryWrites=true&w=majority");
        this.database = this.mongoClient.getDatabase("HeadServer");
        this.players = this.database.getCollection("players");
    }
}
