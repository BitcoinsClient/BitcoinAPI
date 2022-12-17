package de.bitcoinclient.bitcoinapiplugin.database;

public class DatabaseManager {

    public static DatabaseManager addDatabase(CreateConnection connection, String databaseName) {
        if(!connection.isConnected()) {
            return null;
        }

        connection.getMongoClient().getDatabase(databaseName);
        return null;
    }
}
