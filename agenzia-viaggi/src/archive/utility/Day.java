/*
 For example, January 1, 1998 was a Thursday. If getFirstDayOfWeek() is MONDAY
 and getMinimalDaysInFirstWeek() is 4 (these are the values reflecting ISO 8601
 and many national standards), then week 1 of 1998 starts on December 29, 1997,
 and ends on January 4, 1998. If, however, getFirstDayOfWeek() is SUNDAY,
 then week 1 of 1998 starts on January 4, 1998, and ends on January 10, 1998;
 the first three days of 1998 then are part of week 53 of 1997.
 */

package archive.utility;
import java.util.*;

public class Day implements Comparable<Integer>{
    private Calendar calendar = new GregorianCalendar();
    
    private int currentWeek;

    public Day(){
        calendar.setMinimalDaysInFirstWeek(7);
        currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int compareTo(Integer week) {
        if(week == null) throw new NullPointerException();
        if(week == currentWeek) return 0;
            else{
                return (week > currentWeek   ? 1 : -1);
            }
    }
    public int getCurrentWeek(){
        return currentWeek;
    }
}
