package bluefridayfx.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CategoriesRadioButtons {

    private RadioButton rb1,rb2,rb3, graphicRadioButton;
    private ToggleGroup tg;
    private HBox categoriesHBox;
    private QueryRadioButtons query;

    public CategoriesRadioButtons() throws IOException {
        query = new QueryRadioButtons();
        tg = new ToggleGroup();
        initializeRadioButton();
        setCategoriesRadioButtonHBox();

    }

    private void initializeRadioButton()
    {
        rb1 = new RadioButton("Show all the data\ncategorised by Days");
        rb2 = new RadioButton("Show all the data\ncategorised by Months");
        rb3 = new RadioButton("Querying data by Days");
        graphicRadioButton = new RadioButton("Switch to Graphical data");
        addRadioButtonToToggleGroup();
    }

    private void addRadioButtonToToggleGroup() {
        tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb3.setToggleGroup(tg);
        graphicRadioButton.setToggleGroup(tg);
    }

    private void setCategoriesRadioButtonHBox()
    {
        categoriesHBox = new HBox();
        categoriesHBox.getChildren().addAll(rb1,rb3,rb2, graphicRadioButton);
        categoriesHBox.setPadding(new Insets(10,0,0,0));
        categoriesHBox.setAlignment(Pos.TOP_CENTER);
        categoriesHBox.setSpacing(25);
    }

    public HBox getCategoriesHBox() {
        return categoriesHBox;
    }

    public ToggleGroup getTg() {
        return tg;
    }

    public RadioButton getRb1() {
        return rb1;
    }

    public RadioButton getRb2() {
        return rb2;
    }

    public RadioButton getRb3() {
        return rb3;
    }

}
