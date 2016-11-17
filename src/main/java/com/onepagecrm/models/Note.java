package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.serializers.NoteListSerializer;
import com.onepagecrm.models.serializers.NoteSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Note extends ApiResource implements Serializable {

    private String id;
    private String author;
    private String text;
    private String contactId;
    private Date createdAt;
    private Date date;
    private String linkedDealId;

    public Note save() throws OnePageException {
        return this.isValid() ? update() : create();
    }

    private Note create() throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact_id", contactId);
        Request request = new PostRequest(
                NOTES_ENDPOINT,
                Query.fromParams(params),
                NoteSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return NoteSerializer.fromString(response.getResponseBody());
    }

    private Note update() throws OnePageException {
        Request request = new PutRequest(
                addNoteIdToEndpoint(NOTES_ENDPOINT),
                null,
                NoteSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return NoteSerializer.fromString(response.getResponseBody());
    }

    public void delete() throws OnePageException {
        Request request = new DeleteRequest(addNoteIdToEndpoint(NOTES_ENDPOINT));
        request.send();
    }

    public static NoteList list(String contactId) throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact_id", contactId);
        Request request = new GetRequest(NOTES_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        NoteList notes = NoteListSerializer.fromString(response.getResponseBody());
        notes.setContactId(contactId);
        return notes;
    }

    public static NoteList list(String contactId, Paginator paginator) throws OnePageException {
        Map<String, Object> params = Query.params(paginator);
        params.put("contact_id", contactId);
        Request request = new GetRequest(NOTES_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        NoteList notes = NoteListSerializer.fromString(response.getResponseBody());
        notes.setContactId(contactId);
        return notes;
    }

    public static NoteList list() throws OnePageException {
        Request request = new GetRequest(NOTES_ENDPOINT);
        Response response = request.send();
        return NoteListSerializer.fromString(response.getResponseBody());
    }

    private String addNoteIdToEndpoint(String endpoint) {
        return endpoint + "/" + this.id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Note setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return NoteSerializer.toJsonObject(this);
    }

    public Note setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Note setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    public Note setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public String getContactId() {
        return contactId;
    }

    public Note setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Note setDate(Date date) {
        this.date = date;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Note setLinkedDealId(String linkedDealId) {
        this.linkedDealId = linkedDealId;
        return this;
    }

    public String getLinkedDealId() {
        return linkedDealId;
    }
}
