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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class BlueFridayApplication extends Application {

    private MonthComboBox monthCombo;
    private DaysComboBox daysCombo;
    private BorderPane pane, gPane;
    private HBox hBox, gHBox;
    private VBox vBox,gVBox;
    private Button switchingButton;
    private Scene firstScene, secondScene, errorScene;
    private Stage window;
    private GraphicRadioButtons grb;
    private CategoriesRadioButtons rb;
    private Label gMessage;
    private Text errorTextBox;
    private QueryRadioButtons query;
    private Text textView;

    public BlueFridayApplication()
    {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            window = stage;
            setFirstScene();
            window.setScene(firstScene);
            window.setTitle("Holiday JavaFX Window");
            window.show();
        }
        catch (IOException e)
        {
            exceptionScene(window);
        }
    }

    private void exceptionScene(Stage stage) {
        window = stage;
        setErrorScene();
        window.setScene(errorScene);
        window.setTitle("Error Occurred");
        window.show();
    }

    private void setErrorScene()
    {
        errorTextBox = new Text("Unable to display data. \n Check your internet connection " +
                "\n Close this window and \n Re-run the application");
        errorTextBox.setFill(Color.RED);
        HBox errorBox = new HBox(errorTextBox);
        errorBox.setPadding(new Insets(10,10,10,10));
        errorScene = new Scene(errorBox,300,100);
    }

    private void setFirstScene() throws IOException {
        textView = new Text();
        initializeFirstSceneVariables();
        hBox = rb.getCategoriesHBox();
        vBox.getChildren().addAll(hBox, pane);
        firstScene = new Scene(vBox, 800, 555);
    }


    private void initializeFirstSceneVariables() throws IOException {
        daysCombo = new DaysComboBox();
        monthCombo = new MonthComboBox();
        pane = new BorderPane();
        vBox = new VBox();
        rb = new CategoriesRadioButtons();
        ToggleGroup tg = rb.getTg();
        tg.selectedToggleProperty().addListener(new RadioListener());
    }

    private class RadioListener implements ChangeListener<Toggle>
    {
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
        {
            if(newValue.equals(rb.getRb1()))
            {
                setUpDaysBorderPane(pane);
            }
            else if(newValue.equals(rb.getRb2()))
            {
                setUpMonthBorderPane(pane);
            }
            else if(newValue.equals(rb.getRb3()))
            {
                try {
                    setQueryData(pane);
                } catch (IOException e) {
                    exceptionScene(window);
                }
            }
            else
            {
                try {
                    setSecondScene();
                } catch (IOException e) {
                    exceptionScene(window);
                }
            }
        }
    }

    private void setUpDaysBorderPane(BorderPane pane)
    {
        ComboBox<?> daysComboBox = daysCombo.getDaysComboBox();
        ListView<Holiday> listView = daysCombo.getDaysListView();
        textView = daysCombo.getTextBox();
        designPane(pane,listView,textView,daysComboBox);
    }

    private void setUpMonthBorderPane(BorderPane pane)
    {
        ComboBox<?> monthsComboBox = monthCombo.getMonthsComboBox();
        ListView<Holiday> listView = monthCombo.getMonthsListView();
        textView = monthCombo.getTextBox();
        designPane(pane,listView,textView,monthsComboBox);
    }

    private void setQueryData(BorderPane pane) throws IOException {
        textView.setText("");
        query = new QueryRadioButtons();
        pane.setTop(query.getOptionsBox());
        HBox displayBox = query.getDisplayBox();
        displayBox.setAlignment(Pos.CENTER);
        displayBox.setPadding(new Insets(20,20,20,20));
        pane.setCenter(displayBox);

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

    private void setSecondScene() throws IOException {
        initializeSecondSceneVariables();
        switchingButton.setOnAction(e -> {
            window.setScene(firstScene);
            rb.getRb2().setSelected(true);
        });
        designGPane();
        secondScene = new Scene(gPane,900,555);
        window.setScene(secondScene);
    }

    private void designGPane() {
        gPane.setTop(gMessage);
        gPane.setAlignment(gMessage,Pos.TOP_CENTER);
        gPane.setMargin(gMessage, new Insets(12,12,12,12));
        gPane.setCenter(gVBox);
        gPane.setBottom(switchingButton);
        gPane.setAlignment(switchingButton,Pos.BOTTOM_CENTER);
        gPane.setMargin(switchingButton, new Insets(12,12,12,12));
    }

    private void initializeSecondSceneVariables() throws IOException {
        gPane = new BorderPane();
        grb = new GraphicRadioButtons();
        gHBox = grb.getGraphicHBox();
        gVBox = grb.getGVBox();
        switchingButton = new Button("Switch back to text data");
        gMessage = new Label("Welcome to Graphics");
    }

}
