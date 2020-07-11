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
public class Mapillary {

    private static String rootEndpoint = "https://a.mapillary.com/3";
    private String clientID;

    public Mapillary(String clientID) {
        this.clientID = clientID;
    }

}
