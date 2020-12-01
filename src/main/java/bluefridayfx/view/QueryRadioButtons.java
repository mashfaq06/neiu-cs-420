package bluefridayfx.view;

import bluefridayfx.models.Holiday;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static bluefridayfx.models.ConvertToCollectionsForHolidayData.getHoliday;

public class QueryRadioButtons {

    private RadioButton qrb1,qrb2;
    private ToggleGroup tg;
    private HBox optionsBox, displayBox;
    private Text displayText;
    private List<Holiday> holidayList;
    private List<String> data;

    public QueryRadioButtons() throws IOException {
        displayBox = new HBox();
        tg = new ToggleGroup();
        displayText = new Text("");
        holidayList = getHoliday();
        data = new ArrayList<>();
        initializeVariables();
        setUpRadioButtons();
    }

    private void initializeVariables()
    {
        qrb1 = new RadioButton("Show which day has most holidays");
        qrb2 = new RadioButton("Show which day has least holidays");
        qrb1.setToggleGroup(tg);
        qrb2.setToggleGroup(tg);
        displayBox.getChildren().add(displayText);

    }

    public HBox getOptionsBox() {
        return optionsBox;
    }

    private void setUpRadioButtons()
    {
        optionsBox = new HBox();
        optionsBox.getChildren().addAll(qrb1,qrb2);
        optionsBox.setPadding(new Insets(10,0,0,0));
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setSpacing(25);
        tg.selectedToggleProperty().addListener(new QueryRadioListener());

    }

    private class QueryRadioListener implements ChangeListener<Toggle>
    {

        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            if(newValue.equals(qrb1))
            {
                mostHolidays();
            }
            else if(newValue.equals(qrb2))
            {
                leastHolidays();
            }
        }
    }

    private void mostHolidays()
    {
        Optional<Map.Entry<String, Long>> days = holidayList
                                                .stream()
                                                .collect(Collectors.groupingBy(Holiday::getDay,Collectors.counting()))
                                                .entrySet()
                                                .stream()
                                                .max(Map.Entry.comparingByValue());

        displayText.setText(days.get().getKey());
        displayText.setFont(Font.font(30));

    }

    public HBox getDisplayBox() {
        return displayBox;
    }

    private void leastHolidays()
    {
        Optional<Map.Entry<String, Long>> days = holidayList
                .stream()
                .collect(Collectors.groupingBy(Holiday::getDay,Collectors.counting()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue());

        displayText.setText(days.get().getKey());
        displayText.setFont(Font.font(30));
    }

}


