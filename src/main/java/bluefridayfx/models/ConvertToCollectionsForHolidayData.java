package bluefridayfx.models;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

public class ConvertToCollectionsForHolidayData {

    private static List<Holiday> holiday;

    private ConvertToCollectionsForHolidayData(){}

    public static List<Holiday> getHoliday() throws IOException {
        String readData = ReadDataFromFile.readHolidayData();
        holiday = new ArrayList<>();
        int length = readData.split("\n").length;
        IntStream.range(0, length).forEach(i -> insertDataInHoliday(i, readData));
        return holiday;
    }

    public static Map<Months,List<Holiday>> getMonthlyHoliday() throws IOException {
        Map<Months,List<Holiday>> monthly = new HashMap<>();
        getHoliday().forEach(h -> placeHolidaysInMonthCategory(monthly, h));
        return monthly;
    }

    private static void placeHolidaysInMonthCategory(Map<Months, List<Holiday>> monthly, Holiday h) {
        Arrays.asList(Months.values()).forEach(months -> {
            if(h.getDate().getMonthValue() == months.getMonthNo())
                addMonthsToMap(months, h, monthly);
        });
    }

    public static Map<Days,List<Holiday>> getDaysHoliday() throws IOException {
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

    private static void insertDataInHoliday(int i, String readData)
    {
        LocalDate date = convertToDate(readData.split("\n")[i].split(" ",2)[0]);
        String name = readData.split("\n")[i].split(" ",2)[1].split(":")[0];
        String day = readData.split("\n")[i].split(" ",2)[1].split(":")[1];
        holiday.add(new Holiday(date,name,day));
    }

    private static LocalDate convertToDate(String sDate) {
        DateTimeFormatter fromFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newDate = LocalDate.parse(sDate.trim(),fromFormat);
        return newDate;
    }

}
