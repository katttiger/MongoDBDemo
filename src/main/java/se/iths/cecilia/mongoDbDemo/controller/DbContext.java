package se.iths.cecilia.mongoDbDemo.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DbContext {
    private static final String uri = "mongodb+srv://this_db_user:BpT3YxkAslIsiWyw@cluster0.xsndhfu.mongodb.net/?appName=Cluster0";

    private static MongoClient client;

    public static MongoCollection<Document> setup() {
        client = connectToDatabase();
        MongoDatabase database = getDatabase(client);
        MongoCollection<Document> collection = getCollection(database);
        seedData(new MovieDAOMongo(collection));
        return collection;
    }

    private static void seedData(MovieDAO movieDAO) {
        movieDAO.insert("James Bond: Golden eye", 1995);
        movieDAO.insert("Pulp Fiction", 1992);
        movieDAO.insert("The Lord of the Rings", 2001);
        movieDAO.insert("Den gode, den onde, den fule", 1966);
        movieDAO.insert("Tillbaka till framtiden", 1985);
    }

    private static MongoClient connectToDatabase() {
        MongoClient client = MongoClients.create(uri);
        return client;
    }

    private static MongoDatabase getDatabase(MongoClient client) {
        MongoDatabase database = client.getDatabase("MovieDb");
        return database;
    }

    private static MongoCollection<Document> getCollection(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("Movies");
        return collection;
    }

    public static void closeAllConnections() {
        client.close();
    }
}
