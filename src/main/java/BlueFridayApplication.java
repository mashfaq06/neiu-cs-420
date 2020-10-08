import BlueFridayFX.*;
import BlueFridayFX.Comparators.DaysComparator;
import BlueFridayFX.Comparators.MonthsComparator;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlueFridayApplication extends Application {

    RadioButton rb1,rb2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ComboBox<Days> daysComboBox = new ComboBox<>();
        ComboBox<Months> monthsComboBox = new ComboBox<>();
        ToggleGroup tg = createRadioButton();
        ListView<Holiday> holidayList = new ListView<>();
        BorderPane pane = new BorderPane();
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue.equals(rb1))
                {
                    try {
                        Map<Days, List<Holiday>> daysHolidayMap = ConvertToCollections.getDaysHoliday();
                        daysComboBox.getItems().clear();
                        List<Days> daysList = new ArrayList<>();
                        for (Map.Entry<Days, List<Holiday>> h : daysHolidayMap.entrySet()) {
                            daysList.add(h.getKey());
                        }
                        ObservableList<Days> daysCategories= FXCollections.observableArrayList(daysList);
                        daysCategories.sort(new DaysComparator());
                        daysComboBox.getItems().addAll(daysCategories);
                        daysComboBox.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                            @Override
                            public void handle(javafx.event.ActionEvent event) {
                                Days dayName = daysComboBox.getSelectionModel().getSelectedItem();
                                ObservableList<Holiday> listOfDayHolidays = FXCollections.observableArrayList(daysHolidayMap.get(dayName));
                                holidayList.setItems(listOfDayHolidays);
                            }
                        });
                        daysComboBox.setPromptText("--Select Days--");
                        pane.setCenter(daysComboBox);
                        pane.setBottom(holidayList);
                    }catch (IOException | ParseException | NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Map<Months,List<Holiday>> monthlyHolidayMap = ConvertToCollections.getMonthlyHoliday();
                        monthsComboBox.getItems().clear();
                        List<Months> monthList = new ArrayList<>();
                        for( Map.Entry<Months,List<Holiday>> h : monthlyHolidayMap.entrySet())
                        {
                            monthList.add(h.getKey());
                        }
                        ObservableList<Months> monthCategories = FXCollections.observableArrayList(monthList);
                        monthCategories.sort(new MonthsComparator());
                        monthsComboBox.getItems().addAll(monthCategories);
                        monthsComboBox.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                            @Override
                            public void handle(javafx.event.ActionEvent event) {
                                Months monthName = monthsComboBox.getSelectionModel().getSelectedItem();
                                ObservableList<Holiday> listOfMonthlyHoliday = FXCollections.observableArrayList(monthlyHolidayMap.get(monthName));
                                holidayList.setItems(listOfMonthlyHoliday);
                            }
                        });
                        monthsComboBox.setPromptText("--Select Month--");
                        pane.setCenter(monthsComboBox);
                        pane.setBottom(holidayList);
                    } catch (IOException | ParseException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        HBox hBox = new HBox(rb1,rb2);
        hBox.setPadding(new Insets(10,0,0,0));
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setSpacing(25);
        pane.setTop(hBox);
        Scene scene = new Scene(pane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private ToggleGroup createRadioButton()
    {
        this.rb1 = new RadioButton("Categories by Days");
        this.rb2 = new RadioButton("Categories by Months");
        final ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        return tg;
    }


}
