package BlueFridayFX.Comparators;

import BlueFridayFX.Days;

import java.util.Comparator;

public class DaysComparator implements Comparator<Days> {

    public DaysComparator(){}

    @Override
    public int compare(Days o1, Days o2) {
        if(o1.getDayNo() > o2.getDayNo())
            return 1;
        else if(o1.getDayNo() < o2.getDayNo())
            return -1;
        else
            return 0;
    }
}
