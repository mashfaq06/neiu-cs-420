package bluefridayfx.models;

import javafx.util.StringConverter;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static bluefridayfx.models.ConvertToCollections.getHoliday;

public class Holiday {
    private LocalDate date;
    private String name;
    private String day;

    public Holiday(LocalDate date, String name, String day) {
        this.date = date;
        this.name = name;
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Holiday)) return false;
        Holiday holiday = (Holiday) o;
        return date == holiday.date &&
                Objects.equals(name, holiday.name) &&
                Objects.equals(day, holiday.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name, day);
    }

    public static class HolidayStringConverter extends StringConverter<Holiday>
    {

        @Override
        public String toString(Holiday holiday) {
            if(holiday == null)
                return null;
            else
                return holiday.getName();
        }

        @Override
        public Holiday fromString(String string) {
            try {
                for(Holiday holiday: getHoliday())
                {
                    if(holiday.getName().equals(string))
                        return holiday;
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
