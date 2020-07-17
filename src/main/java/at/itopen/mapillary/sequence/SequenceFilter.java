/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.sequence;

import at.itopen.mapillary.Filter;
import at.itopen.mapillary.user.User;
import at.itopen.simplerest.client.RestClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Filter Sequences for the Request. You can fill in as many Fields you like
 *
 * @author roland
 */
public class SequenceFilter extends Filter {

    Double bbox_xmin, bbox_xmax, bbox_ymin, bbox_ymax;
    Date endTime;
    Date startTime;
    Long perPage;
    Boolean priv;
    Boolean starred;
    String title;
    List<String> organizations = new ArrayList<>();
    List<String> users = new ArrayList<>();
    List<String> username = new ArrayList<>();

    /**
     * The Organization the Sequences should belong to
     *
     * @param key String key
     */
    public void addOrgranizationKey(String key) {
        organizations.add(key);
    }

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
     * No sequences recorded after this time
     *
     * @param endTime datetime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * No sequences recorded before this time
     *
     * @param startTime datetime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * How Many Results per Page (Pageable)
     *
     * @param perPage
     */
    public void setPerPage(long perPage) {
        this.perPage = perPage;
    }

    /**
     * Priavte Sequences
     *
     * @param priv true or false
     */
    public void setPrivate(Boolean priv) {
        this.priv = priv;
    }

    /**
     * Starred Sequences
     *
     * @param starred
     */
    public void setStarred(Boolean starred) {
        this.starred = starred;
    }

    /**
     * Sequences with Title
     *
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
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
        if (endTime != null) {
            client.setParameter("end_time", cDate(endTime));
        }
        if (organizations.size() > 0) {
            client.setParameter("organization_keys", cList(organizations));
        }

        if (perPage != null) {
            client.setParameter("per_page", cNumber(perPage));
        }
        if (priv != null) {
            client.setParameter("private", cBool(priv));
        }
        if (starred != null) {
            client.setParameter("starred", cBool(starred));
        }
        if (startTime != null) {
            client.setParameter("start_time", cDate(startTime));
        }
        if (title != null) {
            client.setParameter("title", title);
        }
        if (users.size() > 0) {
            client.setParameter("userkeys", cList(users));
        }
        if (username.size() > 0) {
            client.setParameter("usernames", cList(username));
        }

    }

}
