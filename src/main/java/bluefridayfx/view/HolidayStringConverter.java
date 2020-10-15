package bluefridayfx.view;

import bluefridayfx.models.Holiday;
import javafx.util.StringConverter;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static bluefridayfx.models.ConvertToCollections.getHoliday;

public class HolidayStringConverter extends StringConverter<Holiday>
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
