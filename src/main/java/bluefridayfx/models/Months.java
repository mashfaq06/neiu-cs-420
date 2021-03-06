package bluefridayfx.models;

public enum Months {

    JANUARY (1, "January"),
    FEBRUARY(2, "February"),
    MARCH(3,"March"),
    APRIL(4,"April"),
    MAY(5,"May"),
    JUNE(6,"June"),
    JULY(7,"July"),
    AUGUST(8,"August"),
    OCTOBER(10,"October"),
    SEPTEMBER(9,"September"),
    NOVEMBER(11,"November"),
    DECEMBER(12,"December");

    private int monthNo;
    private String monthName;

    Months(int monthNo, String monthName)
    {
        this.monthNo = monthNo;
        this.monthName = monthName;
    }

    public int getMonthNo() {
        return monthNo;
    }

    public String getMonthName() {
        return monthName;
    }
}
