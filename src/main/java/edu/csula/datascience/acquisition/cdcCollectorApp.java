package edu.csula.datascience.acquisition;

import org.bson.Document;

import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class cdcCollectorApp {
    public static void main(String[] args) {
        cdcSource source = new cdcSource();
        Collection<Document> list = source.next();
        //cdcCollector collector = new cdcCollector();
        //collector.save(list);

    }
}
