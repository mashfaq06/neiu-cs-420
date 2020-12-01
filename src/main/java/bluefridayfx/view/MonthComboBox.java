package bluefridayfx.view;

import bluefridayfx.models.Holiday;
import bluefridayfx.models.Months;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bluefridayfx.models.ConvertToCollectionsForHolidayData.getMonthlyHoliday;
import static javafx.collections.FXCollections.observableArrayList;
import static bluefridayfx.view.FXHelper.createComboListener;

public class MonthComboBox implements HolidayComboBoxes{

    private ComboBox<Months> monthsComboBox;
    private Map<Months, List<Holiday>> monthMap;
    private ObservableList<Months> monthCategories;
    private List<Months> monthsList;
    private ListView<Holiday> monthsListView;
    private Text textBox;

    public MonthComboBox() throws IOException {
        monthMap = getMonthlyHoliday();
        textBox = new Text();
        addDataToList();
        monthCategories = observableArrayList(monthsList);
        setComboBoxData();
    }

    public void addDataToList()
    {
        monthsList = new ArrayList<>();
        for (Map.Entry<Months, List<Holiday>> h : monthMap.entrySet())
            monthsList.add(h.getKey());
    }

    public void setComboBoxData() {
        monthsListView = new ListView<>();
        monthsComboBox = new ComboBox<>();
        monthsComboBox.setPromptText("--Select Month--");
        monthsComboBox.getItems().clear();
        monthsComboBox.getItems().addAll(sortMonths());
        createComboListener(monthsComboBox,monthsListView,monthMap, textBox);
    }

    private ObservableList<Months> sortMonths()
    {
        return monthCategories.sorted((o1, o2) -> {
            if(o1.getMonthNo() > o2.getMonthNo())
                return 1;
            else if(o1.getMonthNo() < o2.getMonthNo())
                return -1;
            else
                return 0;
        });
    }

    public Text getTextBox() {
        return textBox;
    }

    public ComboBox<Months> getMonthsComboBox() {
        return monthsComboBox;
    }

    public ListView<Holiday> getMonthsListView() {
        return monthsListView;
    }
}
