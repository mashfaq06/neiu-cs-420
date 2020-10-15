package bluefridayfx.view;

import bluefridayfx.Holiday;
import bluefridayfx.Months;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static bluefridayfx.ConvertToCollections.getMonthlyHoliday;
import static javafx.collections.FXCollections.observableArrayList;

public class MonthComboBox {

    private ComboBox<Months> monthsComboBox;
    private Map<Months, List<Holiday>> monthMap;
    private ObservableList<Months> monthCategories;
    private List<Months> monthsList;
    private ListView<Holiday> monthsListView;
    private Text textBox;


    public MonthComboBox() throws IOException, ParseException {
        monthMap = getMonthlyHoliday();
        textBox = new Text();
        addDataToList();
        monthCategories = observableArrayList(monthsList);
        setMonthsComboBox();

    }

    private void addDataToList()
    {
        monthsList = new ArrayList<>();
        for (Map.Entry<Months, List<Holiday>> h : monthMap.entrySet())
            monthsList.add(h.getKey());
    }

    private void setMonthsComboBox() {
        monthsListView = new ListView<>();
        monthsComboBox = new ComboBox<>();
        monthsComboBox.setPromptText("--Select Month--");
        monthsComboBox.getItems().clear();
        monthsComboBox.getItems().addAll(sortMonths());
        createMonthListener();
    }

    private void createMonthListener() {
        monthsComboBox.valueProperty().addListener(new ChangeListener<Months>() {
            @Override
            public void changed(ObservableValue<? extends Months> observable, Months oldValue, Months newValue) {
                    Months monthName = monthsComboBox.getSelectionModel().getSelectedItem();
                    ObservableList<Holiday> listOfMonthlyHoliday = observableArrayList(monthMap.get(monthName));
                    monthsListView.setCellFactory(TextFieldListCell.forListView(new HolidayStringConverter()));
                    monthsListView.setItems(listOfMonthlyHoliday);
                    createListListener();
            }
        });
    }
    private void createListListener()
    {
        monthsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Holiday>() {
            @Override
            public void changed(ObservableValue<? extends Holiday> observable, Holiday oldValue, Holiday newValue) {
                    if(newValue != null) {
                        String text = newValue.getName() + " is on " + newValue.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " which falls on " + newValue.getDay();
                        textBox.setText(text);
                        textBox.setVisible(true);
                    }
            }
        });
    }

    private ObservableList<Months> sortMonths()
    {
        return monthCategories.sorted(new Comparator<Months>() {
            @Override
            public int compare(Months o1, Months o2) {
                if(o1.getMonthNo() > o2.getMonthNo())
                    return 1;
                else if(o1.getMonthNo() < o2.getMonthNo())
                    return -1;
                else
                    return 0;
            }
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
