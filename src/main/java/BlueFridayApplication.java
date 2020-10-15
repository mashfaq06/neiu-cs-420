import bluefridayfx.models.Holiday;
import bluefridayfx.view.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class BlueFridayApplication extends Application {

    private RadioButton rb1,rb2;
    private MonthComboBox monthCombo;
    private DaysComboBox daysCombo;
    private BorderPane pane;
    private HBox hBox;
    private VBox vBox;

    public BlueFridayApplication()
    {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            initializeVariables();
            setHBox();
            vBox.getChildren().addAll(hBox, pane);
            Scene scene = new Scene(vBox, 700, 525);
            stage.setScene(scene);
            stage.setTitle("FX Demo");
            stage.show();
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
    }

    private void initializeVariables() throws IOException, ParseException {
        daysCombo = new DaysComboBox();
        monthCombo = new MonthComboBox();
        pane = new BorderPane();
        vBox = new VBox();
        ToggleGroup tg = createRadioButton();
        tg.selectedToggleProperty().addListener(new RadioListener());
    }

    private class RadioListener implements ChangeListener<Toggle>
    {
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
        {
            if(newValue.equals(rb1))
            {
                setUpDaysBorderPane(pane);
            }
            else
            {
                setUpMonthBorderPane(pane);
            }
        }
    }

    private void setUpDaysBorderPane(BorderPane pane)
    {
        ComboBox<?> daysComboBox = daysCombo.getDaysComboBox();
        ListView<Holiday> listView = daysCombo.getDaysListView();
        Text textView = daysCombo.getTextBox();
        designPane(pane,listView,textView,daysComboBox);
    }

    private void setUpMonthBorderPane(BorderPane pane)
    {
        ComboBox<?> monthsComboBox = monthCombo.getMonthsComboBox();
        ListView<Holiday> listView = monthCombo.getMonthsListView();
        Text textView = monthCombo.getTextBox();
        designPane(pane,listView,textView,monthsComboBox);
    }

    private void designPane(BorderPane pane, ListView<Holiday> listView, Text textView, ComboBox<?> comboBoxView)
    {
        pane.setTop(comboBoxView);
        pane.setAlignment(comboBoxView,Pos.TOP_CENTER);
        pane.setMargin(comboBoxView, new Insets(12,12,12,12));
        pane.setCenter(listView);
        pane.setBottom(textView);
        pane.setAlignment(textView,Pos.BOTTOM_CENTER);
        pane.setMargin(textView, new Insets(12,12,12,12));

    }

    private void setHBox()
    {
        hBox = new HBox();
        hBox.getChildren().addAll(rb1,rb2);
        hBox.setPadding(new Insets(10,0,0,0));
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setSpacing(25);
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
