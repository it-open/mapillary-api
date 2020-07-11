/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import java.util.List;

/**
 *
 * @author roland
 */
public class GeoJSONCollection<T> {

    private String type;
    private List<GeoJSON<T>> features;

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
     * @return the features
     */
    public List<GeoJSON<T>> getFeatures() {
        return features;
    }

    /**
     * @param features the features to set
     */
    public void setFeatures(List<GeoJSON<T>> features) {
        this.features = features;
    }

}
