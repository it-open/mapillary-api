/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import at.itopen.mapillary.sequence.SequenceFilter;
import at.itopen.mapillary.sequence.SequenceCollection;
import at.itopen.mapillary.sequence.UploadSequence;
import at.itopen.mapillary.user.User;
import java.text.ParseException;

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
        // m.startOAuthServer(null);
        // m.startOAuth(Mapillary.SCOPE.USER_WRITE, Mapillary.SCOPE.PRIVATE_UPLOAD, Mapillary.SCOPE.PUBLIC_UPLOAD);
        // m.waitforAccess(50);
        m.setAccess_token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJtcHkiLCJzdWIiOiJTNlFuNkRSbWM0YWw2LUJLeDdGM3J3IiwiYXVkIjoiVXpaUmJqWkVVbTFqTkdGc05pMUNTM2czUmpOeWR6bzRNemN5TWpBeU9EUXdPR1ExTTJReSIsImlhdCI6MTU5NDgwNTk4NzY0NCwianRpIjoiZGM1Zjk5YWEyMzE3OGQ5NTNhZGQ1MTQ0OGYyZmY1ZDEiLCJzY28iOlsidXNlcjp3cml0ZSIsInByaXZhdGU6dXBsb2FkIiwicHVibGljOnVwbG9hZCJdLCJ2ZXIiOjF9.3bxmfMDDcujUBp5QcgGC7bFmxxPTn33YC1GUgXKQkTs");
        System.out.println(m.getAccess_token());
        SequenceFilter sf = new SequenceFilter();
        sf.setPerPage(2);
        SequenceCollection coll = m.getSequences(sf);
        System.out.println(coll.asList().get(0).getKey());
        /*UserFilter userFilter = new UserFilter();
        userFilter.addUserName("schu_r");
        UserCollection uc = m.getUsers(userFilter);
        System.out.println(uc.get(0).getAbout());
        BufferedImage image = uc.get(0).fetchAvatar();
        try {
            ImageIO.write(image, "PNG", new File("roland.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        User user = m.getMe();
        System.out.println(user.getUsername());
        //UploadSequence uploadSequence = m.startUpload();
        UploadSequence uploadSequence = m.getUpload("cB17T61o9YShiUO3mnAvvl");
        System.out.println(uploadSequence.getKey() + " - " + uploadSequence.getStatus());
    }

}
