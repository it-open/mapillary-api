/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary.user;

/**
 *
 * @author roland
 */
public class UserEdits {

    private Long approved;
    private Long pending;
    private Long rejected;
    private Long total;
    private Long approved_count;
    private Long pending_count;
    private Long rejected_count;
    private Long total_count;

    /**
     * @return the approved
     */
    public Long getApproved() {
        return approved;
    }

    /**
     * @return the pending
     */
    public Long getPending() {
        return pending;
    }

    /**
     * @return the rejected
     */
    public Long getRejected() {
        return rejected;
    }

    /**
     * @return the total
     */
    public Long getTotal() {
        return total;
    }

    /**
     * @return the approved_count
     */
    public Long getApproved_count() {
        return approved_count;
    }

    /**
     * @return the pending_count
     */
    public Long getPending_count() {
        return pending_count;
    }

    /**
     * @return the rejected_count
     */
    public Long getRejected_count() {
        return rejected_count;
    }

    /**
     * @return the total_count
     */
    public Long getTotal_count() {
        return total_count;
    }

}
