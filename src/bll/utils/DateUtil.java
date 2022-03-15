package bll.utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN_GUI = "dd/MM/yyyy HH:mm";

    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final DateTimeFormatter DATE_FORMATTER_GUI =
            DateTimeFormatter.ofPattern(DATE_PATTERN_GUI);

    public static String formatDateTime(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static String formatDateGui(LocalDateTime date) {
        if(date ==null){
            return null;
        }
        return DATE_FORMATTER_GUI.format(date);
    }

    public static LocalDateTime parseDateTime(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parseDateTime(dateString) != null;
    }
}