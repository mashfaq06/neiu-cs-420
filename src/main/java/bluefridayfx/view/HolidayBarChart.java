package bluefridayfx.view;

import bluefridayfx.models.Holiday;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bluefridayfx.models.ConvertToCollectionsForHolidayData.getHoliday;

public class HolidayBarChart {

    private final BarChart<String,Number> chart;
    private final CategoryAxis xAxis;
    private final NumberAxis yAxis;
    private final List<Holiday> daysList;
    private final List<String> weekend = Arrays.asList("Saturday","Sunday");
    private final List<String> weekday = Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday");
    private final List<String> longWeekend = Arrays.asList("Monday","Friday");
    private XYChart.Series<String, Number> firstSeries, secondSeries, thirdSeries;
    private List<String> distinctMonth;


    public HolidayBarChart() throws IOException {
        daysList = getHoliday();
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        chart = new BarChart<>(xAxis,yAxis);
        filterMonths();
        setChart();
    }

    private void filterMonths()
    {
        distinctMonth = daysList.stream().map(days -> days.getDate().getMonth().toString()).distinct().collect(Collectors.toList());
    }

    private void initializeChart()
    {
        xAxis.setLabel("Months");
        yAxis.setLabel("Number of Days");
        chart.setTitle("Comparison of weekends and weekday holidays every month");
        firstSeries = new XYChart.Series<>();
        firstSeries.setName("Weekends");
        secondSeries = new XYChart.Series<>();
        secondSeries.setName("Week Days");
        thirdSeries = new XYChart.Series<>();
        thirdSeries.setName("Long Weekends");
    }

    private void setChart()
    {
       initializeChart();
       distinctMonth.forEach(month -> {
            firstSeries.getData().add(new XYChart.Data<>(month, daysList.stream().filter(days -> days.getDate().getMonth().toString().equals(month)).filter(days -> weekend.contains(days.getDay())).count()));
            secondSeries.getData().add(new XYChart.Data<>(month, daysList.stream().filter(days -> days.getDate().getMonth().toString().equals(month)).filter(days -> weekday.contains(days.getDay())).count()));
            thirdSeries.getData().add(new XYChart.Data<>(month, (daysList.stream().filter(days -> days.getDate().getMonth().toString().equals(month)).filter(days -> longWeekend.contains(days.getDay())).count())/2));
       });
       chart.getData().add(firstSeries);
       chart.getData().add(secondSeries);
       chart.getData().add(thirdSeries);
    }

    public BarChart<String, Number> getChart() {
        return chart;
    }
}
