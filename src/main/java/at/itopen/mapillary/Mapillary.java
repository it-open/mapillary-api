/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import at.itopen.mapillary.image.Image;
import at.itopen.mapillary.image.ImageCollection;
import at.itopen.mapillary.image.ImageFilter;
import at.itopen.mapillary.sequence.SequenceFilter;
import at.itopen.mapillary.sequence.SequenceCollection;
import at.itopen.mapillary.sequence.UploadSequence;
import at.itopen.mapillary.sequence.UploadSequenceCollection;
import at.itopen.mapillary.user.User;
import at.itopen.mapillary.user.UserCollection;
import at.itopen.mapillary.user.UserFilter;
import at.itopen.mapillary.user.UserStatistic;
import at.itopen.simplerest.RestHttpServer;
import at.itopen.simplerest.client.RestClient;
import at.itopen.simplerest.client.RestFile;
import at.itopen.simplerest.client.RestResponse;
import at.itopen.simplerest.conversion.ContentType;
import at.itopen.simplerest.conversion.Conversion;
import at.itopen.simplerest.endpoints.GetEndpoint;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * The Main Mapillary function class
 *
 * @author roland
 */
public class Mapillary {

    private static String rootEndpoint = "https://a.mapillary.com/v3";
    private String ClientID = "UzZRbjZEUm1jNGFsNi1CS3g3RjNydzo4MzcyMjAyODQwOGQ1M2Qy";
    private String ClientSecret = "ODUzYWNhNTBlMDNlZjM2ZTIyYjU3Y2Y1NjBmOGIyMzc=";
    private String RedirectUrl = "http://localhost:9876/token";
    public final static String IMAGE_FETCH_URL = "https://images.mapillary.com/{key}/thumb-{size}.jpg";

    private RestHttpServer httpserver;
    private String access_token = null;

    /**
     * start the Oauth Mecahnism. A Webserver is started to wait for the token
     * http://localhost:[port]/token
     *
     * @param port Port of the Webserver
     */
    public void startOAuthServer(Integer port) {
        if (httpserver != null) {
            return;
        }

        if (port == null) {
            port = 9876;
        }
        httpserver = new RestHttpServer(port);
        httpserver.getRootEndpoint().addRestEndpoint(new GetEndpoint("token") {
            @Override
            public void Call(Conversion conversion, Map<String, String> params) {
                access_token = conversion.getRequest().getParam("access_token");
                conversion.getResponse().setContentType(ContentType.HTML);
                conversion.getResponse().setData("<html><h1>Authorized</h1>Please Close this window. Thank you!</html>");
            }
        });
        final RestHttpServer h = httpserver;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    h.run();
                } catch (Exception ex) {
                    Logger.getLogger(Mapillary.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }

    /**
     * Wait for the Accesstoken to arrive. If successful the webserver will
     * shutdown
     *
     * @param timeoutSeconds (how long to wait)
     * @return true if successfully authorized
     */
    public boolean waitforAccess(int timeoutSeconds) {
        timeoutSeconds = timeoutSeconds * 100;
        while (!hasAccess()) {
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mapillary.class.getName()).log(Level.SEVERE, null, ex);
            }
            timeoutSeconds--;
            if (timeoutSeconds == 0) {
                return false;
            }
        }
        if (hasAccess()) {
            httpserver.shutdown();
            return true;
        }
        return false;
    }

    /**
     * Get a List of all Sequences
     *
     * @param filter Filter Object for Sequences
     * @return a Collection of Objects
     */
    public SequenceCollection getSequences(SequenceFilter filter) {
        RestClient rc = new RestClient(rootEndpoint + "/sequences", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        filter.makeFilterParams(rc);
        RestResponse rr = rc.toSingle(true);
        SequenceCollection sc = rr.getResponse(SequenceCollection.class);
        sc.parsePageable(rr.getHeader("link"), filter);
        return sc;
    }

    /**
     * Create a upload sequence
     *
     * @return A UploadSequence Object or null on error
     */
    public UploadSequence startUpload() {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads?client_id=" + ClientID, RestClient.REST_METHOD.POST);
        rc.authKey(access_token);
        Map<String, String> params = new HashMap<>();
        params.put("type", "images/sequence");
        rc.setJson(params);

        RestResponse rr = rc.toSingle(true);
        System.out.println(rr.getStatusCode());
        System.out.println(rr.getDataAsString());
        return rr.getResponse(UploadSequence.class);
    }

    /**
     * Upload an Image to the UploadSequence
     *
     * @param file The Image File
     * @param uploadSequence The UploadSequence
     */
    public void uploadImage(File file, UploadSequence uploadSequence) {
        RestClient rc = new RestClient(uploadSequence.getUrl(), RestClient.REST_METHOD.POST);
        for (Map.Entry<String, String> e : uploadSequence.getFields().entrySet()) {
            rc.setParameter(e.getKey(), e.getValue());
        }
        rc.setParameter("key", uploadSequence.getKey_prefix() + file.getName());
        rc.addFile(file.getName(), new RestFile(file, org.apache.http.entity.ContentType.IMAGE_JPEG, file.getName()));
        rc.toSingle(true);

    }

    /**
     * Get an UploadSequence by its key
     *
     * @param key Uniqe key of sequence
     * @return the Uploadsequence or null if error or no found
     */
    public UploadSequence getUploadSequence(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/" + key, RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);

        return rr.getResponse(UploadSequence.class
        );
    }

    /**
     * Publish an Upload Sequence
     *
     * @param key Key of the Sequence
     */
    public void publishUpload(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/" + key + "/closed", RestClient.REST_METHOD.PUT);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        rc.toSingle(true);
    }

    /**
     * delete an UploadSequence
     *
     * @param key key of the sequence
     */
    public void deleteUpload(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/" + key, RestClient.REST_METHOD.DELETE);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        rc.toSingle(true);
    }

    /**
     * Get a List of all open Upload Sequences
     *
     * @return List of Sequences
     */
    public UploadSequenceCollection getOpenUpload() {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);

        return rr.getResponse(UploadSequenceCollection.class
        );
    }

    /**
     * Get all Images according to Filter
     *
     * @param filter FilterObject
     * @return List of Images
     */
    public ImageCollection getImages(ImageFilter filter) {
        RestClient rc = new RestClient(rootEndpoint + "/images", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        filter.makeFilterParams(rc);
        RestResponse rr = rc.toSingle(true);
        ImageCollection ic = rr.getResponse(ImageCollection.class
        );
        ic.parsePageable(rr.getHeader("link"), filter);
        return ic;
    }

    /**
     * Get All Users according to Filter
     *
     * @param filter FilterObject
     * @return List of users
     */
    public UserCollection getUsers(UserFilter filter) {
        RestClient rc = new RestClient(rootEndpoint + "/users", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        filter.makeFilterParams(rc);
        RestResponse rr = rc.toSingle(true);
        UserCollection uc = rr.getResponse(UserCollection.class
        );
        uc.parsePageable(rr.getHeader("link"), filter);
        return uc;
    }

    /**
     * Size Of Images for Image downloading
     */
    public enum IMAGE_FETCH_SIZE {

        /**
         * 320px
         */
        i320,
        /**
         * 640px
         */
        i640,
        /**
         * 1024px
         */
        i1024,
        /**
         * 2048px
         */
        i2048
    };

    /**
     * get an Image Data
     *
     * @param image The Image Metadata
     * @param fetchSize Size of the Image to fetch
     * @return the Image or null on error
     */
    public BufferedImage getImage(Image image, IMAGE_FETCH_SIZE fetchSize) {
        return getImage(image.getKey(), fetchSize);
    }

    /**
     * get an Image Data
     *
     * @param imageKey the Image key
     * @param fetchSize Size of the image to fetch
     * @return the Image or null on error
     */
    public BufferedImage getImage(String imageKey, IMAGE_FETCH_SIZE fetchSize) {
        try {
            String surl = IMAGE_FETCH_URL;
            surl = surl.replace("{key}", imageKey);
            surl = surl.replace("{size}", fetchSize.name().substring(1));

            URL url = new URL(surl);
            return ImageIO.read(url);

        } catch (IOException ex) {
            Logger.getLogger(Mapillary.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Get a User by its key
     *
     * @param key Key of the User
     * @return User Metadata
     */
    public User getUser(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/users/" + key, RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);

        return rr.getResponse(User.class
        );
    }

    /**
     * Get the logged in User
     *
     * @return return the User Metadata of the logged in User
     */
    public User getMe() {
        RestClient rc = new RestClient(rootEndpoint + "/me", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);

        return rr.getResponse(User.class
        );
    }

    /**
     * Get Statistics about the User
     *
     * @param key Key of the User
     * @return UserStatistics Metadata
     */
    public UserStatistic getUserStatistc(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/users/" + key + "/stats", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);

        return rr.getResponse(UserStatistic.class
        );

    }

    /**
     * Check if you have an Access token
     *
     * @return true is Access Token is here
     */
    public boolean hasAccess() {
        return (access_token != null);

    }

    /**
     * Scope of rights.
     * https://www.mapillary.com/developer/api-documentation/#scopes
     */
    public static enum SCOPE {

        /**
         *
         */
        USER_READ,
        /**
         *
         */
        USER_WRITE,
        /**
         *
         */
        USER_EMAIL,
        /**
         *
         */
        PUBLIC_WRITE,
        /**
         *
         */
        PUBLIC_UPLOAD,
        /**
         *
         */
        PRIVATE_READ,
        /**
         *
         */
        PRIVATE_WRITE,
        /**
         *
         */
        PRIVATE_UPLOAD,
        /**
         *
         */
        MAPILLARY_USER
    };

    /**
     * Open the Browser and request a Token
     *
     * @param scopes Which rights do you want?
     */
    public void startOAuth(SCOPE... scopes) {

        StringBuilder url = new StringBuilder();
        url.append("https://www.mapillary.com/connect?");
        url.append("client_id=").append(ClientID);
        url.append("&" + "response_type=" + "token");
        url.append("&redirect_uri=").append(RedirectUrl.replaceAll("/", "%2F"));

        StringBuilder scope = new StringBuilder();
        for (SCOPE scp : scopes) {
            String s = scp.name().toLowerCase();
            s = s.replace('_', ':');
            if (scope.length() > 0) {
                scope.append("%20");
            }
            scope.append(s);
        }
        url.append("&scope=").append(scope.toString());
        try {
            Desktop.getDesktop().browse(new URI(url.toString()));

        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Mapillary.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * The Client ID https://www.mapillary.com/dashboard/developers
     *
     * @param ClientID
     */
    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    /**
     * The Client ID https://www.mapillary.com/dashboard/developers
     *
     * @return
     */
    public String getClientID() {
        return ClientID;
    }

    /**
     * Callback Url https://www.mapillary.com/dashboard/developers
     *
     * @param RedirectUrl
     */
    public void setRedirectUrl(String RedirectUrl) {
        this.RedirectUrl = RedirectUrl;
    }

    /**
     * Callback url https://www.mapillary.com/dashboard/developers
     *
     * @return
     */
    public String getRedirectUrl() {
        return this.RedirectUrl;
    }

    /**
     * get an Access token via OAuth or just put it here
     *
     * @param access_token
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * get the Acces Token you got from Oauth
     *
     * @return
     */
    public String getAccess_token() {
        return access_token;
    }

}
