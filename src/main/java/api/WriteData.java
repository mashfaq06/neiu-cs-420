package api;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WriteData extends CreatePath {
    private final BufferedWriter bw;
    private InputStream is;
    private String fileName;
    private Path path;

    public WriteData (String fileName,InputStream is) throws IOException, URISyntaxException {
        this.fileName = fileName;
        this.is = is;
        this.bw = createBufferedWriter();
    }

    public WriteData(String fileName,InputStream is, Path path) throws IOException {
        this.fileName = fileName;
        this.path = path;
        this.is = is;
        this.bw = createBufferedWriter(path);
    }

    private BufferedWriter createBufferedWriter(Path path) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(path);
        return bw;
    }

    private BufferedWriter createBufferedWriter() throws IOException, URISyntaxException {
        super.setPath(fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(super.getPath())));
        return bw;
    }

    private List<JSONObject> obtainData (InputStream is) throws IOException {
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

}
