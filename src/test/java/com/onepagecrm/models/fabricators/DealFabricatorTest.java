package com.onepagecrm.models.fabricators;

import com.onepagecrm.BaseTest;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.serializers.DateSerializer;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 16/02/2016.
 */
public class DealFabricatorTest extends BaseTest {

    public void testRegular_correctValues() {
        Deal deal = DealFabricator.regular();
        assertTrue("Deal must be valid.", deal.isValid());
        assertEquals("56fa813000d4afc98c000001", deal.getId());
        assertEquals(200400d, deal.getAmount());
        assertEquals("Cillian M.", deal.getAuthor());
        assertEquals("Deal note for Falcon 9", deal.getText());
        assertEquals("56fa805fefa9dd4d3502ab25", deal.getContactId());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29"), deal.getDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-30"), deal.getExpectedCloseDate());
        assertEquals(DateSerializer.fromFormattedString(null), deal.getCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:20:48Z"), deal.getCreatedAt());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:20:48Z"), deal.getModifiedAt());
        assertTrue(deal.getMonths() != null && deal.getMonths() == 1);
        assertEquals("Falcon 9", deal.getName());
        assertEquals("556cb8b61787fa02e000047e", deal.getOwnerId());
        assertTrue(deal.getStage() != null && deal.getStage() == 75);
        assertEquals("pending", deal.getStatus());
        assertEquals(200400d, deal.getTotalAmount());
        assertTrue(deal.getHasRelatedNotes() != null && !deal.getHasRelatedNotes());
    }

    public void testMultiMonth_correctValues() {
        Deal deal = DealFabricator.multiMonth();
        assertTrue("Deal must be valid.", deal.isValid());
        assertEquals("56fa80f200d4afa0360004b0", deal.getId());
        assertEquals(2000d, deal.getAmount());
        assertEquals("Cillian M.", deal.getAuthor());
        assertEquals("Deal note for Big deal. This is the biggest deal note that you have ever seen in this app. " +
                "I am even going to add some more text to this note right here. Let's see if we can read this or " +
                "if it ellipsizes. Now it must surely ellipsize. Please please please.", deal.getText());
        assertEquals("56fa805fefa9dd4d3502ab2b", deal.getContactId());
        assertEquals(DateSerializer.fromFormattedString("2016-08-02"), deal.getDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29"), deal.getExpectedCloseDate());
        assertEquals(DateSerializer.fromFormattedString(null), deal.getCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:19:46Z"), deal.getCreatedAt());
        assertEquals(DateSerializer.fromFormattedString("2016-08-03T09:34:04Z"), deal.getModifiedAt());
        assertTrue(deal.getMonths() != null && deal.getMonths() == 12);
        assertEquals("Big deal", deal.getName());
        assertEquals("556cb8b61787fa02e000047e", deal.getOwnerId());
        assertTrue(deal.getStage() != null && deal.getStage() == 25);
        assertEquals("pending", deal.getStatus());
        assertEquals(24000d, deal.getTotalAmount());
        assertTrue(deal.getHasRelatedNotes() != null && deal.getHasRelatedNotes());
    }

    public void testWon_correctValues() {
        Deal deal = DealFabricator.won();
        assertTrue("Deal must be valid.", deal.isValid());
        assertEquals("5762c8d500d4af75e1e8fdb8", deal.getId());
        assertEquals(234.5d, deal.getAmount());
        assertEquals("Cillian M.", deal.getAuthor());
        assertEquals("Sample text", deal.getText());
        assertEquals("56fa805fefa9dd4d3502ab2b", deal.getContactId());
        assertEquals(DateSerializer.fromFormattedString("2016-07-20"), deal.getDate());
        assertEquals(DateSerializer.fromFormattedString(null), deal.getExpectedCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-07-20"), deal.getCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-06-16T15:42:13Z"), deal.getCreatedAt());
        assertEquals(DateSerializer.fromFormattedString("2016-07-20T10:55:21Z"), deal.getModifiedAt());
        assertTrue(deal.getMonths() != null && deal.getMonths() == 1);
        assertEquals("Tester deal", deal.getName());
        assertEquals("556cb8b61787fa02e000047e", deal.getOwnerId());
        assertTrue(deal.getStage() == null);
        assertEquals("won", deal.getStatus());
        assertEquals(234.5d, deal.getTotalAmount());
        assertTrue(deal.getHasRelatedNotes() != null && !deal.getHasRelatedNotes());
    }

    public void testLost_correctValues() {
        Deal deal = DealFabricator.lost();
        assertTrue("Deal must be valid.", deal.isValid());
        assertEquals("5762c92b00d4afed1ce8de78", deal.getId());
        assertEquals(13d, deal.getAmount());
        assertEquals("Cillian M.", deal.getAuthor());
        assertEquals("56fa805fefa9dd4d3502ab2b", deal.getContactId());
        assertEquals(DateSerializer.fromFormattedString("2016-06-16"), deal.getDate());
        assertEquals(DateSerializer.fromFormattedString(null), deal.getExpectedCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-06-16"), deal.getCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-06-16T15:43:39Z"), deal.getCreatedAt());
        assertEquals(DateSerializer.fromFormattedString("2016-06-16T15:43:39Z"), deal.getModifiedAt());
        assertTrue(deal.getMonths() != null && deal.getMonths() == 1);
        assertEquals("Tester2", deal.getName());
        assertEquals("556cb8b61787fa02e000047e", deal.getOwnerId());
        assertTrue(deal.getStage() == null);
        assertEquals("lost", deal.getStatus());
        assertEquals(13d, deal.getTotalAmount());
        assertTrue(deal.getHasRelatedNotes() != null && !deal.getHasRelatedNotes());
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void testList_allDealsValid() {
        final int expectedAmount = 15;
        DealList deals = DealFabricator.list();
        assertEquals("Should be " + expectedAmount + " deals", expectedAmount, deals.size());

        for (int i = 0; i < deals.size(); i++) {
            Deal deal = deals.get(i);
            assertTrue("Deal must be valid.", deal.isValid());
        }
    }
}
