package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 03/12/2015.
 */
public class AddContactAltCharsDriver {

    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

    public static void main(String[] args) throws OnePageException, JSONException {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");

            // Load the properties file
            prop.load(input);

        } catch (IOException e) {
            LOG.severe("Error loading the config.properties file");
            LOG.severe(e.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.severe("Error closing the config.properties file");
                    LOG.severe(e.toString());
                }
            }
        }

        OnePageCRM.init(Request.APP_US_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        new Contact()
                .setFirstName("ľščťžýáíé")
                .setLastName("ľščťžýáíé")
                .save();
    }
}
