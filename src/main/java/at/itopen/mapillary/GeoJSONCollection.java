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
 * @param <T>
 */
public abstract class GeoJSONCollection<T> extends PageableResult {

    private String type;
    private List<GeoJSON<T>> features;

    /**
     *
     * @return
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

    protected abstract void update(T data, GeoJSON<T> original);

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the features
     */
    public List<GeoJSON<T>> getFeatures() {
        for (GeoJSON<T> data : features) {
            update(data.getProperties(), data);
        }
        return features;
    }

}
