/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.sequence;

import at.itopen.mapillary.GPSData;
import java.util.List;

/**
 * Sequence Properites are the ImageList of the Squence
 *
 * @author roland
 */
public class SequenceProperties {

    private List<Double> cas;
    private List<String> image_keys;
    private List<GPSData> gps;

    /**
     * List of Orientations
     *
     * @return the cas
     */
    public List<Double> getCas() {
        return cas;
    }

    /**
     * List of Image Keys
     *
     * @return the image_keys
     */
    public List<String> getImage_keys() {
        return image_keys;
    }

    /**
     * List of GPS Coordinates
     *
     * @return
     */
    public List<GPSData> getGps() {
        return gps;
    }

    /**
     * Interal Used set the GPS gotm the GeoJSON
     *
     * @param gps
     */
    protected void setGps(List<GPSData> gps) {
        this.gps = gps;
    }

    /**
     * Check the Size of this Array
     *
     * @return
     */
    public int size() {
        int s = cas.size();
        if (image_keys.size() < s) {
            s = image_keys.size();
        }
        if (gps != null) {
            if (gps.size() < s) {
                s = gps.size();
            }
        }
        return s;
    }

    /**
     * Get an image from the List
     *
     * @param number index of the Image
     * @return The Image Metadata (Not the Image Object)
     */
    public Image get(int number) {
        if (number < 0) {
            return null;
        }
        if (number >= cas.size()) {
            return null;
        }
        if (number >= image_keys.size()) {
            return null;
        }
        if (number >= gps.size()) {
            return null;
        }
        if (gps != null) {
            return new Image(cas.get(number), image_keys.get(number), gps.get(number));
        } else {
            return new Image(cas.get(number), image_keys.get(number), null);
        }

    }

    /**
     * The Image Metadata Class
     */
    public class Image {

        double direction;
        String key;
        GPSData gps;

        /**
         * Create a new Image Metadata Class
         *
         * @param direction Image looking direction
         * @param key Image Key
         * @param gps GPS Coordinates
         */
        public Image(double direction, String key, GPSData gps) {
            this.direction = direction;
            this.key = key;
            this.gps = gps;
        }

        /**
         * get the GPS Data (might be null)
         *
         * @return
         */
        public GPSData getGps() {
            return gps;
        }

        /**
         * The image Key
         *
         * @return
         */
        public String getKey() {
            return key;
        }

        /**
         * the Image LookDirection
         *
         * @return
         */
        public double getDirection() {
            return direction;
        }

    }

}
