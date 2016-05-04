package edu.csula.datascience.acquisition;

import java.util.Collection;

import org.bson.Document;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class WeatherCollectorApp {
    public static void main(String[] args) {
    	WeatherCollector collector = new WeatherCollector();
        WeatherSource source = new WeatherSource();
        
        while(source.hasNext()){
        	Collection<Document> list = source.next();
        	Collection<Document> mungedList = collector.mungee(list);
        	collector.save(mungedList);
        }
        	
    }
}
