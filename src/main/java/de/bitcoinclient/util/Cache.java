package de.bitcoinclient.util;

import com.mongodb.client.MongoDatabase;

import java.util.HashMap;

public class Cache {
    public static HashMap<String, MongoDatabase> databaseHashMap = new HashMap<>();
}
