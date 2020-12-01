package bluefridayfx.view;

import bluefridayfx.models.Holiday;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;

public class FXHelper {

    private FXHelper() { }

    public  static <T> void createComboListener(ComboBox<T> comboBox, ListView<Holiday> listView, Map<T, List<Holiday>> mapOfHoliday, Text textBox) {
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Holiday> listOfMonthlyHoliday = observableArrayList(mapOfHoliday.get(comboBox.getSelectionModel().getSelectedItem()));
            listView.setCellFactory(TextFieldListCell.forListView(new Holiday.HolidayStringConverter()));
            listView.setItems(listOfMonthlyHoliday);
            createListListener(listView, textBox);
        });
    }

    private static void createListListener(ListView<Holiday> listView, Text textBox)
    {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                String text = newValue.getName() + " is on " + newValue.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " which falls on " + newValue.getDay();
                textBox.setText(text);
                textBox.setVisible(true);
            }
        });
    }

}
