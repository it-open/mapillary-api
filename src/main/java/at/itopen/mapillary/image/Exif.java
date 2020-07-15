/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.image;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.Tag;
import com.thebuzzmedia.exiftool.core.StandardTag;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roland
 */
public class Exif {

    File imageFile;
    private static ExifTool exifTool = new ExifToolBuilder().build();
    Map<Tag, String> data;

    /**
     *
     * @param imageFile
     */
    public Exif(File imageFile) {
        try {
            data = exifTool.getImageMeta(imageFile);
        } catch (IOException ex) {
            Logger.getLogger(Exif.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public boolean isOpen() {
        return (data != null);
    }

    /**
     *
     * @param name
     */
    public void setSoftware(String name) {
        data.put(StandardTag.SOFTWARE, name);
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        data.put(StandardTag.COMMENT, description);
    }

    private String decimal2dms(double val) {
        long Seconds = (long) Math.round(Math.abs(val * 3600));
        long Degrees = Seconds / 3600;
        Seconds = Seconds % 3600;
        long Minutes = Seconds / 60;
        Seconds %= 60;
        return String.format("%i° %i' %i", Degrees, Minutes, Seconds);
    }

    /**
     *
     * @param lat
     * @param lon
     */
    public void setGPS(double lat, double lon) {
        data.put(StandardTag.GPS_LATITUDE_REF, (lat > 0) ? "N" : "S");
        data.put(StandardTag.GPS_LONGITUDE_REF, (lon > 0) ? "E" : "W");

        data.put(StandardTag.GPS_LATITUDE, decimal2dms(lat));
        data.put(StandardTag.GPS_LONGITUDE, decimal2dms(lon));
    }

    /**
     *
     * @param dir
     */
    public void setDirection(double dir) {
        data.put(StandardTag.GPS_BEARING, ("" + dir).replace(',', '.'));
        data.put(StandardTag.GPS_BEARING_REF, "T");
    }

    /**
     *
     * @param orientaion
     */
    public void setOrientation(long orientaion) {
        data.put(StandardTag.ORIENTATION, ("" + orientaion).replace(',', '.'));
    }

    /**
     *
     * @param make
     * @param model
     */
    public void setCamera(String make, String model) {
        data.put(StandardTag.MAKE, make);
        data.put(StandardTag.MODEL, model);
    }

    /**
     *
     */
    public void write() {
        try {
            exifTool.setImageMeta(imageFile, data);
        } catch (IOException ex) {
            Logger.getLogger(Exif.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
