/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import java.util.ArrayList;
import java.util.List;

/**
 * Geometry means the GPS coordiantes
 *
 * @author roland
 */
public class GeoJSONGeometry {

    private String type;
    private List<List<Double>> coordinates;

    /**
     * make a new List of Coordinates
     */
    public GeoJSONGeometry() {
        coordinates = new ArrayList<>();
    }

    /**
     * List Type (point-just singe or line- multiple)
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the Coordinates
     *
     * @return the coordinates
     */
    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

}
