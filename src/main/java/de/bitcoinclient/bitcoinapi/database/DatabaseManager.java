package de.bitcoinclient.bitcoinapi.database;

public class DatabaseManager {

    public static DatabaseManager setDatabase(CreateConnection connection, String databaseName) {
        if(!connection.isConnected()) {
            return null;
        }

        connection.getMongoClient().getDatabase(databaseName);
        return null;
    }
}
