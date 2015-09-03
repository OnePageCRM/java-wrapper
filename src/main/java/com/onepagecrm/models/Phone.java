package com.onepagecrm.models;

import com.onepagecrm.models.serializer.PhoneSerializer;

import java.io.Serializable;

public class Phone implements Serializable {

    static final long serialVersionUID = 3927723378236473122L;

    private String type;
    private String number;

    public Phone() {
    }

    public Phone(String type, String number) {
        this.setType(type);
        this.setNumber(number);
    }

    public String getType() {
        return type;
    }

    public Phone setType(String type) {
        this.type = type;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Phone setNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public String toString() {
        return PhoneSerializer.toJsonObject(this);
    }
}
