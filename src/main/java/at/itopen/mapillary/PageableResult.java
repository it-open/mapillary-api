/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

/**
 * There are multiple Pages for a Collection how to get to the next page
 *
 * @author roland
 */
public class PageableResult {

    private String pageableNext, pageablePrev;
    private Filter filter;

    /**
     * internal Used save all Information for the next page
     *
     * @param linkline Headerfield Link
     * @param filter the Filter which is being used
     */
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

    /**
     * return the Filter
     *
     * @return
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * next found?
     *
     * @return
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
