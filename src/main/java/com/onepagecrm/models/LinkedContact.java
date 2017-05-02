package com.onepagecrm.models;

import java.io.Serializable;

/**
 * Created by Nichollas on 08/03/17.
 */
public class LinkedContact extends BaseResource implements Serializable {

    private String contactId;
    private String linkedWithId;
    private String companyId;

    public LinkedContact() {

    }

    public String getContactId() {
        return contactId;
    }

    public LinkedContact setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public String getLinkedWithId() {
        return linkedWithId;
    }

    public LinkedContact setLinkedWithId(String linkedWithId) {
        this.linkedWithId = linkedWithId;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public LinkedContact setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public boolean isValid() {
        return contactId != null && !contactId.isEmpty() &&
                linkedWithId != null && !linkedWithId.isEmpty() &&
                companyId != null && !companyId.isEmpty();
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }
}
