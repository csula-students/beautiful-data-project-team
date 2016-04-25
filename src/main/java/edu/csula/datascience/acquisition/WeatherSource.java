package edu.csula.datascience.acquisition;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import com.google.common.collect.Maps;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class WeatherSource implements Source<Document> {
	private int size;
	public static int currentIndex;
	private HashMap<Integer, CSVRecord> recordMap;

	public WeatherSource() {
		currentIndex = 0;
		recordMap = Maps.newHashMap();

		String csvFile = "/Users/andrewgarcia/Documents/"
				+ "Big Data Homework 2 Documents/GlobalLandTemperatures/GlobalLandTemperaturesByCountry.csv";

		List<String> headersList = getHeaders();
		String[] headers = new String[headersList.size()];
		for (int i = 0; i < headersList.size(); i++) {
			headers[i] = headersList.get(i);
		}

		try {
			Reader in = new FileReader(csvFile);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader(headers).parse(in);

			int temp = currentIndex;
			for (CSVRecord record : records) {
				boolean addToMap = true;
				for(int i =0 ; i < headers.length; i++){
					String column = headers[i];
					if(record.get(column) == null || record.get(column).equals("")){
						addToMap = false;
						break;
					}

				}
				
				if(addToMap){
					System.out.println("creating map, index: " + temp);
					recordMap.put(temp++, record);
				}
				
			}
			System.out.println("generated map");

			size = recordMap.size();
			System.out.println("map size: " + size);
		} catch (Exception o) {
			o.printStackTrace();
		}
	}

	@Override
	public boolean hasNext() {
		return size > 0;
	}

	@Override
	public Collection<Document> next() {
		List<String> headersList = getHeaders();
		String[] headers = new String[headersList.size()];
		for (int i = 0; i < headersList.size(); i++) {
			headers[i] = headersList.get(i);
		}

		ArrayList<Document> documentList = new ArrayList<>();

		int temp = currentIndex;
		for (; currentIndex < temp + 10000; currentIndex++) {
			size--;
			// System.out.println("current index: " + currentIndex);
			// System.out.println("size: " + size);

			CSVRecord record = recordMap.get(currentIndex);
			// System.out.println(record + " record");

			Document doc = new Document();
			for (int i = 0; i < headers.length; i++) {
				String column = headers[i];
				// System.out.println("dsafasdf" + record.get(column));
				// System.out.println("column" + column);
				// System.out.println("csv record value from column: " +
				// record.get(column));
				
				try{
					doc.append(column, record.get(column));
				}catch(Exception o){
					System.out.println("error");
				}


			}

			documentList.add(doc);

		}

		// int counter = 0;
		// boolean firstLine = true;
		//
		// counter++;
		// System.out.println(counter);
		// if(firstLine){
		// firstLine = false;
		// continue;
		// }
		//
		// Document doc = new Document();
		// for(int i = 0; i < headers.length; i++){
		// String column = headers[i];
		// System.out.println("dsafasdf" + record.get(column));
		// doc.append(column, record.get(column));
		// }
		// documentList.add(doc);

		return documentList;
	}

	public List<String> getHeaders() {
		String csvFile = "/Users/andrewgarcia/Documents/"
				+ "Big Data Homework 2 Documents/GlobalLandTemperatures/GlobalLandTemperaturesByCountry.csv";
		List<String> list = new ArrayList<>();

		try {
			Reader in = new FileReader(csvFile);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

			for (CSVRecord record : records) {
				for (int i = 0; i < record.size(); i++) {
					list.add(record.get(i));
				}
				break;
			}

			// System.out.println(list.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
