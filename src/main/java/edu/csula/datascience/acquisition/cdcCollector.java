package edu.csula.datascience.acquisition;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by jwj96 on 4/21/2016.
 */
public class cdcCollector implements Collector<Document, Document> {
    static MongoClient mongoClient = new MongoClient("localhost", 27017);
    static MongoDatabase database = mongoClient.getDatabase("test");
    static MongoCollection<Document> cdcCollection = database.getCollection("cdcCollection");


    @Override
    public Collection<Document> mungee(Collection<Document> src) {
    	List<Document> mungedList = Lists.newArrayList();
    	
    	for(Document doc : src){
    		boolean addDoc = true;
    		for(Entry<String, Object> entry : doc.entrySet()){
    			if(entry.getValue() == null || entry.getValue() == ""){
    				addDoc = false;
    				break;
    			}
    			
    		}
    		
    		if(addDoc){
    			mungedList.add(doc);
    		}
    		
    		
    	}
        return mungedList;
    }

    @Override
    public void save(Collection<Document> data) {
        for (Document d: data){
            cdcCollection.insertOne(d);
        }
    }
}
