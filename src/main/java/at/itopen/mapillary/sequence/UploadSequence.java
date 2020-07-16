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
 * The UploadSequence is a a Representation of an Upload Process. When you have
 * a sequence means that the Upload is initiated.
 *
 * @author roland
 */
public class UploadSequence {

    private Mapillary mapillary;

    /**
     * Used Internally the Mapillary Object for authentication
     *
     * @param mapillary
     */
    public void setMapillary(Mapillary mapillary) {
        this.mapillary = mapillary;
    }

    /**
     * Delte the Sequence. All Information and Images are lost
     */
    public void delete() {
        mapillary.deleteUpload(key);
    }

    /**
     * Publish the Sequence. All Images are being processed
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
    private String key_prefix;
    @JsonSerialize(using = Json8601Serializer.class)
    @JsonDeserialize(using = Json8601Deserializer.class)
    private Date signed_at;
    private String status;
    private String type;
    private String url;

    /**
     * When was the sequence created
     *
     * @return the created_at
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * Fields from Mapillary needed for Upload of the Images (Pass Through)
     *
     * @return the fields
     */
    public Map<String, String> getFields() {
        return fields;
    }

    /**
     * The client used for Upload (ClientID)
     *
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * the unique id
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Key Prefix used for uploading images
     *
     * @return the key_prefix
     */
    public String getKey_prefix() {
        return key_prefix;
    }

    /**
     * Sequence published date ??
     *
     * @return the signed_at
     */
    public Date getSigned_at() {
        return signed_at;
    }

    /**
     * status of the Sequence (open/closed)
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sequence Type
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Url for Image Upload
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

}
