/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import at.itopen.mapillary.ISO8601.ISO8601;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author roland
 */
public class MapillaryObject extends HashMap<String, String> {

    public void setBoolean(String key, Boolean value) {
        this.remove(key);
        if (value != null) {
            this.put(key, (value.booleanValue()) ? "true" : "false");
        }
    }

    public void setString(String key, String value) {
        this.remove(key);
        if (value != null) {
            this.put(key, value);
        }
    }

    public void setLong(String key, Long value) {
        this.remove(key);
        if (value != null) {
            this.put(key, "" + value.toString());
        }
    }

    public void setDouble(String key, Double value) {
        this.remove(key);
        if (value != null) {
            this.put(key, "" + value.toString());
        }
    }

    public void setDate(String key, Date value) {
        this.remove(key);
        if (value != null) {
            this.put(key, ISO8601.fromDate(value));
        }
    }

    public Boolean getBoolean(String key) {
        if (containsKey(key)) {
            String value = get(key).toLowerCase();
            if ("true".equals(value)) {
                return true;
            }
            if ("false".equals(value)) {
                return false;
            }
            if ("1".equals(value)) {
                return true;
            }
            if ("0".equals(value)) {
                return false;
            }
            if (value.isBlank()) { // Assume emty is false
                return false;
            }
            return true; // assume everything else is true
        } else {
            return null;
        }
    }

    public Long getLong(String key) {
        if (containsKey(key)) {
            String value = get(key);
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    public Double getDouble(String key) {
        if (containsKey(key)) {
            String value = get(key);
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    public Date getDate(String key) {
        if (containsKey(key)) {
            String value = get(key);
            try {
                return ISO8601.toDate(value);
            } catch (ParseException ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getString(String key) {
        if (containsKey(key)) {
            return get(key);

        } else {
            return null;
        }
    }

}
