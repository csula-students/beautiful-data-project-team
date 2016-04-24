package edu.csula.datascience.acquisition;

import org.bson.Document;

import java.util.Collection;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class CityDataCollectorApp {
    public static void main(String[] args) {
        CityDataSource source = new CityDataSource();
        Collection<Document> list = source.next();
        CityDataCollector collector = new CityDataCollector();

    }
}
