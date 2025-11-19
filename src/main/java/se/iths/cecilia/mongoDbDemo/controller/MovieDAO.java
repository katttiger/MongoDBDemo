package se.iths.cecilia.mongoDbDemo.controller;

import org.bson.Document;

import java.util.List;

public interface MovieDAO {
    void insert(String title, int year);

    List<Document> findAll();

    Document findByTitle(String title);

    void printAll(List<Document> collection);

    void updateYear(String title, int year);

    void deleteByTitle(String title);

}
