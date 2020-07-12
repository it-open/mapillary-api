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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapillary m = new Mapillary();
        m.startOAuthServer(null);
        m.startOAuth(Mapillary.SCOPE.USER_READ);
        m.waitforAccess(50);
    }

}
