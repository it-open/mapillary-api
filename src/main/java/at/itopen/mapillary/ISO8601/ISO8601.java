/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.ISO8601;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author roland
 */
public class ISO8601 {

    /**
     * Transform Calendar to ISO 8601 string.
     *
     * @param date
     * @return
     */
    public static String fromDate(final Date date) {
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ")
                .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Get current date and time formatted as ISO 8601 string.
     *
     * @return
     */
    public static String now() {
        return fromDate(new Date());
    }

    /**
     * Transform ISO 8601 string to Calendar.
     *
     * @param iso8601string
     * @return
     * @throws java.text.ParseException
     */
    public static Date toDate(final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        try {
            int pos = s.lastIndexOf(':');
            s = s.substring(0, pos) + s.substring(pos + 1);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ").parse(s);
        return date;
    }
}
