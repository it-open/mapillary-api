/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import at.itopen.mapillary.image.Image;
import at.itopen.mapillary.user.UserCollection;
import at.itopen.mapillary.user.User;
import at.itopen.mapillary.user.UserFilter;
import at.itopen.mapillary.image.ImageFilter;
import at.itopen.mapillary.image.ImageCollection;
import at.itopen.mapillary.sequence.SequenceFilter;
import at.itopen.mapillary.sequence.SequenceCollection;
import at.itopen.mapillary.sequence.UploadSequence;
import at.itopen.mapillary.sequence.UploadSequenceCollection;
import at.itopen.mapillary.user.UserStatistic;
import at.itopen.simplerest.RestHttpServer;
import at.itopen.simplerest.client.RestClient;
import at.itopen.simplerest.client.RestResponse;
import at.itopen.simplerest.conversion.ContentType;
import at.itopen.simplerest.conversion.Conversion;
import at.itopen.simplerest.endpoints.GetEndpoint;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
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
 *
 * @author roland
 */
public class Mapillary {

    private static String rootEndpoint = "https://a.mapillary.com/v3";
    private static String ClientID = "UzZRbjZEUm1jNGFsNi1CS3g3RjNydzo4MzcyMjAyODQwOGQ1M2Qy";
    private static String RedirectUrl = "http://localhost:9876/token";
    private final static String IMAGE_FETCH_URL = "https://images.mapillary.com/{key}/thumb-{size}.jpg";

    private RestHttpServer httpserver;
    private String access_token = null;
    private Boolean access = null;

    public Mapillary() {

    }

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
                System.out.println(access_token);
                access = access_token != null;
                conversion.getResponse().setContentType(ContentType.HTML);
                conversion.getResponse().setData("<html><h1>READY</h1></html>");
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

    public void waitforAccess(int timeoutSeconds) {
        timeoutSeconds = timeoutSeconds * 100;
        while (access == null) {
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mapillary.class.getName()).log(Level.SEVERE, null, ex);
            }
            timeoutSeconds--;
            if (timeoutSeconds == 0) {
                return;
            }
        }
        if (Boolean.TRUE.equals(access)) {
            httpserver.shutdown();
            //provisionAccessToken();
        }

    }

    public void provisionAccessToken() {
        RestClient rc = new RestClient(rootEndpoint + "/oauth/token", RestClient.REST_METHOD.POST);
        rc.setMultipart(false);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        //  rc.setParameter("client_secret", ClientSecret);
        //  rc.setParameter("redirect_uri", RedirectUrl);
        //  rc.setParameter("grant_type", "bearer");
        //  rc.setParameter("code", access_token);
        RestResponse rr = rc.toSingle(true);
        System.out.println(rr.getDataAsString());
    }

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

    public UploadSequence getUpload(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/" + key, RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);
        return rr.getResponse(UploadSequence.class);
    }

    public void publishUpload(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/" + key + "/closed", RestClient.REST_METHOD.PUT);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        rc.toSingle(true);
    }

    public void deleteUpload(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/" + key, RestClient.REST_METHOD.DELETE);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        rc.toSingle(true);
    }

    public UploadSequenceCollection getOpenUpload() {
        RestClient rc = new RestClient(rootEndpoint + "/me/uploads/", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);
        return rr.getResponse(UploadSequenceCollection.class);
    }

    public ImageCollection getImages(ImageFilter filter) {
        RestClient rc = new RestClient(rootEndpoint + "/images", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        filter.makeFilterParams(rc);
        RestResponse rr = rc.toSingle(true);
        ImageCollection ic = rr.getResponse(ImageCollection.class);
        ic.parsePageable(rr.getHeader("link"), filter);
        return ic;
    }

    public UserCollection getUsers(UserFilter filter) {
        RestClient rc = new RestClient(rootEndpoint + "/users", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        filter.makeFilterParams(rc);
        RestResponse rr = rc.toSingle(true);
        UserCollection uc = rr.getResponse(UserCollection.class);
        uc.parsePageable(rr.getHeader("link"), filter);
        return uc;
    }

    public enum IMAGE_FETCH_SIZE {
        i320, i640, i1024, i2048
    };

    public BufferedImage getImage(Image image, IMAGE_FETCH_SIZE fetchSize) {
        return getImage(image.getKey(), fetchSize);
    }

    public BufferedImage getImage(String imageKey, IMAGE_FETCH_SIZE fetchSize) {
        try {
            String surl = IMAGE_FETCH_URL;
            surl = surl.replace("{key}", imageKey);
            surl = surl.replace("{size}", fetchSize.name().substring(1));

            URL url = new URL(surl);
            return ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(Mapillary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User getUser(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/users/" + key, RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);
        return rr.getResponse(User.class);
    }

    public User getMe() {
        RestClient rc = new RestClient(rootEndpoint + "/me", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);
        return rr.getResponse(User.class);
    }

    public UserStatistic getUserStatistc(String key) {
        RestClient rc = new RestClient(rootEndpoint + "/users/" + key + "/stats", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        RestResponse rr = rc.toSingle(true);
        return rr.getResponse(UserStatistic.class);

    }

    public Boolean hasAccess() {
        return access;
    }

    public static enum SCOPE {
        USER_READ, USER_WRITE, USER_EMAIL, PUBLIC_WRITE, PUBLIC_UPLOAD, PRIVATE_READ, PRIVATE_WRITE, PRIVATE_UPLOAD, MAPILLARY_USER
    };

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
            Logger.getLogger(Mapillary.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void setClientID(String ClientID) {
        Mapillary.ClientID = ClientID;
    }

    public static String getClientID() {
        return ClientID;
    }

    public static void setRedirectUrl(String RedirectUrl) {
        Mapillary.RedirectUrl = RedirectUrl;
    }

    public static String getRedirectUrl() {
        return RedirectUrl;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

}
