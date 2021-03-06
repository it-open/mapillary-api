/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.user;

import at.itopen.mapillary.Filter;
import java.util.ArrayList;

/**
 * A Collection of all Users
 *
 * @author roland
 */
public class UserCollection extends ArrayList<User> {

    private String pageableNext, pageablePrev;
    private Filter filter;

    /**
     * Copied from Pageable (Internally used)
     *
     * @param linkline header link
     * @param filter original filter
     */
    public void parsePageable(String linkline, Filter filter) {
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

    /**
     * return the Filter
     *
     * @return data
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * next found?
     *
     * @return data
     */
    public boolean isPageabe() {
        return this.pageableNext != null;
    }

    /**
     * get the Key to the next page
     *
     * @return key
     */
    public String getPageableNext() {
        return pageableNext;
    }

    /**
     * get the Key to the last page
     *
     * @return key
     */
    public String getPageablePrev() {
        return pageablePrev;
    }

}
