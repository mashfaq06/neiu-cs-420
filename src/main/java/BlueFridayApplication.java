import BlueFridayFX.ConvertToCollections;
import BlueFridayFX.Holiday;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;

public class BlueFridayApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RadioButton rb1 = new RadioButton("Categories by Days");
        RadioButton rb2 = new RadioButton("Categories by Months");
        final ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        BorderPane pane = new BorderPane();
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(newValue.equals(rb1))
                {
                    try {
                        ComboBox<String> days = new ComboBox<>();
                        ListView<Holiday> display = new ListView<>();
                        Map<String, List<Holiday>> holidays = ConvertToCollections.getDaysHoliday();
                        days.getItems().clear();
                        for (Map.Entry<String, List<Holiday>> h : holidays.entrySet()) {
                            days.getItems().add(h.getKey());
                        }
                        days.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                            @Override
                            public void handle(javafx.event.ActionEvent event) {
                                String name = days.getSelectionModel().getSelectedItem();
                                display.setItems(FXCollections.observableArrayList(holidays.get(name)));
                            }
                        });
                        pane.setCenter(days);
                        pane.setBottom(display);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        ComboBox<String> months = new ComboBox<>();
                        ListView<Holiday> display = new ListView<>();
                        Map<String,List<Holiday>> holidays = ConvertToCollections.getMonthlyHoliday();
                        months.getItems().clear();
                        for( Map.Entry<String,List<Holiday>> h : holidays.entrySet())
                        {
                            months.getItems().add(h.getKey());
                        }
                        months.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                            @Override
                            public void handle(javafx.event.ActionEvent event) {
                                String name = months.getSelectionModel().getSelectedItem();
                                display.setItems(FXCollections.observableArrayList(holidays.get(name)));
                            }
                        });
                        pane.setCenter(months);
                        pane.setBottom(display);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        HBox hbox = new HBox(rb1,rb2);
        hbox.setPadding(new Insets(10,0,0,0));
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.setSpacing(25);
        pane.setTop(hbox);
        Scene scene = new Scene(pane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
