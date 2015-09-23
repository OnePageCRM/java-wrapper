package com.onepagecrm.models.serializer;

import com.onepagecrm.models.Phone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PhoneSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(PhoneSerializer.class.getName());

    public static ArrayList<Phone> fromJsonArray(JSONArray phonesArray) {
        ArrayList<Phone> phones = new ArrayList<>();
        for (int j = 0; j < phonesArray.length(); j++) {
            JSONObject phoneObject;
            try {
                phoneObject = phonesArray.getJSONObject(j);
                phones.add(fromJsonObject(phoneObject));
            } catch (JSONException e) {
                LOG.severe("Error parsing phone array");
                LOG.severe(e.toString());
            }
        }
        return phones;
    }

    public static Phone fromJsonObject(JSONObject phoneObject) {
        Phone phone = new Phone();
        try {
            String type = phoneObject.getString(TYPE_TAG);
            String value = phoneObject.getString(VALUE_TAG);
            return phone
                    .setType(type)
                    .setValue(value);
        } catch (JSONException e) {
            LOG.severe("Error parsing phone object");
            LOG.severe(e.toString());
        }
        return phone;
    }

    public static String toJsonObject(Phone phone) {
        JSONObject callObject = new JSONObject();
        addJsonStringValue(phone.getType(), callObject, TYPE_TAG);
        addJsonStringValue(phone.getValue(), callObject, VALUE_TAG);
        return callObject.toString();
    }

    public static String toJsonArray(List<Phone> phones) {
        JSONArray phonesArray = new JSONArray();
        if (phones != null && !phones.isEmpty()) {
            for (int i = 0; i < phones.size(); i++) {
                phonesArray.put(toJsonObject(phones.get(i)));
            }
        }
        return phonesArray.toString();
    }
}