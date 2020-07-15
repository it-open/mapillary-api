/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roland
 */
public class GeoJSONCollection<T> extends PageableResult {

    private String type;
    private List<GeoJSON<T>> features;

    public List<T> asList() {
        List<T> values = new ArrayList<>();
        for (GeoJSON<T> val : features) {
            values.add(val.getProperties());
        }
        return values;
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
