package de.bitcoinclient.bitcoinapi.apis;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ErrorAPI {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> errors;

    SimpleDateFormat date = new SimpleDateFormat(
            "dd.MM.yyyy HH:mm:ss");

    public ErrorAPI() {
        createClient();
    }

    public void createClient() {
        try {
            mongoClient = MongoClients.create("mongodb+srv://error:U3Lu5rS9H3CFbB@stamernetwork.egz5zj4.mongodb.net/?retryWrites=true&w=majority");
            database = mongoClient.getDatabase("Error");
            errors = database.getCollection("errors");
            Bukkit.getConsoleSender().sendMessage("§7Es konnte eine Verbindnung zur Datenbank hergestellt werden!");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§7Es konnte keine Verbindnung zur Datenbank hergestellt werden! Fehler: ");
            e.printStackTrace();
        }
    }

    public MongoCollection<Document> getErrors() {
        return errors;
    }

    public void addError(Player player, String errorcode, String error, Color color) {
        Date currentTime = new Date();

        getErrors().insertOne(new Document()
                .append("pUUID", player.getUniqueId())
                .append("errorcode", errorcode)
                .append("error",error)
                .append("color",color.asRGB())
                .append("day",date.format(currentTime)));
    }

    public void addError(String errorcode, String error, Color color) {
        Date currentTime = new Date();

        getErrors().insertOne(new Document()
                .append("UUID", UUID.randomUUID())
                .append("errorcode", errorcode)
                .append("color",color.asRGB())
                .append("day",date.format(currentTime)));
    }

    public Document getError(String errorcode) {
        if(!containsErrorCode(errorcode)) {
            return null;
        }
        AtomicReference<Document> docum = new AtomicReference<>();
        getErrors().find().forEach(document -> {
            if(document.get("errorcode").toString().equalsIgnoreCase(errorcode)) {
                docum.set(document);
            }
        });
        return docum.get();
    }

    public boolean containsErrorCode(String errorcode) {
        if(errorcode == null) {
            return true;
        }
        AtomicBoolean finded = new AtomicBoolean(false);
        getErrors().find().forEach(document -> {
            if(document.get("errorcode").toString().equalsIgnoreCase(errorcode)) {
                finded.set(true);
            }
        });
        return finded.get();
    }
}
