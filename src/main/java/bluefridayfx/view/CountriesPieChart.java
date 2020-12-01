package bluefridayfx.view;

import bluefridayfx.models.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static bluefridayfx.models.ConvertToCollectionsForCountriesData.getCountriesHoliday;

public class CountriesPieChart {

    private PieChart countryPieChart;
    private List<Countries> listOfCountries;
    private List<String> distinctCountries;

    public CountriesPieChart() throws IOException {
        listOfCountries = getCountriesHoliday();
        getListOfCountries();
        initializeCountriesPieChart();
    }

    private void getListOfCountries()
    {
        distinctCountries = listOfCountries.stream().map(Countries::getName).distinct().collect(Collectors.toList());
    }

    private void initializeCountriesPieChart()
    {
        PieChart.Data[] data = new PieChart.Data[distinctCountries.size()];
        IntStream.range(0, distinctCountries.size()).forEach(i -> {
            String country = distinctCountries.get(i);
            data[i] = new PieChart.Data(country, listOfCountries.stream().filter(countries -> countries.getName().equals(country)).mapToDouble(Countries::getNoOfDays).sum());
        });
        ObservableList<PieChart.Data> dayData = FXCollections.observableArrayList(data);
        countryPieChart = new PieChart(dayData);
        countryPieChart.setTitle("Distribution of Holidays per Country");
    }

    public PieChart getCountryPieChart() {
        return countryPieChart;
    }
}
