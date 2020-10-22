package bluefridayfx.models;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

public class ConvertToCollections {

    private static List<Holiday> holiday;

    private ConvertToCollections(){}

    public static List<Holiday> getHoliday() throws IOException, ParseException {
        String readData = ReadDataFromFile.readData();
        holiday = new ArrayList<>();
        int length = readData.split("\n").length;
        IntStream.range(0, length).forEach(i -> insertData(i, readData));
        return holiday;
    }

    public static Map<Months,List<Holiday>> getMonthlyHoliday() throws IOException, ParseException {
        Map<Months,List<Holiday>> monthly = new HashMap<>();
        getHoliday().forEach(h -> {placeHolidaysInMonthCategory(monthly, h);});
        return monthly;
    }

    private static void placeHolidaysInMonthCategory(Map<Months, List<Holiday>> monthly, Holiday h) {
        Arrays.asList(Months.values()).forEach(months -> {
            if(h.getDate().getMonthValue() == months.getMonthNo())
                addMonthsToMap(months, h, monthly);
        });
    }

    public static Map<Days,List<Holiday>> getDaysHoliday() throws IOException, ParseException {
        Map<Days,List<Holiday>> days = new HashMap<>();
        getHoliday().forEach(h -> {placeHolidayInDaysCategory(days, h);});
        return days;
    }

    private static void placeHolidayInDaysCategory(Map<Days, List<Holiday>> days, Holiday h) {
        Arrays.asList(Days.values()).forEach(day -> {
            if(h.getDay().equals(day.getDayName()))
            addDaysToMap(day, h, days);
        });
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
