package com.onepagecrm.models.serializers;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class ZonedDateTimeSerializerTest extends DateTimeTestHelper {

    // Fri, 01 Jul 2016 08:00:00 UTC = 1467360000
    private ZonedDateTime timeNo1DateTimeUTC;
    private ZonedDateTime timeNo1DateTimeUTCMinus4;
    private String timeNo1FormattedUTC;
    private String timeNo1FormattedUTCMinus4;

    // Wed, 11 Apr 2018 16:00:00 UTC = 1523462400
    private ZonedDateTime timeNo2DateTimeUTC;
    private ZonedDateTime timeNo2DateTimeUTCMinus4;
    private String timeNo2FormattedUTC;
    private String timeNo2FormattedUTCMinus4;

    @Before
    public void setUp() throws Exception {
        timeNo1DateTimeUTC = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1467360000L), ZONE_ID_UTC);
        timeNo1DateTimeUTCMinus4 = timeNo1DateTimeUTC.withZoneSameInstant(ZONE_ID_ET);
        timeNo1FormattedUTC = "08:00 Jul 01, 2016";
        timeNo1FormattedUTCMinus4 = "04:00 Jul 01, 2016";

        timeNo2DateTimeUTC = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1523462400L), ZONE_ID_UTC);
        timeNo2DateTimeUTCMinus4 = timeNo2DateTimeUTC.withZoneSameInstant(ZONE_ID_ET);
        timeNo2FormattedUTC = "16:00 Apr 11, 2018";
        timeNo2FormattedUTCMinus4 = "12:00 Apr 11, 2018";
    }

    @Test
    public void testZoneId_DefaultsMatch() throws Exception {
        ZoneId zoneId = null;
        Exception exception = null;
        try {
            zoneId = ZonedDateTimeSerializer.getInstance().defaultZoneId();
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Default ZoneId does not match", zoneId);
        assertNotNull("Exception should be thrown", exception);
    }

    @Test
    public void testFormatting_ZonedDateTimeToString() throws Exception {
        assertEquals(timeNo1FormattedUTC, ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTC, DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR));
        assertEquals(timeNo1FormattedUTCMinus4, ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTCMinus4, DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR));

        assertEquals(timeNo2FormattedUTC, ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTC, DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR));
        assertEquals(timeNo2FormattedUTCMinus4, ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTCMinus4, DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR));

        assertNull(ZonedDateTimeSerializer.getInstance().format(null));
    }
}