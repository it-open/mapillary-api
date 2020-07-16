/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.user;

/**
 * User statistics
 *
 * @author roland
 */
public class UserStatistic {

    private UserBlur blurs;
    private UserEdits edits;
    private UserImages images;
    private UserSequences sequences;
    private String user_key;

    /**
     * @return the blurs
     */
    public UserBlur getBlurs() {
        return blurs;
    }

    /**
     * @return the edits
     */
    public UserEdits getEdits() {
        return edits;
    }

    /**
     * @return the images
     */
    public UserImages getImages() {
        return images;
    }

    /**
     * @return the sequences
     */
    public UserSequences getSequences() {
        return sequences;
    }

    /**
     * @return the user_key
     */
    public String getUser_key() {
        return user_key;
    }

}
