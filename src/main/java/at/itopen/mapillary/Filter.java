package at.itopen.mapillary;

import at.itopen.mapillary.ISO8601.ISO8601;
import at.itopen.simplerest.client.RestClient;
import java.util.Date;
import java.util.List;

/**
 *
 * @author roland
 */
public abstract class Filter {

    /**
     *
     * @param client
     */
    public abstract void makeFilterParams(RestClient client);

    /**
     *
     * @param date
     * @return
     */
    protected String cDate(Date date) {
        if (date == null) {
            return null;
        }
        return ISO8601.fromDate(date);
    }

    /**
     *
     * @param value
     * @return
     */
    protected String cNumber(Number value) {
        if (value == null) {
            return null;
        }
        return value.toString().replace(',', '.');
    }

    /**
     *
     * @param value
     * @return
     */
    protected String cBool(Boolean value) {
        if (Boolean.TRUE.equals(value)) {
            return "true";
        }
        return "false";
    }

    /**
     *
     * @param value
     * @return
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
