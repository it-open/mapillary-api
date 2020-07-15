/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.user;

import at.itopen.mapillary.Filter;
import at.itopen.simplerest.client.RestClient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roland
 */
public class UserFilter extends Filter {

    Double bbox_xmin, bbox_xmax, bbox_ymin, bbox_ymax;
    Long perPage;
    List<String> users = new ArrayList<>();
    List<String> username = new ArrayList<>();

    /**
     *
     * @param key
     */
    public void addUserKey(String key) {
        users.add(key);
    }

    /**
     *
     * @param name
     */
    public void addUserName(String name) {
        username.add(name);
    }

    /**
     *
     * @param latMin
     * @param latMax
     * @param lonMin
     * @param lonMax
     */
    public void setBBox(double latMin, double latMax, double lonMin, double lonMax) {
        bbox_xmin = latMin;
        bbox_xmax = latMax;
        bbox_ymin = lonMin;
        bbox_ymax = lonMax;
    }

    /**
     *
     * @param perPage
     */
    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    /**
     *
     * @param client
     */
    @Override
    public void makeFilterParams(RestClient client) {
        if (bbox_xmin != null) {
            StringBuilder bbox = new StringBuilder();
            bbox.append(cNumber(bbox_xmin));
            bbox.append(",");
            bbox.append(cNumber(bbox_ymin));
            bbox.append(",");
            bbox.append(cNumber(bbox_xmax));
            bbox.append(",");
            bbox.append(cNumber(bbox_ymax));
            client.setParameter("bbox", bbox.toString());
        }

        if (perPage != null) {
            client.setParameter("per_page", cNumber(perPage));
        }

        if (users.size() > 0) {
            client.setParameter("userkeys", cList(users));
        }
        if (username.size() > 0) {
            client.setParameter("usernames", cList(username));
        }

    }

}
