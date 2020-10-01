package BlueFridayFX;

import api.DataAPI;
import api.WriteData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadDataFromFile {

    private static String fileName = "JSONData.txt";

    public static String readData() throws Exception {
        Path path = getFile();
        BufferedReader br = Files.newBufferedReader(path);
        StringBuilder data = new StringBuilder();
        while (br.ready()) {
            data.append((char)br.read());
        }
        return data.toString();
    }

    private static Path getFile () throws Exception {
        Path path = Path.of("src","main","resources").toAbsolutePath();
        if(!Files.exists(path))
            Files.createDirectories(path);
        if(!Files.exists(Path.of(path.toString(),fileName)))
            createFile(Path.of(path.toString(),fileName));
        return Path.of(path.toString(),fileName);
    }

    private static void createFile(Path filePath) throws Exception {
        DataAPI dApi = new DataAPI("US", 2019);
        InputStream JSON = dApi.makeConnection();
        WriteData wp = new WriteData("JSONData.txt",JSON,filePath);
        wp.exportData(dApi.country,dApi.year);
    }
}
