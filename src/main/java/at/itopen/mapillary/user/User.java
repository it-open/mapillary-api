/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.user;

import at.itopen.mapillary.ISO8601.Json8601Deserializer;
import at.itopen.mapillary.ISO8601.Json8601Serializer;
import at.itopen.mapillary.Mapillary;
import at.itopen.mapillary.image.ImageCollection;
import at.itopen.mapillary.image.ImageFilter;
import at.itopen.mapillary.sequence.SequenceCollection;
import at.itopen.mapillary.sequence.SequenceFilter;
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
 * The User Metadata
 *
 * @author roland
 */
public class User {

    private String about;
    private String avatar;

    @JsonSerialize(using = Json8601Serializer.class)
    @JsonDeserialize(using = Json8601Deserializer.class)
    private Date created_at;
    private String key;
    private String username;

    /**
     * About or Bio
     *
     * @return the about
     */
    public String getAbout() {
        return about;
    }

    /**
     * Avatar Image url
     *
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Return the Avatar Image as BufferedImage
     *
     * @return Avatar Image or null if error
     */
    public BufferedImage fetchAvatar() {
        try {

            URL url = new URL(getAvatar());
            return ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(Mapillary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * When was the User created?
     *
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * Unique User ID
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * The User name
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get all Sequences which belongs to the User
     *
     * @param mapillary Mapillary Object for authentication
     * @param filter Null if no Filter, or just add what you want (User is
     * filled in by this function)
     * @return
     */
    public SequenceCollection fetchSequences(Mapillary mapillary, SequenceFilter filter) {
        if (filter == null) {
            filter = new SequenceFilter();
        }
        filter.addUser(this);
        return mapillary.getSequences(filter);
    }

    /**
     * Get all Images which belongs to the User
     *
     * @param mapillary Mapillary Object for authentication
     * @param filter Null if no Filter, or just add what you want (User is
     * filled in by this function)
     * @return
     */
    public ImageCollection fetchImages(Mapillary mapillary, ImageFilter filter) {
        if (filter == null) {
            filter = new ImageFilter();
        }
        filter.addUser(this);
        return mapillary.getImages(filter);
    }

}
