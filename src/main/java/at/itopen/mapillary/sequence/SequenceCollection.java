/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.sequence;

import at.itopen.mapillary.GPSData;
import at.itopen.mapillary.GeoJSON;
import at.itopen.mapillary.GeoJSONCollection;
import java.util.ArrayList;
import java.util.List;

/**
 * A Collection of Sequences
 *
 * @author roland
 */
public class SequenceCollection extends GeoJSONCollection<Sequence> {

    /**
     * Fix the GPS data. It is in the GeoJSON and has to be transferred into the
     * Object
     *
     * @param data The final Object
     * @param original Original GeoJSON Data
     */
    @Override
    protected void update(Sequence data, GeoJSON<Sequence> original) {
        if (original.getGeometry() != null) {
            if (original.getGeometry().getCoordinates() != null) {
                List<GPSData> erg = new ArrayList<>();
                for (List<Double> pos : original.getGeometry().getCoordinates()) {
                    if (pos.size() == 2) {
                        erg.add(new GPSData(pos.get(0), pos.get(1)));
                    }
                }
                data.getCoordinateProperties().setGps(erg);
            }
        }
    }
}
