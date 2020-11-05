package bluefridayfx.view;

import bluefridayfx.models.Holiday;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bluefridayfx.models.ConvertToCollections.getHoliday;

public class HolidayPieChart {

    private PieChart dayPieChart, monthPieChart;
    private final List<Holiday> daysList;
    private List<String> distinctDays, distinctMonth;


    public HolidayPieChart() throws IOException {
        daysList = getHoliday();
        getListOfDays();
        initializeDayPieChart();
        initializeMonthPieChart();
    }

    private void getListOfDays()
    {
        distinctDays = daysList.stream().map(Holiday::getDay).distinct().collect(Collectors.toList());
        distinctMonth = daysList.stream().map(days -> days.getDate().getMonth().toString()).distinct().collect(Collectors.toList());
    }

    public void initializeDayPieChart()
    {
        PieChart.Data[] data = new PieChart.Data[7];
        IntStream.range(0, distinctDays.size()).forEach(i -> {
            String day = distinctDays.get(i);
            data[i] = new PieChart.Data(day, daysList.stream().filter(days -> days.getDay().equals(day)).count());
        });
        ObservableList<PieChart.Data> dayData = FXCollections.observableArrayList(data);
        dayPieChart = new PieChart(dayData);
        dayPieChart.setTitle("Displaying data of days");
    }

    private void initializeMonthPieChart()
    {
        PieChart.Data[] monthData = new PieChart.Data[distinctMonth.size()];
        IntStream.range(0, distinctMonth.size()).forEach(i -> {
            String month = distinctMonth.get(i);
            monthData[i] = new PieChart.Data(month, daysList.stream().filter(days -> days.getDate().getMonth().toString().equals(month)).count());
        });
        monthPieChart = new PieChart(FXCollections.observableArrayList(monthData));
        monthPieChart.setTitle("Displaying data of days");
    }

    public PieChart getDayPieChart() {
        return dayPieChart;
    }

    public PieChart getMonthPieChart() {
        return monthPieChart;
    }
}
