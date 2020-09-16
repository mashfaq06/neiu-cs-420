package api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;

public class WriteData extends CreatePath {
    private final BufferedWriter bw;
    private InputStream is;
    private String fileName;

    public WriteData (String fileName,InputStream is) throws IOException, URISyntaxException {
        this.fileName = fileName;
        this.is = is;
        this.bw = createBufferedWriter();
    }

    private BufferedWriter createBufferedWriter() throws IOException, URISyntaxException {
        super.setPath(fileName);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(super.getPath())));
        return bw;
    }

    private JSONArray obtainData (InputStream is) throws IOException, ParseException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(bufferedReader.readLine());
        JSONArray holidayArray = (JSONArray) response.get("holidays");
        return holidayArray;
    }

    public void exportData(String country, int year) throws IOException, ParseException {
        JSONArray holidayArray = obtainData(this.is);
        bw.write("There are " + holidayArray.size() + " holidays in " + country + " for the year " + year + "\n");
        for(Object r: holidayArray)
        {
            JSONObject result = (JSONObject) r;
            bw.write( result.get("name") +" is on "+ result.get("date") + "\n");
        }
        bw.flush();
        bw.close();
    }


}
