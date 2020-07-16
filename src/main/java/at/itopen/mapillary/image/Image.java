/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.image;

import at.itopen.mapillary.GPSData;
import at.itopen.mapillary.ISO8601.Json8601Deserializer;
import at.itopen.mapillary.ISO8601.Json8601Serializer;
import at.itopen.mapillary.Mapillary;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Image Metadata from the Rest Interface
 *
 * @author roland
 */
public class Image {

    private Integer ca;
    private String camera_make;
    private String camera_model;

    @JsonSerialize(using = Json8601Serializer.class)
    @JsonDeserialize(using = Json8601Deserializer.class)
    private Date captured_at;

    private String key;
    private String organization_key;
    private Boolean pano;
    private Boolean priv;
    private String sequence_key;
    private String user_key;
    private String username;
    private GPSData gps;

    /**
     * the Direction 0-360 the Image is looking at
     *
     * @return the ca [0-360]
     */
    public int getCa() {
        if (ca == null) {
            return 0;
        }
        return ca;
    }

    /**
     * The Camera Maker (Logitec)
     *
     * @return the camera_make
     */
    public String getCamera_make() {
        return camera_make;
    }

    /**
     * The Camera Model (C270)
     *
     * @return the camera_model
     */
    public String getCamera_model() {
        return camera_model;
    }

    /**
     * when was the Image taken
     *
     * @return the captured_at
     */
    public Date getCaptured_at() {
        return captured_at;
    }

    /**
     * The Uniqe Image Key
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * To which organization does this Image belong to?
     *
     * @return the organization_key
     */
    public String getOrganization_key() {
        return organization_key;
    }

    /**
     * Is it a Panorammic Image?
     *
     * @return the pano
     */
    public boolean getPano() {
        if (pano == null) {
            return false;
        }
        return pano;
    }

    /**
     * Is it a Private Image?
     *
     * @return the priv
     */
    public boolean getPriv() {
        if (priv == null) {
            return false;
        }
        return priv;
    }

    /**
     * To which sequence does this Image belong to?
     *
     * @return the sequence_key
     */
    public String getSequence_key() {
        return sequence_key;
    }

    /**
     * To which User does this Image belong to?
     *
     * @return the user_key
     */
    public String getUser_key() {
        return user_key;
    }

    /**
     * The Username of the User who upladed the Image
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Fetch the Image (the jpg) from the Mapillary Server.
     *
     * @param fetchSize The Image Size (You can only fetch Thumbnails up to 2048
     * @return The Image as Buffered Image or null if there was an error
     */
    public BufferedImage fetchImage(Mapillary.IMAGE_FETCH_SIZE fetchSize) {
        try {
            String surl = Mapillary.IMAGE_FETCH_URL;
            surl = surl.replace("{key}", key);
            surl = surl.replace("{size}", fetchSize.name().substring(1));

            URL url = new URL(surl);
            return ImageIO.read(url);

        } catch (IOException ex) {
            Logger.getLogger(Mapillary.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get the GPS Coordiantes of the Image
     *
     * @return GPS Data
     */
    public GPSData getGps() {
        return gps;
    }

    /**
     * Used for internal setting the GPD Data
     *
     * @param gps GPS Coordinates
     */
    protected void setGps(GPSData gps) {
        this.gps = gps;
    }

}
