/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.sequence;

import at.itopen.mapillary.ISO8601.Json8601Deserializer;
import at.itopen.mapillary.ISO8601.Json8601Serializer;
import at.itopen.mapillary.Mapillary;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author roland
 */
public class UploadSequence {

    private Mapillary mapillary;

    /**
     *
     * @param mapillary
     */
    public void setMapillary(Mapillary mapillary) {
        this.mapillary = mapillary;
    }

    /**
     *
     */
    public void delete() {
        mapillary.deleteUpload(key);
    }

    /**
     *
     */
    public void publish() {
        mapillary.publishUpload(key);
    }

    @JsonSerialize(using = Json8601Serializer.class)
    @JsonDeserialize(using = Json8601Deserializer.class)
    private Date created_at;
    private Map<String, String> fields;
    private String client;
    private String key;
    private String ley_prefix;
    @JsonSerialize(using = Json8601Serializer.class)
    @JsonDeserialize(using = Json8601Deserializer.class)
    private Date signed_at;
    private String status;
    private String type;
    private String url;

    /**
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * @return the fields
     */
    public Map<String, String> getFields() {
        return fields;
    }

    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the ley_prefix
     */
    public String getLey_prefix() {
        return ley_prefix;
    }

    /**
     * @return the signed_at
     */
    public Date getSigned_at() {
        return signed_at;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

}
