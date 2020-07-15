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
     * @return the about
     */
    public String getAbout() {
        return about;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * @return
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
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param mapillary
     * @param filter
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
     *
     * @param mapillary
     * @param filter
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
