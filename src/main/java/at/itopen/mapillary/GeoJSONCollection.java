/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import java.util.ArrayList;
import java.util.List;

/**
 * List of geoJSON Objects
 *
 * @author roland
 * @param <T> the Data Object Class
 */
public abstract class GeoJSONCollection<T> extends PageableResult {

    private String type;
    private List<GeoJSON<T>> features;

    /**
     * Return all Enries as List
     *
     * @return a List of all Entries
     */
    public List<T> asList() {
        List<T> values = new ArrayList<>();
        for (GeoJSON<T> val : features) {
            T data = val.getProperties();
            update(data, val);
            values.add(data);
        }
        return values;
    }

    /**
     * Update the Object with GPS Info
     *
     * @param data the Object
     * @param original the Original GeoJson data
     */
    protected abstract void update(T data, GeoJSON<T> original);

    /**
     * Get the Geojson Type
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Return a List of all Features (means Objects)
     *
     * @return the features
     */
    public List<GeoJSON<T>> getFeatures() {
        for (GeoJSON<T> data : features) {
            update(data.getProperties(), data);
        }
        return features;
    }

}
