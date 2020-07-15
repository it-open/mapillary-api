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
public class GeoJSONGeometry {

    private String type;
    private List<List<Double>> coordinates;

    /**
     *
     */
    public GeoJSONGeometry() {
        coordinates = new ArrayList<>();
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
     * @return the coordinates
     */
    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

}
