package BlueFridayFX;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConvertToCollections {

    private static List<Holiday> holiday;

    public static List<Holiday> getHoliday() throws Exception {
        String readData = ReadDataFromFile.readData();
        holiday = new ArrayList<>();
        int length = readData.split("\n").length;
        for(int i=0;i<length;i++)
        {
            insertData(i,readData);
        }
        return holiday;
    }

    public static Map<String,List<Holiday>> getMonthlyHoliday() throws Exception {
        Map<String,List<Holiday>> monthly = new HashMap<>();
        for(Holiday h : getHoliday())
        {
            for(int i=0;i<=12;i++)
            {
                if(h.getDate().getMonthValue() == i)
                {
                    addToMap(h.getDate().getMonth().toString(),h,monthly);
                }
            }
        }
        return monthly;
    }

    public static Map<String,List<Holiday>> getDaysHoliday() throws Exception
    {
        Map<String,List<Holiday>> weekly = new HashMap<>();
        for(Holiday h : getHoliday())
        {
            if(h.getDay().equals("Monday"))
                addToMap("Monday",h,weekly);
            if(h.getDay().equals("Tuesday"))
                addToMap("Tuesday",h,weekly);
            if(h.getDay().equals("Wednesday"))
                addToMap("Wednesday",h,weekly);
            if(h.getDay().equals("Thursday"))
                addToMap("Thursday",h,weekly);
            if(h.getDay().equals("Friday"))
                addToMap("Friday",h,weekly);
            if(h.getDay().equals("Saturday"))
                addToMap("Saturday",h,weekly);
            if(h.getDay().equals("Sunday"))
                addToMap("Sunday",h,weekly);
        }
        return weekly;
    }

    private static void addToMap(String key, Holiday holiday, Map<String,List<Holiday>> map)
    {
        if(!map.containsKey(key))
            map.put(key,new ArrayList<>(Arrays.asList(holiday)));
        else
            map.get(key).add(holiday);
    }

    private static void insertData(int i, String readData)
    {
        LocalDate date = convertToDate(readData.split("\n")[i].split(" ",2)[0]);
        String name = readData.split("\n")[i].split(" ",2)[1].split(":")[0];
        String day = readData.split("\n")[i].split(" ",2)[1].split(":")[1];
        holiday.add(new Holiday(date,name,day));
    }

    private static LocalDate convertToDate(String sDate) {
        DateTimeFormatter fromFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newDate = LocalDate.parse(sDate,fromFormat);
        return newDate;
    }

}
