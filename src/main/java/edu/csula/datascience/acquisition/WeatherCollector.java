package edu.csula.datascience.acquisition;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collection;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class WeatherCollector implements Collector<Document, Document>{
    static MongoClient mongoClient = new MongoClient("localhost", 27017);
    static MongoDatabase database = mongoClient.getDatabase("test");
    static MongoCollection<org.bson.Document> weatherCollection = database.getCollection("weatherCollection");

    @Override
    public Collection<Document> mungee(Collection<Document> src) {
        return null;
    }

    @Override
    public void save(Collection<Document> data) {
        for(Document d: data){
            weatherCollection.insertOne(d);
        }
    }
}
