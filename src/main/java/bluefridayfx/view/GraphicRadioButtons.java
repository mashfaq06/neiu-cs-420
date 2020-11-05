package bluefridayfx.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GraphicRadioButtons {

    private RadioButton grb1,grb2,grb3;
    private final ToggleGroup tg;
    private HBox graphicHBox;
    private PieChart dayPieChart, monthPieChart;
    private VBox gVBox;
    private BarChart<String,Number> chart;
    private final HolidayPieChart chartData;
    private final HolidayBarChart barChartData;


    public GraphicRadioButtons() throws IOException {
        chartData = new HolidayPieChart();
        barChartData = new HolidayBarChart();
        tg = new ToggleGroup();
        initializeRadioButton();
        setGraphicRadioButtonHBox();
        createRadioListener();
    }

    private void createRadioListener()
    {
        tg.selectedToggleProperty().addListener(new GraphicRadioListener());
    }

    private class GraphicRadioListener implements ChangeListener<Toggle>
    {
        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
        {
            if(newValue.equals(grb1))
            {
                gVBox.getChildren().remove(chart);
                gVBox.getChildren().remove(monthPieChart);
                getDayPieChart();
                gVBox.getChildren().add(dayPieChart);
            }
            else if(newValue.equals(grb2))
            {
                gVBox.getChildren().remove(chart);
                gVBox.getChildren().remove(dayPieChart);
                getMonthPieChart();
                gVBox.getChildren().add(monthPieChart);
            }
            else
            {

                gVBox.getChildren().remove(monthPieChart);
                gVBox.getChildren().remove(dayPieChart);
                getHistogram();
                gVBox.getChildren().add(chart);
            }
        }
    }

    private void initializeRadioButton()
    {
        grb1 = new RadioButton("Holiday distribution by Day");
        grb2 = new RadioButton("Holiday distribution by Month");
        grb3 = new RadioButton("Comparing holidays between Weekends and Weekdays");
        grb1.setToggleGroup(tg);
        grb2.setToggleGroup(tg);
        grb3.setToggleGroup(tg);
    }

    private void setGraphicRadioButtonHBox()
    {
        gVBox = new VBox();
        graphicHBox = new HBox();
        graphicHBox.getChildren().addAll(grb1,grb2,grb3);
        graphicHBox.setPadding(new Insets(10,0,0,0));
        graphicHBox.setAlignment(Pos.CENTER);
        graphicHBox.setSpacing(25);
        gVBox.getChildren().add(graphicHBox);

    }

    public HBox getGraphicHBox() {
        return graphicHBox;
    }

    private void getDayPieChart()
    {
        dayPieChart = chartData.getDayPieChart();
    }

    private void getMonthPieChart()
    {
        monthPieChart = chartData.getMonthPieChart();
    }

    private void getHistogram()
    {
        chart = barChartData.getChart();
    }

    public VBox getGVBox() {
        return gVBox;
    }

}
