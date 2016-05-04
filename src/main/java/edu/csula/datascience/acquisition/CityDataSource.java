package edu.csula.datascience.acquisition;

import com.google.common.collect.Iterables;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class CityDataSource implements Source<Document>{
    private int size;
    String[] headers = {
            "Indicator Category",
            "Indicator",
            "Year",
            "Gender",
            "Race/ Ethnicity",
            "Value",
            "Place",
            "BCHC Requested Methodology",
            "Source",
            "Methods",
            "Notes",

    };

    public CityDataSource(){
        size = Integer.MAX_VALUE;
    }

    @Override
    public boolean hasNext() {
        return size > 0;
    }

    @Override
    public Collection<Document> next() {
        String csvFile = "/Users/andrewgarcia/Documents/Big Data Homework 2 Documents/"
        		+ "Big_Cities_Health_Data_Inventory.csv";
        CityDataCollector collector = new CityDataCollector();
        ArrayList<Document> list = new ArrayList<>();

        try{
            Reader in = new FileReader(csvFile);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader(headers).parse(in);

            int counter = 0;
            boolean firstLine = true;
            for (CSVRecord record: records) {
                counter++;
                System.out.println(counter);
                if(firstLine){
                    firstLine = false;
                    continue;
                }

                Document doc = new Document();
                for(int i = 0; i < headers.length; i++){
                    String column = headers[i];
                    //System.out.println("dsafasdf"  +  record.get(column));
                    doc.append(column, record.get(column));
                }
                list.add(doc);

                if(counter % 10000 == 0){
                    System.out.println("Saving to mongo");
                    ArrayList<Document> mungedList = (ArrayList<Document>) collector.mungee(list);
                    collector.save(mungedList);
                    list = new ArrayList<>();
                }
            }
            
            ArrayList<Document> mungedList = (ArrayList<Document>) collector.mungee(list);
            collector.save(mungedList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
