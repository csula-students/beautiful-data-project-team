package edu.csula.datascience.acquisition;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class cdcSource implements Source<Document>{
    public static HashMap<String, HashMap> lookUpTable = new HashMap<>();
    private int size;

    public cdcSource(){
        size = Integer.MAX_VALUE;
    }

    private static String[] files = {
            "ResidentStatus.csv",
            "Education1989Revision.csv",
            "Education2003Revision.csv",
            "AgeType.csv",
            "AgeRecode52.csv",
            "AgeRecode27.csv",
            "AgeRecode12.csv",
            "InfantAgeRecode22.csv",
            "PlaceOfDeathAndDecedentsStatus.csv",
            "MaritalStatus.csv",
            "DayOfWeekOfDeath.csv",
            "Icd10Code.csv",
            "MannerOfDeath.csv",
            "MethodOfDisposition.csv",
            "ActivityCode.csv",
            "PlaceOfInjury.csv",
            "Race.csv",
            "BridgedRaceFlag.csv",
            "RaceImputationFlag.csv",
            "RaceRecode3.csv",
            "RaceRecode5.csv",
            "HispanicOrigin.csv",
            "HispanicOriginRaceRecode.csv",
            "EducationReportingFlag.csv",
            //"EntityAxisConditions.csv",
            //"RecordAxisConditions.csv",

    };

    static String[] col = {
            "Id",
            "ResidentStatus",
            "Education1989Revision",
            "Education2003Revision",
            "EducationReportingFlag",
            "MonthOfDeath",
            "Sex",
            "AgeType",
            "Age",
            "AgeSubstitutionFlag",
            "AgeRecode52",
            "AgeRecode27",
            "AgeRecode12",
            "InfantAgeRecode22",
            "PlaceOfDeathAndDecedentsStatus",
            "MaritalStatus",
            "DayOfWeekOfDeath",
            "CurrentDataYear",
            "InjuryAtWork",
            "MannerOfDeath",
            "MethodOfDisposition",
            "Autopsy",
            "ActivityCode",
            "PlaceOfInjury",
            "Icd10Code",
            "CauseRecode358",
            "CauseRecode113",
            "InfantCauseRecode130",
            "CauseRecode39",
            "NumberOfEntityAxisConditions",
            "NumberOfRecordAxisConditions",
            "Race",
            "BridgedRaceFlag",
            "RaceImputationFlag",
            "RaceRecode3",
            "RaceRecode5",
            "HispanicOrigin",
            "HispanicOriginRaceRecode",

    };

    @Override
    public boolean hasNext() {
        return size > 0;
    }

    @Override
    public Collection<Document> next() {
        Collector collector = new cdcCollector();
        Collection<Document> list = new ArrayList<>();
        createHashMaps();

        String csvFile = "/Users/andrewgarcia/Documents/DeathRecords/DeathRecords.csv";
//        File file = new File(csvFile);
//        if(file.exists()){
//        	System.out.println("exists");
//        }
//        else{
//        	System.out.println("not exists");
//        }
        
        boolean header = true;
        try {
            Reader in = new FileReader(csvFile);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader(col).parse(in);

            for (CSVRecord record : records) {
                // Skip first line
                if (header) {
                    header = false;
                    continue;
                }

                Document doc = new Document();
                for (int i = 0; i < col.length; i++) {
                    String column = col[i];
                    System.out.println(record.get("Id"));
                    if (lookUpTable.containsKey(column)) {
                        HashMap<String, String> map = lookUpTable.get(column);
                        doc.append(column, map.get(record.get(column)));
                        //System.out.println(column + ": " + map.get(record.get(column)));
                    } else {
                        doc.append(column, record.get(column));
                        //System.out.println(column + ": " + record.get(column));
                    }
                }
                
                list.add(doc);

                if(list.size() % 10000 == 0){
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
        return list;
    }

    public static void createHashMaps() {
        String csvDir = "/Users/andrewgarcia/Documents/DeathRecords/";
        String file = null;
        BufferedReader br = null;
        String line;

        for (int i = 0; i < files.length; i++) {
            file = files[i];
            String fullPath = csvDir + file;


            try {
                //System.out.println("Parsing " + file);
                br = new BufferedReader(new FileReader(fullPath));
                HashMap<String, String> infoHashMap = new HashMap<>();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] input = line.split(",");
                    infoHashMap.put(input[0], input[1]);

                }
                lookUpTable.put(file.substring(0, file.indexOf('.')), infoHashMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("Finished creating hashmaps");
    }





}
