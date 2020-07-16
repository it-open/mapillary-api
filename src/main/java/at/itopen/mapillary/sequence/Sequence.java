/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.sequence;

import at.itopen.mapillary.ISO8601.Json8601Deserializer;
import at.itopen.mapillary.ISO8601.Json8601Serializer;
import at.itopen.mapillary.Mapillary;
import at.itopen.mapillary.image.ImageCollection;
import at.itopen.mapillary.image.ImageFilter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 * A Sequence is a series of Images
 *
 * @author roland
 */
public class Sequence {

    private String camera_make;

    @JsonSerialize(using = Json8601Serializer.class)
    @JsonDeserialize(using = Json8601Deserializer.class)
    private Date captured_at;

    private SequenceProperties coordinateProperties;

    @JsonSerialize(using = Json8601Serializer.class)
    @JsonDeserialize(using = Json8601Deserializer.class)
    private Date created_at;

    private String key;
    private String organization_key;
    private Boolean pano;
    private Boolean priv;
    private String user_key;
    private String username;

    /**
     * What Camera was used
     *
     * @return the camera_make
     */
    public String getCamera_make() {
        return camera_make;
    }

    /**
     * When was the Squence Captured
     *
     * @return the captured_at
     */
    public Date getCaptured_at() {
        return captured_at;
    }

    /**
     * Propertis of the Images
     *
     * @return the coordinateProperties
     */
    public SequenceProperties getCoordinateProperties() {
        return coordinateProperties;
    }

    /**
     * When was the Sequence created (Uploaded)
     *
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * The Uniqe Sequence Key
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * To which Organization does the Sequence belong to
     *
     * @return the organization_key
     */
    public String getOrganization_key() {
        return organization_key;
    }

    /**
     * Panoramic Images?
     *
     * @return the pano
     */
    public Boolean getPano() {
        return pano;
    }

    /**
     * Private Sequence
     *
     * @return the priv
     */
    public Boolean getPriv() {
        return priv;
    }

    /**
     * To which User does the Sequence belong to
     *
     * @return the user_key
     */
    public String getUser_key() {
        return user_key;
    }

    /**
     * To which Username does the Sequence belong to
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get all Images that belong to the Sequence
     *
     * @param mapillary The Mapillary Object for the Authentication
     * @param filter Null if no Filter, if you provide one the sequence Filter
     * will be added
     * @return List of all Images
     */
    public ImageCollection fetchImages(Mapillary mapillary, ImageFilter filter) {
        if (filter == null) {
            filter = new ImageFilter();
        }
        filter.addSequence(this);
        return mapillary.getImages(filter);
    }

}
