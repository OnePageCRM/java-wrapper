package com.onepagecrm.models.fabricators;

import com.onepagecrm.BaseTest;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.serializers.DateSerializer;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 16/02/2016.
 */
public class DealFabricatorTest extends BaseTest {

    public void testSingle_correctValues() {
        Deal deal = DealFabricator.single();
        assertTrue("Deal must be valid.", deal.isValid());
        assertEquals("56fa82159007ba1965000000", deal.getId());
        assertEquals(20400d, deal.getAmount());
        assertEquals("Cillian M.", deal.getAuthor());
        assertEquals("Deal note for Big deal", deal.getText());
        assertEquals("56fa81eb9007ba07fc000080", deal.getContactId());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29"), deal.getDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29"), deal.getExpectedCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:24:37.801Z"), deal.getCreatedAt());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:24:37.801Z"), deal.getModifiedAt());
        assertTrue(deal.getMonths() != null && deal.getMonths() == 1);
        assertEquals("Big deal", deal.getName());
        assertEquals("559cd1866f6e656707000001", deal.getOwnerId());
        assertTrue(deal.getStage() != null && deal.getStage() == 50);
        assertEquals("pending", deal.getStatus());
        assertEquals(20400d, deal.getTotalAmount());
        assertTrue(deal.getHasRelatedNotes() != null && !deal.getHasRelatedNotes());
        assertEquals(null, deal.getCloseDate());
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void testList_allCountriesValid() {
        DealList deals = DealFabricator.list();
        assertEquals("Should be 2 deals", deals.size(), 2);

        for (int i = 0; i < deals.size(); i++) {
            Deal deal = deals.get(i);
            assertTrue("Deal must be valid.", deal.isValid());
        }
    }
}