package BlueFridayApp;

import api.DataAPI;
import api.WriteData;

import java.io.InputStream;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args)
    {
        try
        {
            DataAPI dApi = new DataAPI("US", 2019);
            InputStream JSON = dApi.makeConnection();
            WriteData wp = new WriteData("JSONData.txt",JSON);
            wp.exportData(dApi.country,dApi.year);
        }
        catch (URISyntaxException e)
        {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
