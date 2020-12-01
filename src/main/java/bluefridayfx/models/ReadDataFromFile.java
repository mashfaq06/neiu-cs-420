package bluefridayfx.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static bluefridayfx.api.CreateFileFromAPI.*;

public class ReadDataFromFile {

    private ReadDataFromFile(){}

    public static String readHolidayData() throws IOException {
        Path holidayPath = getHolidayDataFileLocation();
        BufferedReader br = Files.newBufferedReader(holidayPath);
        return br.lines().collect(Collectors.joining("\n"));
    }

    public static String readCountriesData() throws IOException {
        putCountriesDataInMap();
        Path path = getCountriesFileLocation();
        BufferedReader br = Files.newBufferedReader(path);
        return br.lines().collect(Collectors.joining("\n"));

    }

}
