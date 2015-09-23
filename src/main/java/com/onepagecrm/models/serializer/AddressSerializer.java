package com.onepagecrm.models.serializer;

import com.onepagecrm.models.Address;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AddressSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(AddressSerializer.class.getName());

    public static Address fromJsonArray(JSONArray addressArray) {
        ArrayList<Address> addresses = new ArrayList<>();
        for (int j = 0; j < addressArray.length(); j++) {
            JSONObject addressObject;
            try {
                addressObject = addressArray.getJSONObject(j);
                addresses.add(fromJsonObject(addressObject));
            } catch (JSONException e) {
                LOG.severe("Error parsing address_list array");
                LOG.severe(e.toString());
            }
        }
        return addresses.get(0);
    }

    public static Address fromJsonObject(JSONObject addressObject) {
        Address address = new Address();
        try {
            String line1 = addressObject.getString(ADDRESS_TAG);
            String city = addressObject.getString(CITY_TAG);
            String state = addressObject.getString(STATE_TAG);
            String zipCode = addressObject.getString(ZIP_CODE_TAG);
            String countryCode = addressObject.getString(COUNTRY_CODE_TAG);
            return address
                    .setAddress(line1)
                    .setCity(city)
                    .setState(state)
                    .setZipCode(zipCode)
                    .setCountryCode(countryCode);
        } catch (JSONException e) {
            LOG.severe("Error parsing address_list object");
            LOG.severe(e.toString());
        }
        return address;
    }

    public static String toJsonObject(Address address) {
        JSONObject addressObject = new JSONObject();
        addJsonStringValue(address.getAddress(), addressObject, ADDRESS_TAG);
        addJsonStringValue(address.getCity(), addressObject, CITY_TAG);
        addJsonStringValue(address.getState(), addressObject, STATE_TAG);
        addJsonStringValue(address.getZipCode(), addressObject, ZIP_CODE_TAG);
        addJsonStringValue(address.getCountryCode(), addressObject, COUNTRY_CODE_TAG);
        return addressObject.toString();
    }

    public static String toJsonArray(Address address) {
        JSONArray addressArray = new JSONArray();
        addressArray.put(toJsonObject(address));
        return addressArray.toString();
    }
}
