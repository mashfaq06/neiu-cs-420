package bluefridayfx.view;

import bluefridayfx.models.Days;
import bluefridayfx.models.Holiday;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.text.Text;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bluefridayfx.models.ConvertToCollections.getDaysHoliday;
import static javafx.collections.FXCollections.observableArrayList;

public class DaysComboBox{
    private ComboBox<Days> daysComboBox;
    private Map<Days, List<Holiday>> daysMap;
    private ObservableList<Days> daysCategories;
    private List<Days> daysList;
    private ListView<Holiday> daysListView;
    private Text textBox;

    public DaysComboBox() throws IOException, ParseException {
        daysMap = getDaysHoliday();
        textBox = new Text();
        addDataToList();
        daysCategories = observableArrayList(daysList);
        setDaysComboBox();
    }

    private void addDataToList() {
        daysList = new ArrayList<>();
        for (Map.Entry<Days, List<Holiday>> h : daysMap.entrySet())
            daysList.add(h.getKey());
    }

    private void setDaysComboBox() {
        daysListView = new ListView<>();
        daysComboBox = new ComboBox<>();
        daysComboBox.setPromptText("--Select Days--");
        daysComboBox.getItems().clear();
        daysComboBox.getItems().addAll(sortDays());
        daysComboBox.getSelectionModel().clearSelection();
        createDayListener();
    }

    private void createDayListener() {
        daysComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                Days dayName = daysComboBox.getSelectionModel().getSelectedItem();
                ObservableList<Holiday> listOfDayHolidays = observableArrayList(daysMap.get(dayName));
                daysListView.setCellFactory(TextFieldListCell.forListView((new Holiday.HolidayStringConverter())));
                daysListView.setItems(listOfDayHolidays);
                createListListener();
        });
    }

    private void createListListener()
    {
        daysListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    String text = newValue.getName() + " is on " + newValue.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " which falls on " + newValue.getDay();
                    textBox.setText(text);
                    textBox.setVisible(true);
                }
        });
    }

    private ObservableList<Days> sortDays()
    {
        return daysCategories.sorted((o1, o2) -> {
            if(o1.getDayNo() > o2.getDayNo())
                return 1;
            else if(o1.getDayNo() < o2.getDayNo())
                return -1;
            else
                return 0;
        });
    }

    public Text getTextBox() {
        return textBox;
    }

    public ListView<Holiday> getDaysListView() {
        return daysListView;
    }

    public ComboBox<Days> getDaysComboBox() {
        return daysComboBox;
    }

}
