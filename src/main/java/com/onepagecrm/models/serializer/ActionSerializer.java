package com.onepagecrm.models.serializer;

import com.onepagecrm.models.Action;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ActionSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(ActionSerializer.class.getName());

    public static Action fromJsonObject(JSONObject actionObject) {
        Action action = new Action();
        String status = null;
        Date date = null;
        try {
            if (actionObject.has(ID_TAG)) {
                action.setId(actionObject.getString(ID_TAG));
            }
            if (actionObject.has(CONTACT_ID_TAG)) {
                action.setContactId(actionObject.getString(CONTACT_ID_TAG));
            }
            if (actionObject.has(TEXT_TAG)) {
                action.setText(actionObject.getString(TEXT_TAG));
            }
            if (actionObject.has(ASSIGNEE_ID_TAG)) {
                action.setAssigneeId(actionObject.getString(ASSIGNEE_ID_TAG));
            }
            if (actionObject.has(MODIFIED_AT_TAG)) {
                String modifiedAtStr = actionObject.getString(MODIFIED_AT_TAG);
                Date modifiedAt = DateSerializer.fromFormattedString(modifiedAtStr);
                action.setModifiedAt(modifiedAt);
            }
            if (actionObject.has(STATUS_TAG)) {
                status = actionObject.getString(STATUS_TAG);
                action.setStatus(status);
            }
            if (actionObject.has(DATE_TAG)) {
                if (!actionObject.isNull(DATE_TAG)) {
                    String dateStr = actionObject.getString(DATE_TAG);
                    date = DateSerializer.fromFormattedString(dateStr);
                    action.setDate(date);
                }
            }
            int dateColor = DateSerializer.getDateColour(date, status);
            action.setDateColor(dateColor);

            return action;

        } catch (JSONException e) {
            LOG.severe("Error parsing contact object");
            LOG.severe(e.toString());
        }
        return new Action();
    }

    public static List<Action> fromJsonArray(JSONArray actionsArray) {
        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < actionsArray.length(); i++) {
            try {
                actions.add(fromJsonObject(actionsArray.getJSONObject(i)));
            } catch (JSONException e) {
                LOG.severe("Error parsing contact object");
                LOG.severe(e.toString());
            }
        }
        return actions;
    }
}
