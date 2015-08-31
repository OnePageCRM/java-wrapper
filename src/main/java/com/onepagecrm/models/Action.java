package com.onepagecrm.models;

import java.io.Serializable;
import java.util.Date;

import com.onepagecrm.models.serializer.DateSerializer;
import com.onepagecrm.net.ApiResource;

public class Action extends ApiResource implements Serializable {

    private static final long serialVersionUID = -7486991046434989805L;

    private String id;
    private String assigneeId;
    private String contactId;
    private String text;
    private Date modifiedAt;
    private String status;
    private Date date;
    private String friendlyDateString;
    private int dateColor;

    public Action() {

    }

    public String getAssigneeId() {
	return assigneeId;
    }

    public Action setAssigneeId(String assigneeId) {
	this.assigneeId = assigneeId;
	return this;
    }

    public String getContactId() {
	return contactId;
    }

    public Action setContactId(String contactId) {
	this.contactId = contactId;
	return this;
    }

    public String getText() {
	return text;
    }

    public Action setText(String text) {
	this.text = text;
	return this;
    }

    public Date getModifiedAt() {
	return modifiedAt;
    }

    public Action setModifiedAt(Date modifiedAt) {
	this.modifiedAt = modifiedAt;
	return this;
    }

    public String getStatus() {
	return status;
    }

    public Action setStatus(String status) {
	this.status = status;
	return this;
    }

    public Date getDate() {
	return date;
    }

    public Action setDate(Date date) {
	this.date = date;
	return this;
    }

    public String getFriendlyDateString() {
	if (this.date == null) {
	    return this.status.toUpperCase();
	} else {
	    return DateSerializer.toFriendlyDateString(this.date).toUpperCase();
	}
    }

    public Action setFriendlyDateString(String friendlyDateString) {
        this.friendlyDateString = friendlyDateString;
        return this;
    }

    public String getId() {
	return id;
    }

    public Action setId(String id) {
	this.id = id;
	return this;
    }

    public int getDateColor() {
	return dateColor;
    }

    public Action setDateColor(int dateColor) {
	this.dateColor = dateColor;
	return this;
    }

    @Override
    public String toString() {
	return "Action{id=\'" + id + "\', assigneeId=\'" + assigneeId + "\', contactId=\'"
		+ contactId + "\', text=\'" + text + "\', modifiedAt=\'" + modifiedAt
		+ "\', status=\'" + status + "\', date=\'" + date + "\'}";
    }
}