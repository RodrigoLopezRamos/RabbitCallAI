package com.analia.common.util;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to manipulate  calendar .
 * <p>
 * Utility class to manipulate  calendar .
 */
/**
 * Utility class to manipulate  calendar .
 */

/**
 *
 * @author Rodrigo Lopez
 *
 */
public final class DateUtils {
    private static final SimpleDateFormat dobFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dobFormatterTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    /**
     * The number of Milliseconds in one Day. Used by the
     * {@link DateUtils#daysBetween(Date, Date)} method.
     */
    private static final long MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
    static GregorianCalendar calendar = new GregorianCalendar();


    private DateUtils() {
        // Not to be instantiated
    }

    /**
     *
     * @return
     */
    public static int getMinutesFromMidNigth(Date date, TimeZone timeZone) {
        Date m = DateUtils.getStartDate(date, timeZone);
        Calendar c = Calendar.getInstance(timeZone);
        c.setTime(date);
        long diff = (c.getTimeInMillis() - m.getTime());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return (int) minutes;
    }

    public static int getMinutesFromMidNigth(Date date) {
        Date m = DateUtils.getStartDate(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        long diff = (c.getTimeInMillis() - m.getTime());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return (int) minutes;
    }

    /**
     * Gets a new {@link Date} with the time set to the beginning of the day and
     * the same date as the provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return A new {@link Date} with the time set to the beginning of the day
     *         and the same date as the provided {@code date}.
     */
    public static Date getStartDate(Date date, TimeZone timeZone) {
        calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
        calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));
        return (Date) calendar.getTime().clone();
    }

    public static Date getStartDate(Date date) {
        //calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
        calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} with the time set to the end of the day and the
     * same date as the provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return A new {@link Date} with the time set to the end of the day and the
     *         same date as the provided {@code date}.
     */
    public static Date getEndDate(Date date) {
        calendar.setTime(date);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
        calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} with the time set to the beginning of the day and
     * the date set to yesterday relative to the provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return A new {@link Date} with the time set to the beginning of the day
     *         and the date set to yesterday relative to the provided
     *         {@code date}.
     */
    public static Date getPreviousStartDate(Date date) {
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
        calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
        calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} with the time set to tomorrow relative to the
     * provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return A new {@link Date} with the time set to tomorrow relative to the
     *         provided {@code date}.d
     */
    public static Date getNextDay(Date date) {
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} with the time set to yesterday relative to the
     * provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return A new {@link Date} with the time set to yesterday relative to the
     *         provided {@code date}.
     */
    public static Date getPreviousDay(Date date) {
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} {@code n} days into the future relative to the
     * provided {@code date}.
     *
     * <p>
     * <b>Note:</b> A negative {@code n} value will move into the past instead of
     * the future.
     * </p>
     *
     * @param date
     *           The {@link Date} to use.
     * @param n
     *           The number of days to go into the future.
     * @return A new {@link Date} {@code n} days into the future relative to the
     *         provided {@code date}.
     */
    public static Date getNextNDay(Date date, int n) {
        calendar.setTime(date);
        calendar.add(GregorianCalendar.DAY_OF_MONTH, n);
        return (Date) calendar.getTime().clone();
    }

    /**
     *
     * @param date
     * @param n
     * @return
     */
    public static Date setMinutes(Date date, int minutes) {
        calendar.setTime(date);
        calendar.add(GregorianCalendar.MINUTE, minutes);
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} {@code months} into the future relative to the
     * provided {@code date} set to the first day of the resultant month.
     *
     * <p>
     * <b>Note:</b> A negative {@code months} will move into the past instead of
     * the future.
     * </p>
     *
     * @param date
     *           The {@link Date} to use.
     * @param months
     *           The number of months to go into the future.
     * @return A new {@link Date} {@code months} into the future relative to the
     *         provided {@code date} set to the first day of the resultant month.
     */
    public static Date getBeginOfMonth(Date date, int months) {
        calendar.setTime(date);
        if (months != 0) calendar.add(GregorianCalendar.MONTH, months);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} {@code months} into the future relative to the
     * provided {@code date} set to the last day of the resultant month.
     *
     * <p>
     * <b>Note:</b> A negative {@code months} will move into the past instead of
     * the future.
     * </p>
     *
     * @param date
     *           The {@link Date} to use.
     * @param months
     *           The number of months to go into the future.
     * @return A new {@link Date} {@code months} into the future relative to the
     *         provided {@code date} set to the last day of the resultant month.
     */
    public static Date getEndOfMonth(Date date, int months) {
        calendar.setTime(date);
        if (months != 0) calendar.add(GregorianCalendar.MONTH, months);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} set to the beginning of the year as specified by
     * the provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return A new {@link Date} set to the beginning of the year as specified
     *         by the provided {@code date}.
     */
    public static Date getBeginOfYear(Date date) {
        calendar.setTime(date);
        calendar.set(GregorianCalendar.MONTH, calendar.getActualMinimum(GregorianCalendar.MONTH));
        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
        calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
        calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets a new {@link Date} set to the end of the year as specified by the
     * provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return A new {@link Date} set to the end of the year as specified by the
     *         provided {@code date}.
     */
    public static Date getEndOfYear(Date date) {
        calendar.setTime(date);
        calendar.set(GregorianCalendar.MONTH, calendar.getActualMaximum(GregorianCalendar.MONTH));
        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
        calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets the year of the provided {@code date}.
     *
     * @param date
     *           The {@link Date} to use.
     * @return The year of the provided {@code date}.
     */
    public static int getYearOfDate(Date date) {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Gets the {@link Date} that represents the end of the specified
     * {@code year}.
     *
     * @param year
     *           The year to get the end of.
     * @return A new {@link Date} that represents the end of the specified
     *         {@code year}.
     */
    public static Date getEndOfYear(int year) {
        calendar.set(GregorianCalendar.YEAR, year);
        calendar.set(GregorianCalendar.MONTH, calendar.getActualMaximum(GregorianCalendar.MONTH));
        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
        calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
        calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
        calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));
        return (Date) calendar.getTime().clone();
    }

    /**
     * Gets the number of months between the provided {@code startDate} and
     * {@code endDate}.
     *
     * <p>
     * <b>Note:</b> If {@code startDate} comes after {@code endDate} then the
     * number of months will be negative.
     * </p>
     *
     * @param startDate
     *           The start of the period to get the number of months between.
     * @param endDate
     *           The end of the period to get the number of months between.
     * @return The number of months between the provided {@code startDate} and
     *         {@code endDate}.
     */
    public static int monthsBetween(Date startDate, Date endDate) {
        calendar.setTime(startDate);
        int startMonth = calendar.get(GregorianCalendar.MONTH);
        int startYear = calendar.get(GregorianCalendar.YEAR);
        calendar.setTime(endDate);
        int endMonth = calendar.get(GregorianCalendar.MONTH);
        int endYear = calendar.get(GregorianCalendar.YEAR);
        return (endYear - startYear) * 12 + (endMonth - startMonth);
    }

    /**
     * Compares the provided {@code startDate} to the provided {@code endDate}.
     *
     * @param startDate
     *           The first {@link Date} to compare.
     * @param endDate
     *           The second {@link Date} to compare.
     * @return The value 0 if {@code startDate} represents the same time as
     *         {@code endDate}. A value less than 0 if {@code startDate}
     *         represents a time before {@code endDate}. A value greater than 0
     *         if {@code startDate} represents a time after {@code endDate}.
     */
    public static int compareDates(Date startDate, Date endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        return c1.compareTo(c2);
    }

    /**
     * Gets the number of days between the provided {@code startDate} and
     * {@code endDate}.
     *
     * <p>
     * <b>Note:</b> For the purposes of this method:
     * <ul>
     * <li>{@code 2012-01-01 00:00:00} and {@code 2012-01-01 23:59:59} are
     * considered 0 days apart.</li>
     * <li>{@code 2012-01-01 23:59:59} and {@code 2012-01-02 00:00:00} are
     * considered 1 day apart.</li>
     * <li>{@code 2012-01-01 12:00:00} and {@code 2012-01-02 13:00:00} are
     * considered 1 day apart.</li>
     * </ul>
     * </p>
     *
     * @param startDate
     *           The start {@link Date} of the period to find the number of days
     *           in.
     * @param endDate
     *           The end {@link Date} of the period to find the number of days
     *           in.
     * @return The number of days between the provided {@code startDate} and
     *         {@code endDate}.
     */
    public static int daysBetween(Date startDate, Date endDate, TimeZone timeZone) {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("The Start Calendar must be before the End Calendar");
        }

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(getStartDate(startDate, timeZone));

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(getStartDate(endDate, timeZone));

        // We will try to guess the number of days and
        // then use the Calendar to correct any error
        int days = (int) ((endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis()) / MILLISECONDS_IN_DAY);

        Calendar stepCalendar = Calendar.getInstance();
        stepCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
        stepCalendar.add(Calendar.DAY_OF_YEAR, days);

        int step = stepCalendar.before(endCalendar) ? 1 : -1;

        while (stepCalendar.getTimeInMillis() != endCalendar.getTimeInMillis()) {
            stepCalendar.add(Calendar.DAY_OF_YEAR, step);
            days = days + step;
        }

        return days;
    }

    /**
     * Gets the number of days between the provided {@code startCalendar} and
     * {@code endCalendar}.
     *
     * <p>
     * <b>Note:</b> For the purposes of this method:
     * <ul>
     * <li>{@code 2012-01-01 00:00:00} and {@code 2012-01-01 23:59:59} are
     * considered 0 days apart.</li>
     * <li>{@code 2012-01-01 23:59:59} and {@code 2012-01-02 00:00:00} are
     * considered 1 day apart.</li>
     * <li>{@code 2012-01-01 12:00:00} and {@code 2012-01-02 13:00:00} are
     * considered 1 day apart.</li>
     * </ul>
     * </p>
     *
     * @param startCalendar
     *           The start {@link Calendar} of the period to find the number of
     *           days in.
     * @param endDate
     *           The end {@link Calendar} of the period to find the number of
     *           days in.
     * @return The number of days between the provided {@code startCalendar} and
     *         {@code endCalendar}.
     */
    public static int daysBetween(Calendar startCalendar, Calendar endCalendar, TimeZone timeZone) {
        return daysBetween(startCalendar.getTime(), endCalendar.getTime(), timeZone);
    }

    /**
     * Parses a date out of the provided {@code dateToFormat}. The date must be
     * of the form {@code yyyy/MM/dd}.
     *
     * @param dateToFormat
     *           The {@link String} representation of the date we are trying to
     *           parse.
     * @return The {@link Date} that represents the provided {@code dateToFormat}
     *         .
     * @throws AnaliaException
     *            If the parse was unsuccessful.
     */
    public static Date formatDate(String dateToFormat) throws AnaliaException {
        Date date = null;
        if (dateToFormat != null && dateToFormat.trim().length() != 0) {
            try {
                date = dobFormatter.parse(dateToFormat);
            } catch (ParseException pe) {
                throw new AnaliaException(ExceptionCode.INVALID_DATE_FORMAT, null, pe);
            }
        }
        return date;
    }

    /**
     * Parses a date out of the provided {@code dateToFormat}. The date must be
     * of the form {@code yyyy/MM/dd/HH/mm}.
     *
     * @param dateToFormat
     *           The {@link String} representation of the date we are trying to
     *           parse.
     * @return The {@link Date} that represents the provided {@code dateToFormat}
     *         .
     * @throws AnaliaException
     *            If the parse was unsuccessful.
     */
    public static Date formatDateWithTime(String dateToFormat) throws AnaliaException {
        Date date = null;
        if (dateToFormat != null && dateToFormat.trim().length() != 0) {
            try {
                date = dobFormatterTime.parse(dateToFormat);
            } catch (ParseException pe) {
                throw new AnaliaException(ExceptionCode.INVALID_DATE_FORMAT, null, pe);
            }
        }
        return date;
    }

    /**
     * Formats the provided {@code dateToFormat} in the form {@code yyyy/MM/dd}.
     *
     * @param dateToFormat
     *           The {@link Date} to format.
     * @return The formatted representation of the provided {@code dateToFormat}.
     */
    public static String formatDate(Date dateToFormat) {
        String date = "";
        if (dateToFormat != null) {
            date = dobFormatter.format(dateToFormat);
        }
        return date;
    }

    /**
     * Formats the provided {@code dateToFormat} in the form
     * {@code yyyy/MM/dd/HH/mm}.
     *
     * @param dateToFormat
     *           The {@link Date} to format.
     * @return The formatted representation of the provided {@code dateToFormat}.
     */
    public static String formatDateTime(Date dateToFormat) {
        String date = null;
        if (dateToFormat != null) {
            date = dobFormatterTime.format(dateToFormat);
        }
        return date;
    }

    public static String convertUTCtimeToTimezone(Date utcDate, String timeZone) {
        dobFormatterTime.setTimeZone(TimeZone.getTimeZone(timeZone));
        return dobFormatterTime.format(utcDate);
    }

    /**
     * Returns the number of years between the provided {@code birthDate} and
     * now. Effectively calculating the age of a person with the provided
     * {@code birthDate}.
     *
     * @param birthDate
     *           The {@link Date} representing the birth day to use.
     * @return The number of years between now and the provided {@code birthDate}
     *         .
     */
    public static Integer getAge(Date birthDate) {
        Integer ageToReturn = null;
        if (birthDate != null) {
            // Create a calendar object with the date of birth
            Calendar dateOfBirth = Calendar.getInstance();
            dateOfBirth.setTime(birthDate);

            // Create a calendar object with today's date
            Calendar today = Calendar.getInstance();

            // Get age based on year
            int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

            // Add the tentative age to the date of birth to get this year's
            // birthday
            dateOfBirth.add(Calendar.YEAR, age);

            // If this year's birthday has not happened yet, subtract one from age
            if (today.before(dateOfBirth)) {
                age--;
            }
            ageToReturn = age;
        }
        return ageToReturn;
    }
}