package bluefridayfx.models;

public enum Days {

    MONDAY (1,"Monday"),
    TUESDAY(2, "Tuesday"),
    WEDNESDAY(3,"Wednesday"),
    THURSDAY(4,"Thursday"),
    FRIDAY(5,"Friday"),
    SATURDAY(6,"Saturday"),
    SUNDAY(7,"Sunday");

    private int dayNo;
    private String dayName;

    Days (int dayNo, String dayName)
    {
        this.dayNo = dayNo;
        this.dayName = dayName;
    }

    public int getDayNo() {
        return dayNo;
    }

    public String getDayName() {
        return dayName;
    }
}
