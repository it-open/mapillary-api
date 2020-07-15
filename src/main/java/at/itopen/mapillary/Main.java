/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import at.itopen.mapillary.sequence.SequenceFilter;
import at.itopen.mapillary.sequence.SequenceCollection;
import at.itopen.mapillary.user.UserCollection;
import at.itopen.mapillary.user.UserFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author roland
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        Mapillary m = new Mapillary();
        //m.startOAuthServer(null);
        //m.startOAuth(Mapillary.SCOPE.USER_READ);
        //m.waitforAccess(50);
        m.setAccess_token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtcHkiLCJzdWIiOiJTNlFuNkRSbWM0YWw2LUJLeDdGM3J3IiwiYXVkIjoiVXpaUmJqWkVVbTFqTkdGc05pMUNTM2czUmpOeWR6cG1Zak0yTURKaU5EQTFaR0UxTURZdyIsImlhdCI6MTU5NDU2Mjg1NTA2NywianRpIjoiZTY1ZmFlNTZkMTc2N2Q3ODlkYTVhNTg1ZjhlNDMzMTIiLCJzY28iOlsidXNlcjpyZWFkIl0sInZlciI6MX0.TkT__jq1x3F2S6nQ_WR4hwEQGhmqi7D-yJJFXqhH1ac");
        SequenceFilter sf = new SequenceFilter();
        sf.setPerPage(2);
        SequenceCollection coll = m.getSequences(sf);
        System.out.println(coll.asList().get(0).getKey());
        UserFilter userFilter = new UserFilter();
        userFilter.addUserName("schu_r");
        UserCollection uc = m.getUsers(userFilter);
        System.out.println(uc.get(0).getAbout());
        BufferedImage image = uc.get(0).fetchAvatar();
        try {
            ImageIO.write(image, "PNG", new File("roland.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
