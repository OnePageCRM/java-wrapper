package com.onepagecrm.models.helpers;

import com.onepagecrm.models.internal.SystemClock;
import com.onepagecrm.models.serializers.DateTimeSerializer;
import com.onepagecrm.models.serializers.InstantSerializer;
import com.onepagecrm.models.serializers.LocalDateSerializer;
import com.onepagecrm.models.serializers.ZonedDateTimeSerializer;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import static com.onepagecrm.models.helpers.ActionHelper.STATUS_TODAY;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class DateTimeHelper {

    public static LocalDate today() {
        return LocalDate.now(SystemClock.getInstance());
    }

    public static boolean isToday(LocalDate date) {
        return date != null && date.isEqual(today());
    }

    public static LocalDateTime nowLocal() {
        return LocalDateTime.now(SystemClock.getInstance());
    }

    public static ZonedDateTime nowZoned(ZoneId zoneId) {
        return ZonedDateTime.of(nowLocal(), zoneId);
    }

    public static Instant nowUTC() {
        return Instant.now(SystemClock.getInstance());
    }

    public static DateTimeFormatter timeFormat(boolean is24hr) {
        return is24hr
                ? DateTimeSerializer.FORMATTER_FRIENDLY_TIME
                : DateTimeSerializer.FORMATTER_FRIENDLY_TIME_AM_PM;
    }

    public static DateTimeFormatter timeDateYearFormat(boolean is24hr) {
        return is24hr
                ? DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR
                : DateTimeSerializer.FORMATTER_FRIENDLY_TIME_AM_PM_DATE_YEAR;
    }

    public static String formatDate(LocalDate date) {
        return formatDateImpl(date, false, false);
    }

    public static String formatDate(ZonedDateTime date) {
        return formatDateImpl(date, false, false);
    }

    public static String formatDate(Instant date) {
        return formatDateImpl(date, false, false);
    }

    public static String formatDateYear(LocalDate date) {
        return formatDateImpl(date, false, true);
    }

    public static String formatDateYear(ZonedDateTime date) {
        return formatDateImpl(date, false, true);
    }

    public static String formatDateYear(Instant date) {
        return formatDateImpl(date, false, true);
    }

    public static String formatDateFriendly(LocalDate date) {
        return formatDateImpl(date, true, false);
    }

    public static String formatDateFriendly(ZonedDateTime date) {
        return formatDateImpl(date, true, false);
    }

    public static String formatDateFriendly(Instant date) {
        return formatDateImpl(date, true, false);
    }

    public static String formatDateYearFriendly(LocalDate date) {
        return formatDateImpl(date, true, true);
    }

    public static String formatDateYearFriendly(ZonedDateTime date) {
        return formatDateImpl(date, true, true);
    }

    public static String formatDateYearFriendly(Instant date) {
        return formatDateImpl(date, true, true);
    }

    private static String formatDateImpl(LocalDate date, boolean friendly, boolean year) {
        if (date == null) return null;
        return DateTimeHelper.isToday(date) && friendly
                ? STATUS_TODAY
                : LocalDateSerializer.getInstance().format(date,
                year ? DateTimeSerializer.FORMATTER_FRIENDLY_DATE_YEAR : DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
    }

    private static String formatDateImpl(ZonedDateTime date, boolean friendly, boolean year) {
        if (date == null) return null;
        return DateTimeHelper.isToday(date.toLocalDate()) && friendly
                ? STATUS_TODAY
                : ZonedDateTimeSerializer.getInstance().format(date,
                year ? DateTimeSerializer.FORMATTER_FRIENDLY_DATE_YEAR : DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
    }

    private static String formatDateImpl(Instant date, boolean friendly, boolean year) {
        if (date == null) return null;
        return DateTimeHelper.isToday(date.atZone(InstantSerializer.getInstance().defaultZoneId()).toLocalDate())
                && friendly
                ? STATUS_TODAY
                : InstantSerializer.getInstance().format(date,
                year ? DateTimeSerializer.FORMATTER_FRIENDLY_DATE_YEAR : DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
    }

    public static LocalDate parseDateFriendly(String date) {
        if (TextHelper.isEmpty(date)) return null;
        return LocalDateSerializer.getInstance()
                .parse(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
    }
}
