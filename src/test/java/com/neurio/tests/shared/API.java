package com.neurio.tests.shared;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert on 5/30/2016.
 * API Class
 */
public class API {

    private static HttpClient unsafeHttpClient;

    static {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            unsafeHttpClient = HttpClients.custom().setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public static HttpClient getClient() {
        return unsafeHttpClient;
    }


    /**
     * Reformats the Req Body to the correct format for UTF-8
     *
     * @param params Req Body Keys and Values
     * @return String - Req Body Keys and Values in proper format
     */
    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    /**
     * Send a GET request
     *
     * @param token - Authorization token
     * @param url   - URL of GET Request
     * @return String - Response from API Call
     */
    public static String getRequest(String token, String url) throws Exception {

        URL obj = new URL(Common.getAPIModeURL() + url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        if (responseCode == 200) {
            return response.toString();
        } else {
            return "Error: \n" + response.toString();
        }
    }

    /**
     * Send a POST request
     *
     * @param url     - URL of GET Request
     * @param reqBody - POST Request Body
     * @return String - Response from API Call
     */
    public static String postRequest(String url,
                                     JSONObject reqBody) throws Exception {
        HttpClient creepyClient = getClient();
        Unirest.setHttpClient(creepyClient);
        HttpResponse<JsonNode> response = Unirest.post(Common.getAPIModeURL() + url).
                header("Content-Type", "application/x-www-form-urlencoded").
                body(reqBody).asJson();
        return response.getBody().toString();
    }

    /**
     * Send an authorized PATCH request
     *
     * @param token   - Authorization token
     * @param url     - URL of GET Request
     * @param reqBody - POST Request Body
     * @return String - Response from API Call
     */
    public static HttpResponse<JsonNode> patchRequestJSON(String token,
                                                          String url,
                                                          JSONObject reqBody) throws Exception {
        HttpClient creepyClient = getClient();
        Unirest.setHttpClient(creepyClient);
        System.out.println("Patching " + Common.getAPIModeURL() + url);
        System.out.println("Body " + reqBody.toString());
        HttpResponse<JsonNode> response = Unirest.patch(Common.getAPIModeURL() + url).
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(reqBody).asJson();
        return response;
    }

    /**
     * Send an authorized GET request
     *
     * @param token - Authorization token
     * @param url   - URL of GET Request
     * @return JSONObject - Response from API Call
     */
    public static HttpResponse<JsonNode> getRequestJSON(String token,
                                                        String url) throws Exception {
        HttpClient creepyClient = getClient();
        Unirest.setHttpClient(creepyClient);
        HttpResponse<JsonNode> response = Unirest.get(Common.getAPIModeURL() + url).
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).asJson();
        return response;
    }

    /**
     * Send an authorized POST request
     *
     * @param token - Authorization token
     * @param url   - URL of GET Request
     * @return JSONObject - Response from API Call
     */
    public static HttpResponse<JsonNode> postRequestJSON(String token,
                                                         String url,
                                                         JSONObject reqBody) throws Exception {
        HttpClient creepyClient = getClient();
        Unirest.setHttpClient(creepyClient);
        HttpResponse<JsonNode> response = Unirest.post(Common.getAPIModeURL() + url).
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(reqBody).asJson();
        return response;
    }

    /**
     * Send an authorized DELETE request
     *
     * @param token - Authorization token
     * @param url   - URL of GET Request
     * @return JSONObject - Response from API Call
     */
    public static HttpResponse<JsonNode> deleteRequestJSON(String token,
                                                           String url) throws Exception {
        HttpClient creepyClient = getClient();
        Unirest.setHttpClient(creepyClient);
        HttpResponse<JsonNode> response = Unirest.delete(Common.getAPIModeURL() + url).
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).asJson();
        return response;
    }

    /**
     * Send an authorized POST request
     *
     * @param token - Authorization token
     * @param url   - URL of GET Request
     * @return int - Status
     */
    public static HttpResponse<JsonNode> postRequestJSONArray(String token,
                                                              String url,
                                                              JSONArray reqBody) throws Exception {
        HttpClient creepyClient = getClient();
        Unirest.setHttpClient(creepyClient);
        HttpResponse<JsonNode> response = Unirest.post(Common.getAPIModeURL() + url).
                header("Content-Type", "application/json").
                header("Authorization", "Bearer " + token).
                body(reqBody).asJson();
        return response;
    }


    /**
     * Get an authorization token
     *
     * @return String - Token
     */
    public static String getAuthToken() {
        String authToken = "";
        try {
            HttpClient creepyClient = getClient();
            Unirest.setHttpClient(creepyClient);
            HttpResponse<JsonNode> response = Unirest.post(Common.getAPIModeURL() + "oauth2/token")
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic SDRrRWM3dzVRR21QaE96OGw4NjNkZzpOb3pmTFRnNlRLV1dhd2xXM3RXYXlB")
                    .body("grant_type=password&username=admin@energy-aware.com&password=bonny5_worktable")
                    .asJson();
            String token = response.getBody().getObject().getString(StringRef.ACCESS_TOKEN);
            authToken = token;
        } catch (Exception e) {
            System.out.println("exception happened");
            System.out.println(e.getMessage());
        }
        return authToken;
    }

    /**
     * Add a set of user release feature
     *
     * @param token  - Auth token
     * @param userID - User ID
     * @return String - Response
     */
    public static int addUserReleaseFeature(String token, String userID) {
        JSONObject obj = new JSONObject();
        obj.put("userId", userID);
        JSONObject releaseObj = new JSONObject();
        releaseObj.put("id", StringRef.RELEASE_FEATURE_ID);
        obj.put("releaseFeature", releaseObj);
        int reponse = 0;
        JSONArray list = new JSONArray();
        list.put(obj);
        try {
            reponse = postRequestJSONArray(token, "users/features", list).getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reponse;
    }

    /**
     * Get Install Code
     *
     * @param token    - Auth token
     * @param sensorID - Sensor ID
     * @return String - Response
     */
    public static JSONObject getSensor(String token, String sensorID) {
        JSONObject reponse = new JSONObject();
        try {
            reponse = getRequestJSON(token, "sensors/" + sensorID).getBody().getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reponse;
    }

    /**
     * Register a new User
     *
     * @param token       - Auth token
     * @param installCode - Install Code
     * @param userName    - User Name
     * @return String - Response
     */
    public static int registerNewUser(String token, String installCode, String userName, String password) {
        JSONObject reqBody = new JSONObject();
        reqBody.put("email", userName + "@neur.io");
        reqBody.put("name", userName);
        reqBody.put("password", password);
        reqBody.put("installCode", installCode);
        reqBody.put("timezone", "America/Vancouver");
        try {
            HttpResponse<JsonNode> reponse = postRequestJSON(token, "users/signup", reqBody);
            return reponse.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Add a new cobrand
     *
     * @param token      - Auth token
     * @param resellerID - Reseller ID
     * @return String - Response
     */
    public static int addCobrand(String token, String resellerID) {
        JSONObject reqBody = new JSONObject();
        reqBody.put("userId", resellerID);
        reqBody.put("name", "Rob company");
        reqBody.put("email", "bob@cobrand.com");
        reqBody.put("logoUrl", "http://logok.org/wp-content/uploads/2014/05/China-Unicom-Chinese-knot-logo.png");
        reqBody.put("url", "www.cobrand.com");
        reqBody.put("shortText", "T");
        reqBody.put("longText", "Text");
        reqBody.put("frontShortText", "Front");
        reqBody.put("frontShortText", "Back");
        try {
            HttpResponse<JsonNode> reponse = postRequestJSON(token, "sensors/cobrands", reqBody);
            return reponse.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Delete User
     *
     * @param token - Auth token
     * @param id    - User ID
     * @return String - Response
     */
    public static int deleteUser(String token, String id) {
        try {
            HttpResponse<JsonNode> reponse = deleteRequestJSON(token, "users/" + id);
            return reponse.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get User by Sensor ID
     *
     * @param token - Auth token
     * @param id    - Sensor ID
     * @return String - Response
     */
    public static String getUserBySensorID(String token, String id) {
        try {
            return getRequest(token, "users?sensorId=" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
