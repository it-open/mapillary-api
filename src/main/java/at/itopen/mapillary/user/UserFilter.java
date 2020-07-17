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
 * Filter Users when searching
 *
 * @author roland
 */
public class UserFilter extends Filter {

    Double bbox_xmin, bbox_xmax, bbox_ymin, bbox_ymax;
    Long perPage;
    List<String> users = new ArrayList<>();
    List<String> username = new ArrayList<>();

    /**
     * The User the Sequences should belong to
     *
     * @param key String key
     */
    public void addUserKey(String key) {
        users.add(key);
    }

    /**
     * The User the Sequences should belong to
     *
     * @param user User Object
     */
    public void addUser(User user) {
        addUserKey(user.getKey());
    }

    /**
     * The UserName the Sequences should belong to
     *
     * @param name String
     */
    public void addUserName(String name) {
        username.add(name);
    }

    /**
     * The Bounding Box of GPS Information
     *
     * @param latMin Latitude min
     * @param latMax Latitude max
     * @param lonMin Longitude min
     * @param lonMax Longitude max
     */
    public void setBBox(double latMin, double latMax, double lonMin, double lonMax) {
        bbox_xmin = latMin;
        bbox_xmax = latMax;
        bbox_ymin = lonMin;
        bbox_ymax = lonMax;
    }

    /**
     * How Many Results per Page (Pageable)
     *
     * @param perPage numbers of results per page
     */
    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    /**
     * Build the Filter Params (Internal Used)
     *
     * @param client The Rest Client to get the Request
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
