/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

/**
 *
 * @author roland
 * @param <T>
 */
public class GeoJSON<T> {

    private String type;
    private GeoJSONGeometry geometry;
    private T properties;

    /**
     *
     */
    public GeoJSON() {
        geometry = new GeoJSONGeometry();
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
     * @return the geometry
     */
    public GeoJSONGeometry getGeometry() {
        return geometry;
    }

    /**
     * @param geometry the geometry to set
     */
    public void setGeometry(GeoJSONGeometry geometry) {
        this.geometry = geometry;
    }

    /**
     * @return the properties
     */
    public T getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(T properties) {
        this.properties = properties;
    }

}
