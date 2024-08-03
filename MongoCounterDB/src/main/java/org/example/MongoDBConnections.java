package org.example;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
public class MongoDBConnections {

    // Update this with your actual connection string from MongoDB Compass
    private static final String CONNECTION_STRING = "mongodb+srv://arya5631:mydb@cluster0.vayhace.mongodb.net/";
    private static final String DATABASE_NAME = "counterDB"; // Change this to your database name
    private static final String COLLECTION_NAME = "counter";

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBConnections() {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
            collection = database.getCollection(COLLECTION_NAME);
            System.out.println("Successfully connected to MongoDB");
        } catch (Exception e) {
            System.err.println("Error connecting to MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    /*public static void main(String[] args) {
        MongoDBConnections connection = new MongoDBConnections();
        if (connection.getCollection() != null) {
            System.out.println("Successfully connected to collection: " + COLLECTION_NAME);
        }
        connection.close();
    }*/
}
