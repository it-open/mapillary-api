/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.itopen.mapillary;

import at.itopen.simplerest.RestHttpServer;
import at.itopen.simplerest.client.RestClient;
import at.itopen.simplerest.client.RestResponse;
import at.itopen.simplerest.conversion.ContentType;
import at.itopen.simplerest.conversion.Conversion;
import at.itopen.simplerest.endpoints.GetEndpoint;
import java.awt.Desktop;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roland
 */
public class Mapillary {

    private static String rootEndpoint = "https://a.mapillary.com/v3";
    private static String ClientID = "UzZRbjZEUm1jNGFsNi1CS3g3RjNydzpmYjM2MDJiNDA1ZGE1MDYw";
    private static String ClientSecret = "ZGE5MTMyOGZjODQzNjM1ZDdhMTVjMDEwYzJjNjIyYWQ=";
    private static String RedirectUrl = "http://localhost:9876/token";

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
        timeoutSeconds = timeoutSeconds * 10;
        while (access == null) {
            try {
                sleep(100);
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

    public void test() {
        RestClient rc = new RestClient(rootEndpoint + "/sequences", RestClient.REST_METHOD.GET);
        rc.authKey(access_token);
        rc.setParameter("client_id", ClientID);
        rc.setParameter("per_page", "2");
        RestResponse rr = rc.toSingle(true);
        SequenceCollection sc = rr.getResponse(SequenceCollection.class);
        System.out.println(sc.getFeatures().size());

    }

    public Boolean hasAccess() {
        return access;
    }

    public static enum SCOPE {
        USER_READ, USER_WRITE, USER_EMAIL, PUBLIC_WRITE, PUBLI_UPLOAD, PRIVATE_READ, PRIVATE_WRITE, PRIVATE_UPLOAD
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

    public static String getClientSecret() {
        return ClientSecret;
    }

    public static void setClientSecret(String ClientSecret) {
        Mapillary.ClientSecret = ClientSecret;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

}