/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.sequence;

import java.util.List;

/**
 *
 * @author roland
 */
public class SequenceProperties {

    private List<Double> cas;
    private List<String> image_keys;

    /**
     * @return the cas
     */
    public List<Double> getCas() {
        return cas;
    }

    /**
     * @param cas the cas to set
     */
    public void setCas(List<Double> cas) {
        this.cas = cas;
    }

    /**
     * @return the image_keys
     */
    public List<String> getImage_keys() {
        return image_keys;
    }

    /**
     * @param image_keys the image_keys to set
     */
    public void setImage_keys(List<String> image_keys) {
        this.image_keys = image_keys;
    }

}
