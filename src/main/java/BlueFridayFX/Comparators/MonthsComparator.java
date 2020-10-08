package BlueFridayFX.Comparators;

import BlueFridayFX.Months;

import java.util.Comparator;

public class MonthsComparator implements Comparator<Months> {

    public MonthsComparator(){}

    @Override
    public int compare(Months o1, Months o2) {
        if(o1.getMonthNo() > o2.getMonthNo())
            return 1;
        else if(o1.getMonthNo() < o2.getMonthNo())
            return -1;
        else
            return 0;
    }
}
