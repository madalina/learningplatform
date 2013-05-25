package com.madi.learningplatform.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.Collection;
import com.madi.learningplatform.DatabaseConnection;
import com.madi.learningplatform.exceptions.CollectionDuplicateException;
import com.madi.learningplatform.exceptions.CollectionNotFoundException;

public class CollectionService {
    private static final Logger log = LoggerFactory
            .getLogger(CollectionService.class);
    public ObservableList<Collection> collections;

    public CollectionService() {
        try {
            collections = loadCollections();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public ObservableList<Collection> loadCollections() throws IOException {
        ObservableList<Collection> result = FXCollections.observableArrayList();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseConnection.getConnection().createStatement();
            resultSet = statement.executeQuery("select * from collections");

            while (resultSet.next()) {
                result.add(new Collection(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        
        return result;
    }

    public Collection getCollection(String name) throws CollectionNotFoundException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseConnection.getConnection().createStatement();
            resultSet = statement
                    .executeQuery("select * from collections where name = " + name);

            if (resultSet.next()) {
                return new Collection(resultSet.getInt("id"), resultSet.getString("name")); 
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new CollectionNotFoundException();
    }

    public Collection getCollection(int id)
            throws CollectionNotFoundException {
        
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseConnection.getConnection().createStatement();
            resultSet = statement
                    .executeQuery("select * from collections where id = " + id);

            if (resultSet.next()) {
                return new Collection(resultSet.getInt("id"), resultSet.getString("name")); 
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        throw new CollectionNotFoundException();
    }

    public void addCollection(Collection col)
            throws CollectionDuplicateException {
//TODO
        /*
        try {
            getCollection(col.getName());
            throw new CollectionDuplicateException();
        } catch (CollectionNotFoundException ex) {
            DBCollection table = State.getDatabaseConn().getCollection(
                    "collections");
            BasicDBObject document = new BasicDBObject();
            document.put("name", col.getName());
            table.insert(document);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("name", col.getName());
            DBCursor cursor = table.find(whereQuery);
            while (cursor.hasNext()) {
                DBObject row = cursor.next();
                collections.add(new Collection((ObjectId) row.get("_id"), row
                        .get("name").toString()));
            }
            return;
        }
        */
    }

    public void deleteCollection(int id) {
        //TODO
        /*
        Collection ctr = null;
        for (int i = 0; i < collections.size(); i++) {
            if (collections.get(i).getId().equals(id))
                ctr = collections.get(i);
        }
        if (ctr != null)
            collections.remove(ctr);

        DBCollection collection = State.getDatabaseConn().getCollection(
                "collections");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);

        DBCursor cursor = collection.find(whereQuery);
        while (cursor.hasNext()) {
            collection.remove(cursor.next());
        }
        */
    }

    public void renameCollection(int id, String newName) {
        //TODO
        /*
        DBCollection collection = State.getDatabaseConn().getCollection(
                "collections");

        for (int i = 0; i < collections.size(); i++) {
            if (id.equals(collections.get(i).getId()))
                collections.get(i).setName(newName);
        }

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);

        DBCursor cursor = collection.find(whereQuery);
        if (cursor.hasNext()) {
            DBObject newObject = cursor.next();
            newObject.put("name", newName);
            collection.findAndModify(whereQuery, newObject);
        }
        */
    }

    /**
     * A collection is available for studying if it has at least 1 unlearned
     * note.
     */
    public boolean isSelectedCollectionRelevantForStudy() {
        //TODO
        return true;
        /*
        DBCollection table = State.getDatabaseConn().getCollection("notes");
        BasicDBObject queryObj = new BasicDBObject();
        queryObj.put("collection", State.getSelectedCollection().getId());
        queryObj.put("learned", false);
        DBCursor cursor = table.find(queryObj);
        return cursor.count() > 0;
        */
    }
}
