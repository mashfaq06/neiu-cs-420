package bluefridayfx.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class CreateFileFromAPI {

    private static final String holidayFileName = "HolidayData.txt", countriesFileName = "CountriesHoliday.txt";

    private static Map<String,String> countryMap;

    public CreateFileFromAPI() { }

    public static Path getHolidayDataFileLocation () throws IOException {
        Path path = Path.of("build","resources","main").toAbsolutePath();
        if(!Files.exists(path))
            Files.createDirectories(path);
        if(!Files.exists(Path.of(path.toString(), holidayFileName)))
            exportHolidayDataToFile(Path.of(path.toString(), holidayFileName));
        return Path.of(path.toString(), holidayFileName);
    }

    private static Path getListOfCountriesFileLocation() throws IOException {
        Path path = Path.of("src","main","resources").toAbsolutePath();
        if(!Files.exists(path))
            Files.createDirectories(path);
        return Path.of(path.toString(), "listOfCountries.txt");
    }

    public static Path getCountriesFileLocation () throws IOException {
        Path path = Path.of("build","resources","main").toAbsolutePath();
        if(!Files.exists(path))
            Files.createDirectories(path);
        if(!Files.exists(Path.of(path.toString(), countriesFileName)))
            exportCountriesDataToFile(Path.of(path.toString(), countriesFileName));
        return Path.of(path.toString(), countriesFileName);
    }

    private static void exportHolidayDataToFile(Path filePath) throws IOException {
        DataAPI dApi = new DataAPI("US", 2019);
        InputStream JSON = dApi.makeConnection();
        WriteData wp = new WriteData(JSON,filePath);
        wp.exportData();
    }

    private static void exportCountriesDataToFile(Path filePath)
    {
        countryMap.forEach((code,country) -> {
            DataAPI dApi = new DataAPI(code, 2019);
            try {
                InputStream JSON = dApi.makeConnection();
                WriteData wp = new WriteData(JSON,filePath,country);
                wp.exportCountriesData();
            } catch (IOException e) {
            }
        });
    }

    public static void putCountriesDataInMap() throws IOException {
        countryMap = new HashMap<>();
        Path path = getListOfCountriesFileLocation();
        BufferedReader br = Files.newBufferedReader(path);
        br.lines().forEach(country -> {
            countryMap.put(country.split(",")[0], country.split(",")[1]);
        });
    }

}
