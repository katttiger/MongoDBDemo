package se.iths.cecilia.mongoDbDemo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import se.iths.cecilia.mongoDbDemo.controller.DbContext;
import se.iths.cecilia.mongoDbDemo.controller.MovieDAO;
import se.iths.cecilia.mongoDbDemo.controller.MovieDAOMongo;

public class Main {
    public static void main(String[] args) {
        MongoCollection<Document> collection = DbContext.setup();
        MovieDAO movieDAO = new MovieDAOMongo(collection);

        movieDAO.insert("A very good movie", 1983);

        movieDAO.findAll().forEach(System.out::println);
        DbContext.closeAllConnections();
    }
}
