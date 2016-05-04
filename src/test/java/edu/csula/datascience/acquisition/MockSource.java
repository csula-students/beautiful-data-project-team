package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.bson.Document;

/**
 * A mock source to provide data
 */
public class MockSource implements Source<Document> {
    int index = 0;

    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<Document> next() {
    	
    	Document one = new Document();
    	one.append("country", "Zimbabwe");
    	
    	Document two = new Document();
    	two.append("country", "United States");
    	
    	Document three = new Document();
    	three.append("country", null);
    	
    	Document four = new Document();
    	four.append("country", "United States");
    	
    	ArrayList<Document> mockDocuments= Lists.newArrayList(one,two,three,four);
    	
    	
    	return mockDocuments;
    }
}
