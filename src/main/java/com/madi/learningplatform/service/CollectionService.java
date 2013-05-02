package com.madi.learningplatform.service;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.madi.learningplatform.Collection;
import com.madi.learningplatform.State;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class CollectionService {
    private static final Logger log = LoggerFactory
            .getLogger(CollectionService.class);
    public ObservableList<Collection> collections;

    public CollectionService() {
        try {
            collections = loadCollections();
        } catch (JsonParseException e) {
            log.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public ObservableList<Collection> loadCollections() throws JsonParseException,
            JsonMappingException, IOException {
        DBCollection collection = State.getDatabaseConn()
                .getCollection("collections");
        ObservableList<Collection> result = FXCollections.observableArrayList();
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject row = cursor.next();
            result.add(new Collection( (ObjectId)row.get("_id"), row.get("name").toString()));
        }
        return result;
    }
    public void addCollection(Collection col) {
        DBCollection table = State.getDatabaseConn().getCollection("collections");
        BasicDBObject document = new BasicDBObject();
        document.put("name", col.getName());
        table.insert(document);
        
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", col.getName());
        DBCursor cursor = table.find(whereQuery);
        while (cursor.hasNext()) {
            DBObject row = cursor.next();
            collections.add(new Collection( (ObjectId)row.get("_id"), row.get("name").toString()));
        }
    }

    public void deleteCollection(ObjectId id) {
        Collection ctr = null;
        for(int i = 0 ; i < collections.size(); i++) {
            if(collections.get(i).getId().equals(id))
                ctr = collections.get(i);
        }
        if(ctr != null)
            collections.remove(ctr);

        DBCollection collection = State.getDatabaseConn()
                .getCollection("collections");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        
        DBCursor cursor = collection.find(whereQuery);
        while (cursor.hasNext()) {
            collection.remove(cursor.next());
        }
    }

    public void renameCollection(ObjectId id, String newName) {
        DBCollection collection = State.getDatabaseConn()
                .getCollection("collections");
        
        for(int i = 0 ; i < collections.size(); i++) {
            if(id.equals(collections.get(i).getId()))
                collections.get(i).setName(newName);
        }

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        
        DBCursor cursor = collection.find(whereQuery);
        if(cursor.hasNext())
        {
            DBObject newObject =  cursor.next();
            newObject.put("name", newName);
            collection.findAndModify(whereQuery, newObject);
        }
    }
}
