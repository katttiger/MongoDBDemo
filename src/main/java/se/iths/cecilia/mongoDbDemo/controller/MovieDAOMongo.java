package se.iths.cecilia.mongoDbDemo.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MovieDAOMongo implements MovieDAO {
    private final MongoCollection<Document> collection;

    public MovieDAOMongo(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public void insert(String title, int year) {
        Document document = new Document();
        document.append("title", title);
        document.append("year", year);

        InsertOneResult result = collection.insertOne(document);

        document.clear();

        if (result.wasAcknowledged()) {
            BsonValue insertedId = result.getInsertedId();
            System.out.println("Inserted ID: " + insertedId);
        } else {
            System.out.println("Insert failed");
        }
    }

    @Override
    public List<Document> findAll() {
        FindIterable<Document> documents = collection.find();
        List<Document> documentList = new ArrayList<>();
        documents.forEach(documentList::add);
        return documentList;
    }

    public void printAll(List<Document> documentList) {
        for (Document document : documentList) {
            System.out.println(document.toJson());
        }
    }

    @Override
    public Document findByTitle(String title) {
        Bson filter = Filters.eq("title", title);
        Document document = collection.find(filter).first();
        return document;
    }

    public Document findByYear(int year) {
        Bson filter = Filters.eq("year", year);
        Document document = collection.find(filter).first();
        return document;
    }

    public void updateYear(String title, int newYear) {
        Bson filter = Filters.eq("title", title);
        Bson update = Updates.set("year", newYear);

        UpdateResult result = collection.updateOne(filter, update);

        if (result.wasAcknowledged()) {
            System.out.println("Update successful. \nNumber of documents updated: " + result.getModifiedCount());
        } else {
            System.out.println("Update failed");
        }
    }

    public void updateMoviesProducedBeforeYear2000() {
        Bson filter = Filters.lt("year", 2000);
        Bson update = Updates.set("year", 1900);
        UpdateResult result = collection.updateMany(filter, update);

        if (result.wasAcknowledged()) {
            System.out.println("Update successful. \nNumber of documents updated: " + result.getModifiedCount());
        } else {
            System.out.println("Update failed");
        }

    }

    @Override
    public void deleteMoviesProducedBeforeYear1995() {
        Bson filter = Filters.lt("year", 1995);
        DeleteResult result = collection.deleteMany(filter);
        if (result.wasAcknowledged()) {
            System.out.println("Delete successful. \nNumber of documents deleted: " + result.getDeletedCount());
        } else {
            System.out.println("Delete failed");
        }
    }

    @Override
    public void deleteAllFromDatabase() {
        Bson filter = Filters.lt("year", 3000);
        DeleteResult result = collection.deleteMany(filter);

        if (result.wasAcknowledged()) {
            System.out.println("Delete all successful.\nNumber of documents deleted: " + result.getDeletedCount());
        } else {
            System.out.println("Delete all failed");
        }
    }

    public void deleteByTitle(String title) {
        Bson filter = Filters.eq("title", title);

        DeleteResult result = collection.deleteOne(filter);

        if (result.wasAcknowledged()) {
            System.out.println("Delete successful. \nNumber of documents deleted: " + result.getDeletedCount());
        } else {
            System.out.println("Delete failed");
        }
    }
}
