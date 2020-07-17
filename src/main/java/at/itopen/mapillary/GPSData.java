/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

/**
 * a GPS Point with Longitude and Latitude (Used in Image and Sequence)
 *
 * @author roland
 */
public class GPSData {

    private double longitude;
    private double latitude;

    /**
     * Create a new GPS Point
     *
     * @param longitude
     * @param latitude
     */
    public GPSData(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Get the Longitude
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Get the Latitude
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

}
