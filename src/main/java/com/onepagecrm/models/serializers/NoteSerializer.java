package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Note;
import com.onepagecrm.models.NoteList;
import com.onepagecrm.models.internal.Paginator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 4/25/16.
 */
public class NoteSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(NoteSerializer.class.getName());

    private static final String NOTES_TAG = "notes";
    private static final String NOTE_TAG = "note";
    public static final String LINKED_DEAL_ID_TAG = "linked_deal_id";

    public static NoteList fromJsonString(String pResponseBody) {
        NoteList notes = new NoteList();
        try {
            JSONObject root = new JSONObject(pResponseBody);
            JSONObject data = root.optJSONObject(DATA_TAG);
            JSONArray notesArray = data.optJSONArray(NOTES_TAG);
            Paginator paginator = RequestMetadataSerializer.fromJsonObject(data);
            notes.setPaginator(paginator);
            for (int i = 0; i < notesArray.length(); ++i) {
                JSONObject obj = notesArray.optJSONObject(i);
                Note note = objFromJson(obj);
                if (note != null) {
                    notes.add(note);
                }
            }
        } catch (Exception e) {
            LOG.severe("Could not find Note object tags");
            LOG.severe(e.toString());
        }
        return notes;
    }

    public static Note fromString(String pResponseBody) {
        Note note = new Note();
        try {
            JSONObject responseObject = new JSONObject(pResponseBody);
            JSONObject data = responseObject.optJSONObject(DATA_TAG);
            note = objFromJson(data);
        } catch (JSONException e) {
            LOG.severe("Could not find note object tags");
            LOG.severe(e.toString());
        }

        return note;
    }

    private static Note objFromJson(JSONObject pObj) {
        JSONObject lJSONObject = pObj.optJSONObject(NOTE_TAG);
        if (lJSONObject == null)
            return null;
        return fromJsonObject(lJSONObject);
    }

    public static List<Note> fromJsonArray(JSONArray notesArray) {
        List<Note> notes = new LinkedList<>();
        for (int i = 0; i < notesArray.length(); ++i) {
            JSONObject obj = notesArray.optJSONObject(i);
            Note note = fromJsonObject(obj);
            if (note != null) {
                notes.add(note);
            }
        }
        return notes;
    }

    public static Note fromJsonObject(JSONObject noteObject) {
        // Fix for some objects not having name.
        if (noteObject.has(NOTE_TAG)) {
            noteObject = noteObject.optJSONObject(NOTE_TAG);
        }
        Note note = new Note();
        note.setId(noteObject.optString(ID_TAG));
        note.setAuthor(noteObject.optString(AUTHOR_TAG));
        note.setText(noteObject.optString(TEXT_TAG));
        note.setContactId(noteObject.optString(CONTACT_ID_TAG));
        note.setCreatedAt(DateSerializer.fromFormattedString(noteObject.optString(CREATED_AT_TAG)));
        note.setDate(DateSerializer.fromFormattedString(noteObject.optString(DATE_TAG)));
        if (!noteObject.isNull(LINKED_DEAL_ID_TAG)) {
            note.setLinkedDealId(noteObject.optString(LINKED_DEAL_ID_TAG));
        }
        return note;
    }

    public static String toJsonObject(Note pNote) {
        JSONObject noteObject = new JSONObject();
        addJsonStringValue(pNote.getId(), noteObject, ID_TAG);
        addJsonStringValue(pNote.getAuthor(), noteObject, AUTH_KEY_TAG);
        addJsonStringValue(pNote.getText(), noteObject, TEXT_TAG);
        addJsonStringValue(pNote.getContactId(), noteObject, CONTACT_ID_TAG);
        addJsonStringValue(DateSerializer.toFormattedDateTimeString(pNote.getCreatedAt()), noteObject, CREATED_AT_TAG);
        addJsonStringValue(DateSerializer.toFormattedDateString(pNote.getDate()), noteObject, DATE_TAG);
        addJsonStringValue(pNote.getLinkedDealId(), noteObject, LINKED_DEAL_ID_TAG);
        return noteObject.toString();
    }
}
