package at.itopen.mapillary;

import at.itopen.mapillary.ISO8601.ISO8601;
import at.itopen.simplerest.client.RestClient;
import java.util.Date;
import java.util.List;

/**
 * Base Class for all Filters (ImageFilter, Seuqence Filter)
 *
 * @author roland
 */
public abstract class Filter {

    /**
     * Build the Filter Params (Internal Used)
     *
     * @param client The Rest Client to get the Request
     */
    public abstract void makeFilterParams(RestClient client);

    /**
     * Convert Date to ISO8601 String
     *
     * @param date The Date
     * @return The String
     */
    protected String cDate(Date date) {
        if (date == null) {
            return null;
        }
        return ISO8601.fromDate(date);
    }

    /**
     * Convert Number to compatible String
     *
     * @param value Any Number
     * @return the String
     */
    protected String cNumber(Number value) {
        if (value == null) {
            return null;
        }
        return value.toString().replace(',', '.');
    }

    /**
     * Convert a Boolean to String
     *
     * @param value Boolean Value
     * @return "true" if true otherwise "false"
     */
    protected String cBool(Boolean value) {
        if (Boolean.TRUE.equals(value)) {
            return "true";
        }
        return "false";
    }

    /**
     * Convert a String List to a String
     *
     * @param value List of Strings
     * @return text,text,text
     */
    protected String cList(List<String> value) {
        StringBuilder sb = new StringBuilder();
        for (String v : value) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(v);
        }
        return sb.toString();
    }

}
