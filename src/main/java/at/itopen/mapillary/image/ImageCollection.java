/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.image;

import at.itopen.mapillary.GPSData;
import at.itopen.mapillary.GeoJSON;
import at.itopen.mapillary.GeoJSONCollection;
import java.util.List;

/**
 * A Collection of Images
 *
 * @author roland
 */
public class ImageCollection extends GeoJSONCollection<Image> {

    /**
     * Fix the GPS data. It is in the GeoJSON and has to be transferred into the
     * Object
     *
     * @param data The final Object
     * @param original Original GeoJSON Data
     */
    @Override
    protected void update(Image data, GeoJSON<Image> original) {
        if (original.getGeometry() != null) {
            if (original.getGeometry().getCoordinates() != null) {
                if (original.getGeometry().getCoordinates().size() == 1) {
                    List<Double> pos = original.getGeometry().getCoordinates().get(0);
                    if (pos.size() == 2) {
                        data.setGps(new GPSData(pos.get(0), pos.get(1)));
                    }
                }
            }
        }
    }
}
