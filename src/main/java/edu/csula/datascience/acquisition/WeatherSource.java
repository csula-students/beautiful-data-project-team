package edu.csula.datascience.acquisition;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class WeatherSource implements Source<Document> {
    private  int size;

    public WeatherSource(){
        size = Integer.MAX_VALUE;
    }


    @Override
    public boolean hasNext() {
        return size > 0;
    }

    @Override
    public Collection<Document> next() {
        List<String> headersList = getHeaders();
        String[] headers = new String[headersList.size()];
        for(int i = 0; i < headersList.size(); i++){
            headers[i] = headersList.get(i);
        }

        WeatherCollector collector = new WeatherCollector();
        ArrayList<Document> documentList= new ArrayList<>();

        String csvFile = "C:\\Users\\jwj96\\Downloads\\GlobalLandTemperatures\\GlobalLandTemperaturesByCountry.csv";

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
                    System.out.println("dsafasdf"  +  record.get(column));
                    doc.append(column, record.get(column));
                }
                documentList.add(doc);

                if(counter % 10000 == 0){
                    System.out.println("Saving to mongo");
                    collector.save(documentList);
                    documentList = new ArrayList<>();
                }
            }

            collector.save(documentList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getHeaders(){
        String csvFile = "C:\\Users\\jwj96\\Downloads\\GlobalLandTemperatures\\GlobalLandTemperaturesByCountry.csv";
        List<String> list = new ArrayList<>();

        try {
            Reader in = new FileReader(csvFile);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

            for(CSVRecord record: records){
                for(int i = 0; i < record.size(); i++){
                    list.add(record.get(i));
                }
                break;
            }

            System.out.println(list.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
