/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roland
 */
public class GeoJSONCollection<T> {

    private String type;
    private List<GeoJSON<T>> features;

    private String pageableNext, pageablePrev;
    private Filter filter;

    protected void parsePageable(String linkline, Filter filter) {
        this.filter = filter;
        int pos = linkline.indexOf("_next_page_token=");
        if (pos >= 0) {
            pos = pos + "_next_page_token=".length();
            int p1 = linkline.indexOf("&", pos);
            int p2 = linkline.indexOf(">", pos);
            if (p1 == -1) {
                p1 = linkline.length();
            }
            if (p2 == -1) {
                p1 = linkline.length();
            }

            int p = p1;

            if (p2 < p1) {
                p = p2;
            }
            pageableNext = linkline.substring(pos, p).replaceAll("%3D", "=");

        }
        pos = linkline.indexOf("_prev_page_token=");
        if (pos >= 0) {
            pos = pos + "_prev_page_token=".length();
            int p1 = linkline.indexOf("&", pos);
            int p2 = linkline.indexOf(">", pos);
            if (p1 == -1) {
                p1 = linkline.length();
            }
            if (p2 == -1) {
                p1 = linkline.length();
            }

            int p = p1;
            if (p2 < p1) {
                p = p2;
            }
            pageablePrev = linkline.substring(pos, p).replaceAll("%3D", "=");

        }
    }

    public List<T> asList() {
        List<T> values = new ArrayList<>();
        for (GeoJSON<T> val : features) {
            values.add(val.getProperties());
        }
        return values;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setPageableNext(String pageableNext) {
        this.pageableNext = pageableNext;
    }

    public void setPageablePrev(String pageablePrev) {
        this.pageablePrev = pageablePrev;
    }

    public boolean isPageabe() {
        return this.pageableNext != null;
    }

    public String getPageableNext() {
        return pageableNext;
    }

    public String getPageablePrev() {
        return pageablePrev;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the features
     */
    public List<GeoJSON<T>> getFeatures() {
        return features;
    }

    /**
     * @param features the features to set
     */
    public void setFeatures(List<GeoJSON<T>> features) {
        this.features = features;
    }

}
