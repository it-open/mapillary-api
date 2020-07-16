/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.image;

import at.itopen.mapillary.Filter;
import at.itopen.mapillary.sequence.Sequence;
import at.itopen.mapillary.user.User;
import at.itopen.simplerest.client.RestClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Filter Images for the Request. You can fill in as many Fields you like
 *
 * @author roland
 */
public class ImageFilter extends Filter {

    Double bbox_xmin, bbox_xmax, bbox_ymin, bbox_ymax;
    Double closeto_lat, closeto_lon;
    Date endTime;
    Date startTime;
    List<String> imagekeys = new ArrayList<>();
    Double lookat_lat, lookat_lon;
    Long radius;
    Long perPage;
    Boolean priv;
    Boolean pano;
    List<String> sequencekeys = new ArrayList<>();
    String title;
    List<String> organizations = new ArrayList<>();
    List<String> users = new ArrayList<>();
    List<String> username = new ArrayList<>();

    /**
     * The Organization the Images should belong to
     *
     * @param key String Key
     */
    public void addOrgranizationKey(String key) {
        organizations.add(key);
    }

    /**
     * The User the Images should belong to
     *
     * @param key String Key
     */
    public void addUserKey(String key) {
        users.add(key);
    }

    /**
     * The Organization the Images should belong to
     *
     * @param user User Object
     */
    public void addUser(User user) {
        addUserKey(user.getKey());
    }

    /**
     * The User Name the Images should belong to
     *
     * @param name String name
     */
    public void addUserName(String name) {
        username.add(name);
    }

    /**
     * The Sequence the Images should belong to
     *
     * @param key Sequence Key
     */
    public void addSequence(String key) {
        sequencekeys.add(key);
    }

    /**
     * The Sequence the Images should belong to
     *
     * @param sequence The sequence Object
     */
    public void addSequence(Sequence sequence) {
        sequencekeys.add(sequence.getKey());
    }

    /**
     * Defined Images Keys
     *
     * @param key String key
     */
    public void addImage(String key) {
        imagekeys.add(key);
    }

    /**
     * Defined Images (Ist this Useful?)
     *
     * @param image Image Object
     */
    public void addImage(Image image) {
        imagekeys.add(image.getKey());
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
     * All Images that look at this certain point
     *
     * @param lat Latitude of Point
     * @param lon Longitude of Point
     */
    public void setLookat(double lat, double lon) {
        lookat_lat = lat;
        lookat_lon = lon;
    }

    /**
     * All Images that are close to this certain point
     *
     * @param lat Latitude of Point
     * @param lon Longitude of Point
     */
    public void setCloseto(double lat, double lon) {
        closeto_lat = lat;
        closeto_lon = lon;
    }

    /**
     * No images recorded after this time
     *
     * @param endTime datetime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * No Iages recorded before this time
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
     * Priavte Images
     *
     * @param priv true or false
     */
    public void setPrivate(Boolean priv) {
        this.priv = priv;
    }

    /**
     * Panoramic Images
     *
     * @param pano trueor false
     */
    public void setPano(Boolean pano) {
        this.pano = pano;
    }

    /**
     * A Radius for the Close To
     *
     * @param radius Meters of radius
     */
    public void setRadius(Long radius) {
        this.radius = radius;
    }

    /**
     * Image with certain titles
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
        if (closeto_lat != null) {
            StringBuilder data = new StringBuilder();
            data.append(cNumber(closeto_lon));
            data.append(",");
            data.append(cNumber(closeto_lat));
            client.setParameter("closeto", data.toString());
        }
        if (endTime != null) {
            client.setParameter("end_time", cDate(endTime));
        }
        if (organizations.size() > 0) {
            client.setParameter("organization_keys", cList(organizations));
        }
        if (imagekeys.size() > 0) {
            client.setParameter("image_keys", cList(imagekeys));
        }
        if (lookat_lat != null) {
            StringBuilder data = new StringBuilder();
            data.append(cNumber(lookat_lon));
            data.append(",");
            data.append(cNumber(lookat_lat));
            client.setParameter("lookat", data.toString());
        }
        if (pano != null) {
            client.setParameter("pano", cBool(pano));
        }
        if (perPage != null) {
            client.setParameter("per_page", cNumber(perPage));
        }
        if (priv != null) {
            client.setParameter("private", cBool(priv));
        }
        if (radius != null) {
            client.setParameter("radius", cNumber(radius));
        }
        if (startTime != null) {
            client.setParameter("start_time", cDate(startTime));
        }
        if (sequencekeys.size() > 0) {
            client.setParameter("sequence_keys", cList(sequencekeys));
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
