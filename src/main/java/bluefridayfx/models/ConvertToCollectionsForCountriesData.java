package bluefridayfx.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ConvertToCollectionsForCountriesData {

    private static List<Countries> countriesHoliday;

    private ConvertToCollectionsForCountriesData(){}

    public static List<Countries> getCountriesHoliday() throws IOException {
        String data = ReadDataFromFile.readCountriesData();
        countriesHoliday = new ArrayList<>();
        int length = data.split("\n").length;
        IntStream.range(0, length).forEach(i -> insertDataInCountry(i, data.split("\n")));
        return countriesHoliday;
    }

    private static void insertDataInCountry(int i, String[] data)
    {
        String name = data[i].split(",")[0];
        int noOfHolidays = Integer.parseInt(data[i].split(",")[1]);
        countriesHoliday.add(new Countries(name,noOfHolidays));
    }

}
