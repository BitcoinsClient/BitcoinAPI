package de.bitcoinclient.bitcoinapi.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class CreateConnection {

    private static MongoClient mongoClient;

    private static String url = "";
    private static boolean connected = false;

    public CreateConnection setURL(String url) {
        CreateConnection.url = url;
        return null;
    }

    public CreateConnection connect() {
        mongoClient = MongoClients.create(CreateConnection.url);
        connected = true;
        return null;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public boolean isConnected() {
        return connected;
    }
}
