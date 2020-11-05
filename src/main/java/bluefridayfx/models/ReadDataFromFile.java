package bluefridayfx.models;

import api.DataAPI;
import api.WriteData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadDataFromFile {

    private ReadDataFromFile(){}

    private static final String fileName = "JSONData.txt";

    public static String readData() throws IOException {
        Path path = getFile();
        BufferedReader br = Files.newBufferedReader(path);
        String data = br.lines().collect(Collectors.joining("\n"));
        return data;
    }

    private static Path getFile () throws IOException {
        Path path = Path.of("src","main","resources").toAbsolutePath();
        if(!Files.exists(path))
            Files.createDirectories(path);
        if(!Files.exists(Path.of(path.toString(),fileName)))
            createFile(Path.of(path.toString(),fileName));
        return Path.of(path.toString(),fileName);
    }

    private static void createFile(Path filePath) throws IOException {
        DataAPI dApi = new DataAPI("US", 2019);
        InputStream JSON = dApi.makeConnection();
        WriteData wp = new WriteData("JSONData.txt",JSON,filePath);
        wp.exportData();
    }
}
