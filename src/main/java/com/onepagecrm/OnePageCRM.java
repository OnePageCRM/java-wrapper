package com.onepagecrm;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.AppServer;
import com.onepagecrm.models.serializers.AppServerSerializer;
import com.onepagecrm.net.API;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.AppServersRequest;
import com.onepagecrm.net.request.Request;

import java.util.List;

/**
 * Created by Cillian Myles on 02/02/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */

@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public final class OnePageCRM {

    public static Account account;
    private static OnePageCRM instance;

    public static final String CHARSET_UTF_8 = "UTF-8";

    public static final String WRAPPER_JSON_PATH = "./src/test/res/responses/perfect/";
    public static String ASSET_PATH = WRAPPER_JSON_PATH;

    public static boolean DEBUG = false;
    public static int SERVER = Request.APP_US_SERVER;
    public static String SOURCE = "java-wrapper";
    public static boolean COMPLEX_AUTH = false;
    public static boolean MOBILE = false;
    public static String APP_VERSION = null;
    public static String USER_ID = null;
    public static String API_KEY = null;


    public static OnePageCRM getInstance() {
        if (instance == null) {
            instance = new OnePageCRM();
        }
        return instance;
    }

    public static OnePageCRM init(int server, String user_id, String api_key)  throws OnePageException {
        SERVER = server;
        USER_ID = user_id;
        API_KEY = api_key;
        API.Auth.bootstrap();
        return getInstance();
    }

    public static String getEndpointUrl() {
        return Request.getServerUrl(SERVER);
    }

    public static OnePageCRM setDebug(boolean debug) {
        DEBUG = debug;
        return getInstance();
    }

    public static OnePageCRM setServer(int server) {
        SERVER = server;
        return getInstance();
    }

    public static OnePageCRM setSource(String source) {
        SOURCE = source;
        return getInstance();
    }

    public static OnePageCRM setLocalDevUrl(String customUrl) {
        Request.setLocalDevUrl(customUrl);
        return getInstance();
    }

    public static OnePageCRM setNetworkDevUrl(String customUrl) {
        Request.setNetworkDevUrl(customUrl);
        return getInstance();
    }

    public static OnePageCRM setCustomUrl(String customUrl) {
        Request.setCustomUrl(customUrl);
        return getInstance();
    }

    public static OnePageCRM setComplexAuth(boolean complexAuth) {
        COMPLEX_AUTH = complexAuth;
        return getInstance();
    }

    public static OnePageCRM setMobile(boolean mobile) {
        MOBILE = mobile;
        return getInstance();
    }

    public static OnePageCRM setAppVersion(String appVersion) {
        APP_VERSION = appVersion;
        return getInstance();
    }

    public static OnePageCRM setUserId(String user_id) {
        USER_ID = user_id;
        return getInstance();
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static OnePageCRM setApiKey(String api_key) {
        API_KEY = api_key;
        return getInstance();
    }

    public static String getApiKey() {
        return API_KEY;
    }

    /**
     * List available servers for authentication - in the multi-server environment.
     *
     * @return list of available {@link AppServer servers}.
     * @throws OnePageException if an error occurs.
     */
    public static List<AppServer> availableServers() throws OnePageException {
        return availableServers(Request.DEFAULT_AUTH_SERVER);
    }

    /**
     * List available servers for authentication - in the multi-server environment.
     *
     * @return list of available {@link AppServer servers}.
     * @throws OnePageException if an error occurs.
     */
    public static List<AppServer> availableServers(int authServerId) throws OnePageException {
        // Take note of the current server.
        final int oldServerId = OnePageCRM.SERVER;
        OnePageCRM.setServer(authServerId);

        // Request the server details.
        Request request = new AppServersRequest();
        Response response = request.send();
        String body = response.getResponseBody();
        List<AppServer> servers = AppServerSerializer.fromString(body);

        // Go back to "current" server.
        OnePageCRM.setServer(oldServerId);

        return servers;
    }
}
