/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

/**
 * Geo JSON Expression from Mapillary
 *
 * @author roland
 * @param <T> Class used for Paramter
 */
public class GeoJSON<T> {

    private String type;
    private GeoJSONGeometry geometry;
    private T properties;

    /**
     * Make a new Object
     */
    public GeoJSON() {
        geometry = new GeoJSONGeometry();
    }

    /**
     * Get the GeoJSON type
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the coordinates
     *
     * @return the geometry
     */
    public GeoJSONGeometry getGeometry() {
        return geometry;
    }

    /**
     * Get the data
     *
     * @return the properties
     */
    public T getProperties() {
        return properties;
    }

}
