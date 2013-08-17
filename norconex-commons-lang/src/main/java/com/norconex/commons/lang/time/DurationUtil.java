/* Copyright 2010-2013 Norconex Inc.
 * 
 * This file is part of Norconex Commons Lang.
 * 
 * Norconex Commons Lang is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Norconex Commons Lang is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Norconex Commons Lang. If not, see <http://www.gnu.org/licenses/>.
 */
package com.norconex.commons.lang.time;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Utility class for duration-related features.  English and French are the 
 * two supported locale languages.  An unsupported locale will fallback to 
 * English.
 * @author Pascal Essiembre
 */
public final class DurationUtil {

    private DurationUtil() {
        super();
    }
    /**
     * Formats a duration using time units abbreviations according to supplied 
     * locale. All matching units are returned.
     * @param locale locale of time unit names
     * @param duration the duration to format
     * @param maxUnits the number of different time units to return (zero or 
     *                 less returns all matching units)
     * @return formatted duration
     */
    public static String formatShort(Locale locale, long duration) {
        return format(locale, duration, false, -1);
    }
    /**
     * Formats a duration using time units full names according to supplied 
     * locale. All matching units are returned.
     * @param locale locale of time unit names
     * @param duration the duration to format
     * @return formatted duration
     */
    public static String formatLong(Locale locale, long duration) {
        return format(locale, duration, true, -1);
    }
    /**
     * Formats a duration using time units abbreviations according to supplied 
     * locale.
     * @param locale locale of time unit names
     * @param duration the duration to format
     * @param maxUnits the number of different time units to return (zero or 
     *                 less returns all matching units)
     * @return formatted duration
     */
    public static String formatShort(
            Locale locale, long duration, int maxUnits) {
        return format(locale, duration, false, maxUnits);
    }
    /**
     * Formats a duration using time units full names according to supplied 
     * locale.
     * @param locale locale of time unit names
     * @param duration the duration to format
     * @param maxUnits the number of different time units to return (zero or 
     *                 less returns all matching units)
     * @return formatted duration
     */
    public static String formatLong(
            Locale locale, long duration, int maxUnits) {
        return format(locale, duration, true, maxUnits);
    }

    private static String format(
            Locale locale, long duration, boolean islong, int maxUnits) {
        int max = Integer.MAX_VALUE;
        if (maxUnits > 0) {
            max = maxUnits;
        }
        long days = duration / DateUtils.MILLIS_PER_DAY;
        long accountedMillis = days * DateUtils.MILLIS_PER_DAY;
        long hours = (duration - accountedMillis) / DateUtils.MILLIS_PER_HOUR;
        accountedMillis += (hours * DateUtils.MILLIS_PER_HOUR);
        long mins = (duration - accountedMillis) / DateUtils.MILLIS_PER_MINUTE;
        accountedMillis += (mins * DateUtils.MILLIS_PER_MINUTE);
        long secs = (duration - accountedMillis) / DateUtils.MILLIS_PER_SECOND;
        StringBuilder b = new StringBuilder();
        int unitCount = 0;
        // days
        if (days > 0) {
            b.append(getString(locale, days, "day", islong));
            unitCount++;
        }
        // hours
        if ((hours > 0 || b.length() > 0) && unitCount < max) {
            b.append(getString(locale, hours, "hour", islong));
            unitCount++;
        }
        // minutes
        if ((mins > 0 || b.length() > 0) && unitCount < max) {
            b.append(getString(locale, mins, "minute", islong));
            unitCount++;
        }
        // seconds
        if (unitCount < max) {
            b.append(getString(locale, secs, "second", islong));
        }
        return b.toString().trim();
    }
    private static String getString(
            Locale locale, long unitValue, final String key, boolean islong) {
        String k = key;
        if (islong && unitValue > 1) {
            k += "s";
        }
        String pkg = ClassUtils.getPackageName(DurationUtil.class);
        ResourceBundle time;
        if (locale != null 
                && Locale.FRENCH.getLanguage().equals(locale.getLanguage())) {
            time = ResourceBundle.getBundle(pkg + ".time", Locale.FRENCH);
        } else {
            time = ResourceBundle.getBundle(pkg + ".time");
        }
        if (islong) {
            return " " + unitValue + " " + time.getString("timeunit.long." + k);
        }
        return unitValue + time.getString("timeunit.short." + k);
    }
}
