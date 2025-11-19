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

        findAllItems(movieDAO);

        addOneItem(movieDAO);
        findAllItems(movieDAO);

        findOneItem(movieDAO);
        updateMovieYear(movieDAO);

        deleteOldMovies(movieDAO);
        findAllItems(movieDAO);

        updateMovieYearsThatAreLowerThanTwothousand(movieDAO);
        findAllItems(movieDAO);

        deleteOneMovie(movieDAO);
        findAllItems(movieDAO);

        deleteAllMovies(movieDAO);
        findAllItems(movieDAO);

        DbContext.closeAllConnections();
    }

    private static void deleteOldMovies(MovieDAO movieDAO) {
        System.out.println("Delete movies produced before year 1995");
        movieDAO.deleteMoviesProducedBeforeYear1995();
    }

    private static void updateMovieYearsThatAreLowerThanTwothousand(MovieDAO movieDAO) {
        System.out.println("Updating movie years that are not larger than 2000");
        movieDAO.updateMoviesProducedBeforeYear2000();
    }

    private static void deleteAllMovies(MovieDAO movieDAO) {
        System.out.println("Delete all movies in database");
        movieDAO.deleteAllFromDatabase();
    }

    private static void deleteOneMovie(MovieDAO movieDAO) {
        System.out.println("Delete one movie in database");
        movieDAO.deleteByTitle("MovieDoneIn2014");
    }

    private static void updateMovieYear(MovieDAO movieDAO) {
        System.out.println("Updating movie year for one movie");
        movieDAO.updateYear("MovieDoneIn2014", 2014);
        System.out.println(movieDAO.findByTitle("MovieDoneIn2014"));
    }

    private static void addOneItem(MovieDAO movieDAO) {
        System.out.println("Add one movie to database");
        movieDAO.insert("MovieDoneIn2014", 1952);
    }

    private static void findOneItem(MovieDAO movieDAO) {
        System.out.println("Find one movie in database");
        System.out.println(movieDAO.findByTitle("MovieDoneIn2014"));
    }

    private static void findAllItems(MovieDAO movieDAO) {
        System.out.println("Find all movies in database");
        movieDAO.printAll(movieDAO.findAll());
    }
}
