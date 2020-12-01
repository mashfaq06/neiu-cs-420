package bluefridayfx.view;

import bluefridayfx.models.Holiday;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bluefridayfx.models.ConvertToCollectionsForHolidayData.getHoliday;

public class HolidayPieChart {

    private PieChart monthPieChart;
    private final List<Holiday> daysList;
    private List<String> distinctMonth;


    public HolidayPieChart() throws IOException {
        daysList = getHoliday();
        getListOfDays();
        initializeMonthPieChart();
    }

    private void getListOfDays()
    {
        distinctMonth = daysList.stream().map(days -> days.getDate().getMonth().toString()).distinct().collect(Collectors.toList());
    }

    private void initializeMonthPieChart()
    {
        PieChart.Data[] monthData = new PieChart.Data[distinctMonth.size()];
        IntStream.range(0, distinctMonth.size()).forEach(i -> {
            String month = distinctMonth.get(i);
            monthData[i] = new PieChart.Data(month, daysList.stream().filter(days -> days.getDate().getMonth().toString().equals(month)).count());
        });
        monthPieChart = new PieChart(FXCollections.observableArrayList(monthData));
        monthPieChart.setTitle("Displaying data of Month");
    }

    public PieChart getMonthPieChart() {
        return monthPieChart;
    }
}
