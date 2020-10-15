package bluefridayfx.models;

import api.DataAPI;
import api.WriteData;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadDataFromFile {

    private ReadDataFromFile(){}

    private static final String fileName = "JSONData.txt";

    public static String readData() throws IOException, ParseException {
        Path path = getFile();
        BufferedReader br = Files.newBufferedReader(path);
        StringBuilder data = new StringBuilder();
        while (br.ready()) {
            data.append((char)br.read());
        }
        return data.toString();
    }

    private static Path getFile () throws IOException, ParseException {
        Path path = Path.of("src","main","resources").toAbsolutePath();
        if(!Files.exists(path))
            Files.createDirectories(path);
        if(!Files.exists(Path.of(path.toString(),fileName)))
            createFile(Path.of(path.toString(),fileName));
        return Path.of(path.toString(),fileName);
    }

    private static void createFile(Path filePath) throws IOException, ParseException {
        DataAPI dApi = new DataAPI("US", 2019);
        InputStream JSON = dApi.makeConnection();
        WriteData wp = new WriteData("JSONData.txt",JSON,filePath);
        wp.exportData();
    }
}
