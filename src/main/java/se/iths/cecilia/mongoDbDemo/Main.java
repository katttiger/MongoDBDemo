package se.iths.cecilia.mongoDbDemo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.iths.cecilia.mongoDbDemo.controller.MovieDAO;
import se.iths.cecilia.mongoDbDemo.controller.MovieDAOMongo;

public class Main {
    public static void main(String[] args) {
        MongoCollection<Document> collection = setup();
        MovieDAO movieDAO = new MovieDAOMongo(collection);
        movieDAO.insert("MovieDoneIn2014", 1952);

        System.out.println("\nFind all:\n");
        movieDAO.printAll(movieDAO.findAll());

        System.out.println("""
                
                ---
                
                Find one item:
                """);
        System.out.println(movieDAO.findByTitle("MovieDoneIn2014"));

        System.out.println("""
                
                ---
                
                Update movie year:
                """);
        movieDAO.updateYear("MovieDoneIn2014", 2014);

        System.out.println("""
                
                ---
                
                Delete movie:
                """);
        movieDAO.deleteByTitle("MovieDoneIn2014");

        System.out.println("""
                
                ---
                
                Find all:
                """);
        movieDAO.printAll(movieDAO.findAll());

        closeAllConnections();
    }

    private static final String uri = "mongodb+srv://this_db_user:BpT3YxkAslIsiWyw@cluster0.xsndhfu.mongodb.net/?appName=Cluster0";

    private static MongoClient client;

    public static MongoCollection<Document> setup() {
        client = connectToDatabase();
        MongoDatabase database = getDatabase(client);
        MongoCollection<Document> collection = getCollection(database);
        return collection;
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
