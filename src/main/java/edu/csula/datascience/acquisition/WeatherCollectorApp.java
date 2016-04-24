package edu.csula.datascience.acquisition;

/**
 * Created by jwj96 on 4/23/2016.
 */
public class WeatherCollectorApp {
    public static void main(String[] args) {
        WeatherSource source = new WeatherSource();
        source.next();
    }
}
