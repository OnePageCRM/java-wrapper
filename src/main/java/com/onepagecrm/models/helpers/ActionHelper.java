package com.onepagecrm.models.helpers;

import com.onepagecrm.models.Action;
import com.onepagecrm.models.internal.OPCRMColors;
import com.onepagecrm.models.internal.PredefinedAction;
import com.onepagecrm.models.serializers.DateTimeSerializer;
import com.onepagecrm.models.serializers.LocalDateSerializer;
import com.onepagecrm.models.serializers.ZonedDateTimeSerializer;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ActionHelper {

    private static final Logger LOG = Logger.getLogger(ActionHelper.class.getSimpleName());

    /*
     * Constants.
     */

    public static final String STATUS_ASAP = "ASAP";
    public static final String STATUS_TODAY = "TODAY";
    public static final String STATUS_WAITING = "WAITING";
    public static final String SUNDAY = "sunday";
    public static final String WEEKEND = "weekend";

    public static final int COLOR_ASAP_OVERDUE = OPCRMColors.FLAG_RED;
    public static final int COLOR_TODAY = OPCRMColors.FLAG_ORANGE;
    public static final int COLOR_FUTURE_WAITING = OPCRMColors.FLAG_GREY_BROWN;
    public static final int COLOR_DEFAULT = COLOR_FUTURE_WAITING;

    /**
     * Default date is TODAY.
     *
     * @return LocalDate object for TODAY.
     */
    public static LocalDate defaultDate() {
        return defaultDateTime().toLocalDate();
    }

    /**
     * Default date/time is TODAY at 9am.
     *
     * @return LocalDateTime object for TODAY at 9am.
     */
    public static LocalDateTime defaultDateTime() {
        // Default is TODAY at 9am.
        return DateTimeHelper.nowLocal()
                .withHour(9)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * Default date/time is TODAY at 9am w/ time zone info.
     *
     * @param zoneId - of the device / OnePage settings.
     * @return ZonedDateTime object for TODAY at 9am.
     */
    public static ZonedDateTime defaultDateTime(ZoneId zoneId) {
        // Default is TODAY at 9am.
        return ZonedDateTime.of(defaultDateTime(), zoneId);
    }

    public static Action promote(ZoneId zoneId, PredefinedAction predefined) {
        return promote(zoneId, predefined, null);
    }

    public static Action promote(ZoneId zoneId, PredefinedAction predefined, String notWorkingDays) {
        final String actionText = predefined != null ? predefined.getText() : null;
        int daysToBeAdded = predefined != null ? predefined.getDays() : 0;

        // Default date is TODAY at 9am.
        LocalDate today = DateTimeHelper.today();

        // Skip weekend/sunday if set
        if (notWorkingDays != null) {
            boolean isSunday = today.getDayOfWeek().plus(daysToBeAdded) == DayOfWeek.SUNDAY;
            boolean isSaturday = today.getDayOfWeek().plus(daysToBeAdded) == DayOfWeek.SATURDAY;
            if (SUNDAY.equals(notWorkingDays) && isSunday) {
                daysToBeAdded += 1;
            } else if (WEEKEND.equals(notWorkingDays) && (isSunday || isSaturday)) {
                daysToBeAdded += isSaturday ? 2 : 1;
            }
        }

        ZonedDateTime todayZdt = defaultDateTime(zoneId);
        // Apply the updated date/time to action.
        return new Action()
                .setText(actionText)
                .setDate(today.plusDays(daysToBeAdded))
                .setExactTime(todayZdt.plusDays(daysToBeAdded));
    }

    /*
     * Dates.
     */

    public static String formatFriendlyDate(Action action) {
        if (action == null) {
            return null;
        }

        if (action.getDate() != null) {
            // Return date in format "MMM dd" (uppercase).
            return DateTimeHelper.formatDateFriendly(action.getDate()).toUpperCase(Locale.ENGLISH);

        } else if (action.getStatus() != null) {
            // Return status (uppercase).
            return action.getStatus().toString().toUpperCase();
        }

        return null; // <- needed to correctly display contacts w/out NA's in Action Stream.
    }

    public static String formatFriendlyActionText(ZoneId zoneId, boolean is24hr, Action action) {
        final String actionText = action != null && action.getText() != null ? action.getText() : "";
        if (action == null || action.getExactTime() == null) {
            return actionText;
        }
        return formatTimeAndActionText(zoneId, is24hr, action);
    }

    public static String formatFriendlyTimeAndDate(ZoneId zoneId, boolean is24hr, Action action) {
        final String actionText = action != null && action.getText() != null ? action.getText() : "";
        if (action == null || action.getExactTime() == null) {
            return actionText;
        }
        return formatTimeAndDate(zoneId, is24hr, action);
    }

    public static String formatDate(Action action) {
        final LocalDate date = action != null ? action.getDate() : DateTimeHelper.today();
        return LocalDateSerializer.getInstance().format(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
    }

    public static String formatDateYear(Action action) {
        final LocalDate date = action != null ? action.getDate() : DateTimeHelper.today();
        return LocalDateSerializer.getInstance().format(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE_YEAR);
    }

    public static String formatTimeAndActionText(ZoneId zoneId, boolean is24hr, Action action) {
        final String actionText = action != null && action.getText() != null ? action.getText() : "";
        final ZonedDateTime exactTime = action != null && action.getExactTime() != null
                ? action.getExactTime(zoneId)
                : defaultDateTime(zoneId);
        final String time = ZonedDateTimeSerializer.getInstance().format(exactTime, DateTimeHelper.timeFormat(is24hr));
        return String.format("%s %s", time, actionText);
    }

    public static String formatTimeAndDate(ZoneId zoneId, boolean is24hr, Action action) {
        final ZonedDateTime exactTime = action != null && action.getExactTime(zoneId) != null
                ? action.getExactTime(zoneId)
                : defaultDateTime(zoneId);
        return ZonedDateTimeSerializer.getInstance().format(exactTime, DateTimeHelper.timeDateYearFormat(is24hr));
    }

    /*
     * Flags/colours.
     */

    public static int calculateFlagColor(Action action) {
        if (action == null) {
            return COLOR_DEFAULT;
        }
        return calculateFlagColor(action.getDate(), action.getStatus());
    }

    private static int calculateFlagColor(LocalDate date, Action.Status status) {
        if (date != null) {
            return calculateFlagColor(date);
        } else if (status != null) {
            return calculateFlagColor(status);
        }
        return COLOR_DEFAULT;
    }

    private static int calculateFlagColor(LocalDate date, String status) {
        if (date != null) {
            return calculateFlagColor(date);
        } else if (status != null) {
            return calculateFlagColor(status);
        }
        return COLOR_DEFAULT;
    }

    private static int calculateFlagColor(LocalDate date) {
        if (date == null) {
            return COLOR_DEFAULT;
        }

        final LocalDate today = DateTimeHelper.today();

        if (date.isAfter(today)) {
            return COLOR_FUTURE_WAITING;
        } else if (date.isEqual(today)) {
            return COLOR_TODAY;
        } else if (date.isBefore(today)) {
            return COLOR_ASAP_OVERDUE;
        }

        return COLOR_DEFAULT;
    }

    private static int calculateFlagColor(Action.Status status) {
        if (status == null) {
            return COLOR_DEFAULT;
        }
        switch (status) {
            case ASAP: {
                return COLOR_ASAP_OVERDUE;
            }
            case WAITING: {
                return COLOR_FUTURE_WAITING;
            }
        }
        return COLOR_DEFAULT;
    }

    private static int calculateFlagColor(String status) {
        if (TextHelper.isEmpty(status)) {
            return COLOR_DEFAULT;
        }

        if (STATUS_WAITING.equalsIgnoreCase(status)) {
            return COLOR_FUTURE_WAITING;
        } else if (STATUS_TODAY.equalsIgnoreCase(status)) {
            return COLOR_TODAY;
        } else if (STATUS_ASAP.equalsIgnoreCase(status)) {
            return COLOR_ASAP_OVERDUE;
        }

        return COLOR_DEFAULT;
    }
}
