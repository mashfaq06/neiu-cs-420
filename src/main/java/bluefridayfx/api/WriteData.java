package bluefridayfx.api;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WriteData extends CreateFileFromAPI {
    private final BufferedWriter bw;
    private InputStream is;
    private String country;

    public WriteData(InputStream is, Path path) throws IOException {
        this.is = is;
        this.bw = createBufferedWriter(path);
    }

    public WriteData(InputStream is, Path path, String country) throws IOException {
        this.is = is;
        this.country = country;
        this.bw = createBufferedWriter(path);

    }

    private BufferedWriter createBufferedWriter(Path path) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.CREATE ,StandardOpenOption.APPEND);
        return bw;
    }

    private List<JSONObject> obtainData (InputStream is) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        Stream<String> lines = bufferedReader.lines();
        return lines.map(line -> new JSONObject(line).getJSONArray("holidays"))
                    .map(jsonArray -> {
                        List<JSONObject> data = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            data.add(jsonArray.getJSONObject(i));
                        }
                        return data;
                    })
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
    }

    public void exportData() throws IOException {
        getData(obtainData(this.is));
        bw.flush();
        bw.close();
    }

    public void getData(List<JSONObject> array) throws IOException {
        List<String> name = array.stream().map(data -> data.get("name").toString()).collect(Collectors.toList());
        List<String> date = array.stream().map(data -> data.get("date").toString()).collect(Collectors.toList());
        List<String> day = array.stream().map(data -> data.getJSONObject("weekday").getJSONObject("date").get("name").toString()).collect(Collectors.toList());
        for (int i = 0; i < name.stream().count(); i++) {
            bw.write(date.get(i) + " " + name.get(i) + ":" + day.get(i) + "\n");
        }
    }

    public void exportCountriesData() throws IOException {
        obtainCountriesData(this.is);
        bw.flush();
        bw.close();
    }

    private void obtainCountriesData (InputStream is) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        Stream<String> lines = bufferedReader.lines();
        lines.map(line -> new JSONObject(line).getJSONArray("holidays")).forEach(jsonArray -> {
            try {
                bw.write(country + "," +jsonArray.length() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
