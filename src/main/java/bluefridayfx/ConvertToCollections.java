package bluefridayfx;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConvertToCollections {

    private static List<Holiday> holiday;

    private ConvertToCollections(){}

    public static List<Holiday> getHoliday() throws IOException, ParseException {
        String readData = ReadDataFromFile.readData();
        holiday = new ArrayList<>();
        int length = readData.split("\n").length;
        for(int i=0;i<length;i++)
        {
            insertData(i,readData);
        }
        return holiday;
    }

    public static Map<Months,List<Holiday>> getMonthlyHoliday() throws IOException, ParseException {
        Map<Months,List<Holiday>> monthly = new HashMap<>();
        for(Holiday h : getHoliday())
            for(Months m : Months.values())
                if(h.getDate().getMonthValue() == m.getMonthNo())
                    addMonthsToMap(m,h,monthly);
        return monthly;
    }

    public static Map<Days,List<Holiday>> getDaysHoliday() throws IOException, ParseException {
        Map<Days,List<Holiday>> days = new HashMap<>();
        for(Holiday h : getHoliday())
            for(Days names: Days.values())
                if(h.getDay().equals(names.getDayName()))
                    addDaysToMap(names,h,days);
        return days;
    }

    private static void addMonthsToMap(Months key, Holiday holiday, Map<Months,List<Holiday>> map)
    {
        if(!map.containsKey(key))
            map.put(key,new ArrayList<>(Arrays.asList(holiday)));
        else
            map.get(key).add(holiday);
    }

    private static void addDaysToMap(Days key, Holiday holiday, Map<Days,List<Holiday>> map)
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
