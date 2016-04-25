package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class WeatherCollector implements Collector<Document, Document>{
    static MongoClient mongoClient = new MongoClient("localhost", 27017);
    static MongoDatabase database = mongoClient.getDatabase("test");
    static MongoCollection<org.bson.Document> weatherCollection = database.getCollection("weatherCollection");

    @Override
    public Collection<Document> mungee(Collection<Document> src) {
    	ArrayList<Document> mungedList = Lists.newArrayList();
    	
    	for(Document doc : src){
    		boolean addDoc = true;
    		for(Entry<String, Object> entry : doc.entrySet()){
    			String colName = entry.getKey();
    			String value = (String) entry.getValue();
    			
    			if(value == null || !value.equalsIgnoreCase("United States") ||  value.equals("")){
    				addDoc = false;
    				break;
    			}
    			
    			
//    			if(!value.equalsIgnoreCase("United States") || value.equals("")
//    					|| value == null){
//    				addDoc = false;
//    				break;
//    			}
    			
    		}
    		
    		if(addDoc){
    			mungedList.add(doc);
    		}
    	}
    	
        return mungedList;
    }

    @Override
    public void save(Collection<Document> data) {
        for(Document d: data){
            weatherCollection.insertOne(d);
        }
    }
}
