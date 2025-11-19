package se.iths.cecilia.mongoDbDemo.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;

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


}
