/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.image;

import at.itopen.mapillary.ISO8601.Json8601Deserializer;
import at.itopen.mapillary.ISO8601.Json8601Serializer;
import at.itopen.mapillary.Mapillary;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
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

    /**
     * @return the ca
     */
    public int getCa() {
        return ca;
    }

    /**
     * @param ca the ca to set
     */
    public void setCa(int ca) {
        this.ca = ca;
    }

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
     * @return the camera_model
     */
    public String getCamera_model() {
        return camera_model;
    }

    /**
     * @param camera_model the camera_model to set
     */
    public void setCamera_model(String camera_model) {
        this.camera_model = camera_model;
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
     * @return the sequence_key
     */
    public String getSequence_key() {
        return sequence_key;
    }

    /**
     * @param sequence_key the sequence_key to set
     */
    public void setSequence_key(String sequence_key) {
        this.sequence_key = sequence_key;
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
     * @param fetchSize
     * @return
     */
    public BufferedImage fetchImage(Mapillary mapillary, Mapillary.IMAGE_FETCH_SIZE fetchSize) {
        return mapillary.getImage(this, fetchSize);
    }

}
