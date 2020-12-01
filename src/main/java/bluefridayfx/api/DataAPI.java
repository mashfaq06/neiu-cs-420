package bluefridayfx.api;

import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataAPI {

    private String urlString = "https://holidayapi.com/v1/";
    private URL url;
    private final int timeOut = 1000;
    private HttpURLConnection connection = null;
    public String country;
    public int year;

    public DataAPI(String country, int year) {
        this.country = country;
        this.year = year;
    }

    public InputStream makeConnection() throws IOException {
        getHolidayURL(this.country, this.year);
        getResponse(this.url);
        if(connection.getResponseCode() == 200) {
            return connection.getInputStream();
        }
        else
            throw new IOException();
    }

    private void getHolidayURL(String country, int year)
    {
        try {
            this.urlString += "holidays?key=" + System.getenv("API_KEY") + "&country=" + country + "&year=" + year;
            this.url = new URL(urlString);
        }catch (MalformedURLException e){
            System.out.println(e.toString());
        }
    }

    private void getResponse(URL url) throws IOException
    {
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(timeOut);
        connection.setRequestMethod(HttpMethod.GET);
    }

}
