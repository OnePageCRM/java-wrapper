package com.onepagecrm.net;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.StartupData;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.LoginData;
import com.onepagecrm.models.serializers.LoginDataSerializer;
import com.onepagecrm.models.serializers.LoginSerializer;
import com.onepagecrm.models.serializers.StartupDataSerializer;
import com.onepagecrm.net.request.GoogleLoginRequest;
import com.onepagecrm.net.request.LoginRequest;
import com.onepagecrm.net.request.Request;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public interface API {

    abstract class Auth {

        public static LoginData authenticate(String username, String password, boolean fullResponse) throws OnePageException {
            Request request = new LoginRequest(username, password, fullResponse);
            Response response = request.send();
            LoginData loginData = LoginDataSerializer.fromString(response.getResponseBody());
            return loginData
                    .setUsername(username)
                    .setPassword(password)
                    .setFullResponse(fullResponse);
        }

        public static StartupData startup(LoginData loginData) throws OnePageException {
            Request request = new LoginRequest(loginData);
            Response response = request.send();
            return StartupDataSerializer.fromString(response.getResponseBody());
        }

        public static User simpleLogin(LoginData loginData) throws OnePageException {
            return startup(loginData.setFullResponse(false)).getUser();
        }

        public static StartupData loginOld(LoginData loginData, boolean fullResponse) throws OnePageException {
            Request request = new LoginRequest(loginData.setFullResponse(fullResponse));
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody(), fullResponse);
        }

        /* ... */

        public static User googleLogin(String authCode) throws OnePageException {
            Request request = new GoogleLoginRequest(authCode, true);
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody());
        }

        public static User googleSignup(String authCode) throws OnePageException {
            Request request = new GoogleLoginRequest(authCode, false);
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody());
        }
    }
}