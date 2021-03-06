package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.internal.Commission;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * @author Cillian Myles (cillian@onepagecrm.com) on 24/11/2015.
 */
public class DealSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(DealSerializer.class.getName());

    public static Deal fromString(String responseBody) throws OnePageException {
        Deal deal = new Deal();
        try {
            String parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonObject(responseObject);

        } catch (ClassCastException e) {
            throw (OnePageException) BaseSerializer.fromString(responseBody);

        } catch (JSONException e) {
            LOG.severe("Error parsing Deal object from response body");
            LOG.severe(e.toString());
        }
        return deal;
    }

    public static Deal fromJsonObject(JSONObject dataObject) {
        Deal deal = new Deal();
        JSONObject dealObject;
        try {
            // Fix for some objects not having name.
            if (dataObject.has(DEAL_TAG)) {
                dealObject = dataObject.getJSONObject(DEAL_TAG);
            } else {
                dealObject = dataObject;
            }
            // Now parse info.
            if (dealObject.has(ID_TAG)) {
                deal.setId(dealObject.getString(ID_TAG));
            }
            if (dealObject.has(AMOUNT_TAG)) {
                deal.setAmount(dealObject.getDouble(AMOUNT_TAG));
            }
            if (dealObject.has(AUTHOR_TAG)) {
                deal.setAuthor(dealObject.getString(AUTHOR_TAG));
            }
            if (dealObject.has(TEXT_TAG)) {
                deal.setText(dealObject.getString(TEXT_TAG));
            }
            if (dealObject.has(CONTACT_ID_TAG)) {
                deal.setContactId(dealObject.getString(CONTACT_ID_TAG));
            }
            if (dealObject.has(CREATED_AT_TAG)) {
                deal.setCreatedAt(InstantSerializer.getInstance().parse(dealObject.optString(CREATED_AT_TAG)));
            }
            if (dealObject.has(DATE_TAG)) {
                deal.setDate(LocalDateSerializer.getInstance().parse(dealObject.optString(DATE_TAG)));
            }
            if (dealObject.has(EXPECTED_CLOSE_DATE_TAG)) {
                deal.setExpectedCloseDate(LocalDateSerializer.getInstance().parse(dealObject.optString(EXPECTED_CLOSE_DATE_TAG)));
            }
            if (dealObject.has(MONTHS_TAG)) {
                deal.setMonths(dealObject.getInt(MONTHS_TAG));
            }
            if (dealObject.has(NAME_TAG)) {
                deal.setName(dealObject.getString(NAME_TAG));
            }
            if (dealObject.has(OWNER_ID_TAG)) {
                deal.setOwnerId(dealObject.getString(OWNER_ID_TAG));
            }
            if (dealObject.has(STAGE_TAG)) {
                deal.setStage(dealObject.getInt(STAGE_TAG));
            }
            if (dealObject.has(STATUS_TAG)) {
                deal.setStatus(dealObject.getString(STATUS_TAG));
            }
            if (dealObject.has(TOTAL_AMOUNT_TAG)) {
                deal.setTotalAmount(dealObject.getDouble(TOTAL_AMOUNT_TAG));
            }
            if (dealObject.has(MODIFIED_AT_TAG)) {
                deal.setModifiedAt(InstantSerializer.getInstance().parse(dealObject.optString(MODIFIED_AT_TAG)));
            }
            if (dealObject.has(HAS_RELATED_NOTES_TAG)) {
                deal.setHasRelatedNotes(dealObject.getBoolean(HAS_RELATED_NOTES_TAG));
            }
            if (dealObject.has(CLOSE_DATE_TAG) && !dealObject.isNull(CLOSE_DATE_TAG)) {
                deal.setCloseDate(LocalDateSerializer.getInstance().parse(dealObject.optString(CLOSE_DATE_TAG)));
            }
            if (dealObject.has(CONTACT_INFO_TAG)) {
                JSONObject contactInfoObj = dealObject.getJSONObject(CONTACT_INFO_TAG);
                Deal.ContactInfo contactInfo = new Deal.ContactInfo();
                if (contactInfoObj.has(CONTACT_NAME_TAG)) {
                    contactInfo.setContactName(contactInfoObj.getString(CONTACT_NAME_TAG));
                }
                if (contactInfoObj.has(COMPANY_TAG)) {
                    contactInfo.setCompany(contactInfoObj.getString(COMPANY_TAG));
                }
                deal.setContactInfo(contactInfo);
            }
            // CFD fields.
            if (dealObject.has(DEAL_FIELDS_TAG)) {
                deal.setDealFields(CustomFieldSerializer.fromJsonArray(
                        dealObject.optJSONArray(DEAL_FIELDS_TAG), CustomField.CF_TYPE_DEAL));
            }
            if (dealObject.has(COST_TAG) && !dealObject.isNull(COST_TAG)) {
                deal.setCost(dealObject.getDouble(COST_TAG));
            }
            if (dealObject.has(MARGIN_TAG) && !dealObject.isNull(MARGIN_TAG)) {
                deal.setMargin(dealObject.getDouble(MARGIN_TAG));
            }
            if (dealObject.has(TOTAL_COST_TAG) && !dealObject.isNull(TOTAL_COST_TAG)) {
                deal.setTotalCost(dealObject.getDouble(TOTAL_COST_TAG));
            }
            if (dealObject.has(COMMISSION_TAG) && !dealObject.isNull(COMMISSION_TAG)) {
                deal.setCommission(dealObject.getDouble(COMMISSION_TAG));
            }
            if (dealObject.has(COMMISSION_BASE_TAG)) {
                deal.setCommissionBase(Commission.Base.fromString(dealObject.getString(COMMISSION_BASE_TAG)));
            }
            if (dealObject.has(COMMISSION_TYPE_TAG)) {
                deal.setCommissionType(Commission.Type.fromString(dealObject.getString(COMMISSION_TYPE_TAG)));
            }
            if (dealObject.has(COMMISSION_PERCENTAGE_TAG) && !dealObject.isNull(COMMISSION_PERCENTAGE_TAG)) {
                deal.setCommissionPercentage(dealObject.getDouble(COMMISSION_PERCENTAGE_TAG));
            }
            if (dealObject.has(ATTACHMENTS_TAG)) {
                deal.setAttachments(AttachmentSerializer.fromJsonArray(dealObject.optJSONArray(ATTACHMENTS_TAG)));
            }
            // Related notes (outer object).
            if (dataObject.has(RELATED_NOTES_TAG)) {
                deal.setRelatedNotes(NoteSerializer.fromJsonArray(dataObject.optJSONArray(RELATED_NOTES_TAG)));
            }
            if (dealObject.has(HAS_DEAL_ITEMS_TAG)) {
                deal.setHasDealItems(dealObject.optBoolean(HAS_DEAL_ITEMS_TAG));
            }
            if (dealObject.has(DEAL_ITEMS_TAG)) {
                deal.setDealItems(DealItemSerializer.fromJsonArray(dealObject.optJSONArray(DEAL_ITEMS_TAG)));
            }
        } catch (JSONException e) {
            LOG.severe("Error parsing Deal object");
            LOG.severe(e.toString());
        }
        return deal;
    }

    public static String toJsonObject(Deal deal) {
        return toJsonObject(deal, false);
    }

    public static String toJsonObject(Deal deal, boolean partial) {
        JSONObject dealObject = new JSONObject();
        addJsonStringValue(deal.getId(), dealObject, ID_TAG);
        addJsonDoubleValue(deal.getAmount(), dealObject, AMOUNT_TAG);
        addJsonStringValue(deal.getAuthor(), dealObject, AUTHOR_TAG);
        addJsonStringValue(deal.getText(), dealObject, TEXT_TAG);
        addJsonStringValue(deal.getContactId(), dealObject, CONTACT_ID_TAG);
        addJsonStringValue(
                InstantSerializer.getInstance().format(deal.getCreatedAt()),
                dealObject,
                CREATED_AT_TAG
        );
        addJsonStringValue(
                LocalDateSerializer.getInstance().format(deal.getDate()),
                dealObject,
                DATE_TAG
        );
        addJsonStringValue(
                LocalDateSerializer.getInstance().format(deal.getExpectedCloseDate()),
                dealObject,
                EXPECTED_CLOSE_DATE_TAG
        );
        addJsonIntegerValue(deal.getMonths(), dealObject, MONTHS_TAG);
        addJsonStringValue(deal.getName(), dealObject, NAME_TAG);
        addJsonStringValue(deal.getOwnerId(), dealObject, OWNER_ID_TAG);
        addJsonIntegerValue(deal.getStage(), dealObject, STAGE_TAG);
        addJsonStringValue(deal.getStatus(), dealObject, STATUS_TAG);
        addJsonDoubleValue(deal.getTotalAmount(), dealObject, TOTAL_AMOUNT_TAG);
        addJsonStringValue(
                InstantSerializer.getInstance().format(deal.getModifiedAt()),
                dealObject,
                MODIFIED_AT_TAG
        );
        addJsonBooleanValue(deal.getHasRelatedNotes(), dealObject, HAS_RELATED_NOTES_TAG);
        addJsonStringValue(
                LocalDateSerializer.getInstance().format(deal.getCloseDate()),
                dealObject,
                CLOSE_DATE_TAG
        );
        addJsonDoubleValue(deal.getCost(), dealObject, COST_TAG);
        addJsonDoubleValue(deal.getMargin(), dealObject, MARGIN_TAG);
        addJsonDoubleValue(deal.getTotalCost(), dealObject, TOTAL_COST_TAG);
        addJsonDoubleValue(deal.getCommission(), dealObject, COMMISSION_TAG);
        addJsonObjectValue(deal.getCommissionBase(), dealObject, COMMISSION_BASE_TAG);
        addJsonObjectValue(deal.getCommissionType(), dealObject, COMMISSION_TYPE_TAG);
        addJsonDoubleValue(deal.getCommissionPercentage(), dealObject, COMMISSION_PERCENTAGE_TAG);
        try {
            JSONArray dealFieldsArray = new JSONArray(CustomFieldSerializer.toJsonArray(deal.getDealFields()));
            addJsonArray(dealFieldsArray, dealObject, DEAL_FIELDS_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Deal Fields array while constructing Deal object");
            LOG.severe(e.toString());
        }
        // AND-926 always send "has_deal_items" flag.
        if (partial && !deal.hasDealItems()) {
            addJsonBooleanValue(deal.hasDealItemsReadOnly(), dealObject, HAS_DEAL_ITEMS_TAG);
        } else {
            addJsonBooleanValue(deal.hasDealItems(), dealObject, HAS_DEAL_ITEMS_TAG);
        }
        // AND-926 always send deal items if present.
        addJsonArray(DealItemSerializer.toJsonArray(deal.getDealItems()), dealObject, DEAL_ITEMS_TAG);
        return dealObject.toString();
    }

    public static String toJsonArray(DealList deals) {
        JSONArray dealsArray = new JSONArray();
        if (deals != null && !deals.isEmpty()) {
            for (Deal deal : deals) {
                try {
                    dealsArray.put(new JSONObject(toJsonObject(deal)));
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of Deals");
                    LOG.severe(e.toString());
                }
            }
        }
        return dealsArray.toString();
    }
}
