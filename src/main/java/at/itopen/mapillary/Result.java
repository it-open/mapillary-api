/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

/**
 *
 * @author roland
 */
public class Result {

    private int statusCode;
    private String errorMessage;
    private String errorInvalidParameter;
    private String errorMissingKey;

    /**
     *
     * @return
     */
    public boolean isError() {
        return statusCode >= 400;
    }

}
