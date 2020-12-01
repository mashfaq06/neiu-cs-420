package bluefridayfx.models;

import java.util.Objects;

public class Countries {

    private String name;
    private int noOfDays;

    public Countries(String name, int noOfDays) {
        this.name = name;
        this.noOfDays = noOfDays;
    }

    public String getName() {
        return name;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Countries)) return false;
        Countries countries = (Countries) o;
        return noOfDays == countries.noOfDays &&
                Objects.equals(name, countries.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, noOfDays);
    }

    @Override
    public String toString() {
        return "Countries{" +
                "name='" + name + '\'' +
                ", noOfDays=" + noOfDays +
                '}';
    }
}
