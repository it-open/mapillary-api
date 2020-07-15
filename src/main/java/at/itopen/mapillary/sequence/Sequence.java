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
     * @return the camera_make
     */
    public String getCamera_make() {
        return camera_make;
    }

    /**
     * @param camera_make the camera_make to set
     */
    public void setCamera_make(String camera_make) {
        this.camera_make = camera_make;
    }

    /**
     * @return the captured_at
     */
    public Date getCaptured_at() {
        return captured_at;
    }

    /**
     * @param captured_at the captured_at to set
     */
    public void setCaptured_at(Date captured_at) {
        this.captured_at = captured_at;
    }

    /**
     * @return the coordinateProperties
     */
    public SequenceProperties getCoordinateProperties() {
        return coordinateProperties;
    }

    /**
     * @param coordinateProperties the coordinateProperties to set
     */
    public void setCoordinateProperties(SequenceProperties coordinateProperties) {
        this.coordinateProperties = coordinateProperties;
    }

    /**
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the organization_key
     */
    public String getOrganization_key() {
        return organization_key;
    }

    /**
     * @param organization_key the organization_key to set
     */
    public void setOrganization_key(String organization_key) {
        this.organization_key = organization_key;
    }

    /**
     * @return the pano
     */
    public Boolean getPano() {
        return pano;
    }

    /**
     * @param pano the pano to set
     */
    public void setPano(Boolean pano) {
        this.pano = pano;
    }

    /**
     * @return the priv
     */
    public Boolean getPriv() {
        return priv;
    }

    /**
     * @param priv the priv to set
     */
    public void setPriv(Boolean priv) {
        this.priv = priv;
    }

    /**
     * @return the user_key
     */
    public String getUser_key() {
        return user_key;
    }

    /**
     * @param user_key the user_key to set
     */
    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param mapillary
     * @param filter
     * @return
     */
    public ImageCollection fetchImages(Mapillary mapillary, ImageFilter filter) {
        if (filter == null) {
            filter = new ImageFilter();
        }
        filter.addSequence(this);
        return mapillary.getImages(filter);
    }

}
