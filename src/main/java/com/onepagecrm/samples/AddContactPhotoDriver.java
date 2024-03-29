package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.FileUtilities;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class AddContactPhotoDriver {

    private static final Logger LOG = Logger.getLogger(AddContactPhotoDriver.class.getName());

    public static void main(String[] args) throws OnePageException {
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

        final String imagePath = "src/test/res/image_encode/onepage-logo.png";
        final String data = FileUtilities.getResourceContents(imagePath);
        LOG.info("RAW image data: " + data);
        final String b64EncodedData = FileUtilities.encodeImage(imagePath);
        LOG.info("Base64 encoded String: " + b64EncodedData);

        OnePageCRM.init(Request.STAGING_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        final String name = "Bloggs";
        final ContactList stream = Account.getCurrentUser().searchActionStream(name);
        final Contact contact = stream.get(0);
        contact.addPhoto(b64EncodedData);
        LOG.info(contact.toString());
    }
}
