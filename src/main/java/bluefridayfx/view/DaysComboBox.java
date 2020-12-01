package bluefridayfx.view;

import bluefridayfx.models.Days;
import bluefridayfx.models.Holiday;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bluefridayfx.models.ConvertToCollectionsForHolidayData.getDaysHoliday;
import static bluefridayfx.view.FXHelper.createComboListener;
import static javafx.collections.FXCollections.observableArrayList;

public class DaysComboBox implements HolidayComboBoxes{
    private ComboBox<Days> daysComboBox;
    private Map<Days, List<Holiday>> daysMap;
    private ObservableList<Days> daysCategories;
    private List<Days> daysList;
    private ListView<Holiday> daysListView;
    private Text textBox;

    public DaysComboBox() throws IOException {
        daysMap = getDaysHoliday();
        textBox = new Text();
        addDataToList();
        daysCategories = observableArrayList(daysList);
        setComboBoxData();
    }

    public void addDataToList() {
        daysList = new ArrayList<>();
        for (Map.Entry<Days, List<Holiday>> h : daysMap.entrySet())
            daysList.add(h.getKey());
    }

    public void setComboBoxData() {
        daysListView = new ListView<>();
        daysComboBox = new ComboBox<>();
        daysComboBox.setPromptText("--Select Days--");
        daysComboBox.getItems().clear();
        daysComboBox.getItems().addAll(sortDays());
        daysComboBox.getSelectionModel().clearSelection();
        createComboListener(daysComboBox,daysListView,daysMap,textBox);
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
