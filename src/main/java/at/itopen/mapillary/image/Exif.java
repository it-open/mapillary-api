/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.image;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.ExifToolOptions;
import com.thebuzzmedia.exiftool.Tag;
import com.thebuzzmedia.exiftool.core.StandardOptions;
import com.thebuzzmedia.exiftool.core.StandardTag;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Exif Editor is special for Mapillary Data Information. You can edit all
 * Fields needed for an successful Upload
 *
 * @author roland
 */
public class Exif {

    File imageFile;
    private static ExifTool exifTool = new ExifToolBuilder().build();
    ExifToolOptions options = StandardOptions.builder().withOverwiteOriginalInPlace().build();

    Map<Tag, String> data;

    /**
     * Open an existing Image File. All Tags will be saved. You can modify them.
     *
     * @param imageFile The File Object of an existing Image file (JPEG)
     */
    public Exif(File imageFile) {
        this.imageFile = imageFile;
        try {
            data = new HashMap<>();
            for (Map.Entry<Tag, String> e : exifTool.getImageMeta(imageFile).entrySet()) {
                data.put(e.getKey(), e.getValue());
            }
        } catch (IOException ex) {
            Logger.getLogger(Exif.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Could the file be successfully be read?
     *
     * @return True if everyting is OK, False if there where Errors.
     */
    public boolean isOpen() {
        return (!data.isEmpty());
    }

    /**
     * Set the Software Field in the Exifinfo
     *
     * @param name The Software used to create the Image
     */
    public void setSoftware(String name) {
        data.put(StandardTag.SOFTWARE, name);
    }

    /**
     * Set the Subject Field in the Exifinfo
     *
     * @param description New Suject Text
     */
    public void setDescription(String description) {
        data.put(StandardTag.SUBJECT, description);
    }

    /**
     * Set the GPS Data in the Exifinfo
     *
     * @param lat Latitude as double
     * @param lon Longitude as double
     */
    public void setGPS(double lat, double lon) {
        data.put(StandardTag.GPS_LATITUDE_REF, (lat > 0) ? "N" : "S");
        data.put(StandardTag.GPS_LONGITUDE_REF, (lon > 0) ? "E" : "W");

        data.put(StandardTag.GPS_LATITUDE, ("" + lat).replace(',', '.'));
        data.put(StandardTag.GPS_LONGITUDE, ("" + lon).replace(',', '.'));
    }

    /**
     * Set the Looking direction from 0-360 Degrees everything below 0 will be
     * converted to positive. As well as Values greater 360 will get normalized
     *
     * @param dir the new Direction
     */
    public void setDirection(double dir) {
        while (dir < 0) {
            dir += 360;
        }
        while (dir > 360) {
            dir -= 360;
        }
        data.put(StandardTag.GPS_BEARING, ("" + dir).replace(',', '.'));
        data.put(StandardTag.GPS_BEARING_REF, "T");
    }

    /**
     * Image Orientation Normal, Rotated
     *
     * @param orientaion the new Orientation from 0-9
     */
    public void setOrientation(long orientaion) {
        if (orientaion < 0) {
            return;
        }
        if (orientaion > 9) {
            return;
        }

        data.put(StandardTag.ORIENTATION, ("" + orientaion).replace(',', '.'));
    }

    /**
     * The Camera Model and Maker
     *
     * @param make The Maker of the Camera (Logitec)
     * @param model The Camera Model (C270)
     */
    public void setCamera(String make, String model) {
        data.put(StandardTag.MAKE, make);
        data.put(StandardTag.MODEL, model);
    }

    /**
     * Write the Information to the Image file. The File will be overwritten.
     * For a very short time there will be a temporary file with [name]_original
     */
    public void write() {
        try {
            exifTool.setImageMeta(imageFile, options, data);
        } catch (IOException ex) {
            Logger.getLogger(Exif.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
