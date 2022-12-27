package de.bitcoinclient.apis;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import de.bitcoinclient.BitcoinAPI;
import net.md_5.bungee.api.ChatColor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CrateAPI {
    private String preifx;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private static MongoCollection<Document> players;
    private static MongoCollection<Document> crates;
    private HashMap<String,String> crateInfos;
    //CreateTypes: COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTHICAL, EVENT
    
    public CrateAPI(String preifx) {
        this.preifx = preifx;
        this.crateInfos = new HashMap<>();
        createConnection();
        fetchCreateInfos();
        runFetchCrateTypes();
    }
    
    public CrateAPI addCreate(OfflinePlayer player, String crateType, int amount) {
        if(!this.crateInfos.containsKey(crateType.toUpperCase())) {
            return null;
        }
        if(getPlayerDoc(player) == null) {
            return null;
        }
        String[] crates = getPlayerDoc(player).getString("crates").split("#");
        int c = Integer.parseInt(crates[0]);
        int u = Integer.parseInt(crates[1]);
        int r = Integer.parseInt(crates[2]);
        int e = Integer.parseInt(crates[3]);
        int l = Integer.parseInt(crates[4]);
        int m = Integer.parseInt(crates[5]);
        int event = Integer.parseInt(crates[6]);

        if(crateType.equalsIgnoreCase("COMMON")) {
            c = c+amount;
        }
        if(crateType.equalsIgnoreCase("UNCOMMON")) {
            u = u+amount;
        }
        if(crateType.equalsIgnoreCase("RARE")) {
            r = r+amount;
        }
        if(crateType.equalsIgnoreCase("EPIC")) {
            e = e+amount;
        }
        if(crateType.equalsIgnoreCase("LEGENDARY")) {
            l = l+amount;
        }
        if(crateType.equalsIgnoreCase("MYTHICAL")) {
            m = m+amount;
        }
        if(crateType.equalsIgnoreCase("EVENT")) {
            event = event+amount;
        }

        Document query = new Document().append("UUID" ,player.getUniqueId().toString());

        Bson bson = Updates.combine(
                Updates.set("crates", c+"#"+u+"#"+r+"#"+e+"#"+l+"#"+m+"#"+event),
                Updates.currentTimestamp("lastUpdate")
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        UpdateResult result = players.updateOne(query,bson,options);
        return null;
    }

    public CrateAPI removeCreate(OfflinePlayer player, String crateType, int amount) {
        if(!this.crateInfos.containsKey(crateType.toUpperCase())) {
            return null;
        }
        if(getPlayerDoc(player) == null) {
            return null;
        }
        String[] crates = getPlayerDoc(player).getString("crates").split("#");
        int c = Integer.parseInt(crates[0]);
        int u = Integer.parseInt(crates[1]);
        int r = Integer.parseInt(crates[2]);
        int e = Integer.parseInt(crates[3]);
        int l = Integer.parseInt(crates[4]);
        int m = Integer.parseInt(crates[5]);
        int event = Integer.parseInt(crates[6]);

        if(crateType.equalsIgnoreCase("COMMON")) {
            c = c-amount;
        }
        if(crateType.equalsIgnoreCase("UNCOMMON")) {
            u = u-amount;
        }
        if(crateType.equalsIgnoreCase("RARE")) {
            r = r-amount;
        }
        if(crateType.equalsIgnoreCase("EPIC")) {
            e = e-amount;
        }
        if(crateType.equalsIgnoreCase("LEGENDARY")) {
            l = l-amount;
        }
        if(crateType.equalsIgnoreCase("MYTHICAL")) {
            m = m-amount;
        }
        if(crateType.equalsIgnoreCase("EVENT")) {
            event = event-amount;
        }

        Document query = new Document().append("UUID" ,player.getUniqueId().toString());

        Bson bson = Updates.combine(
                Updates.set("crates", c+"#"+u+"#"+r+"#"+e+"#"+l+"#"+m+"#"+event),
                Updates.currentTimestamp("lastUpdate")
        );

        UpdateOptions options = new UpdateOptions().upsert(true);

        UpdateResult result = players.updateOne(query,bson,options);
        return null;
    }

    private void createConnection() {
        this.mongoClient = MongoClients.create("mongodb+srv://StamerHDServer:Stamer187@bitcoincloud.qu89a.mongodb.net/?retryWrites=true&w=majority");
        this.database = this.mongoClient.getDatabase("HeadServer");
        players = this.database.getCollection("players");
        crates = this.database.getCollection("crates");
    }
    
    private void fetchCreateInfos() {
        if(this.crateInfos.size() != 0) {
            this.crateInfos.clear();
        }
        crates.find().forEach(document -> {
            this.crateInfos.put(document.getString("name"),
                    ChatColor.of(document.getString("IGNHex")) + document.getString("IGNName"));
        });
    }

    private Document getPlayerDoc(OfflinePlayer player) {
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

    private boolean containsPlayer(OfflinePlayer player) {
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

    private void runFetchCrateTypes() {
        new Thread(() -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    fetchCreateInfos();
                }
            }.runTaskTimer(BitcoinAPI.getPlugin(BitcoinAPI.class),0,20*60*15);
        }).start();
    }
}
