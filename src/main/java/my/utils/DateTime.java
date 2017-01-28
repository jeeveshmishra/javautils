package my.utils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class DateTime {
    private Date source;
    private String format;
    private TimeZone timeZone;

    public DateTime() {
        this.source = new Date();
        this.format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        this.timeZone = TimeZone.getTimeZone("UTC");
    }

    public static DateTime now() {
        return new DateTime();
    }

    public static DateTime of(Instant instant) {
        return of(instant.toString());
    }

    public static DateTime of(Date date) {
        DateTime result = new DateTime();
        result.source = (Date) date.clone();

        return result;
    }

    public static DateTime of(String dateTime) {
        DateTime result = new DateTime();
        result.source = result.parse(dateTime);

        return result;
    }

    public static DateTime of(String dateTime, DateTimeFormat format) {
        return of(dateTime, format.getValue());
    }

    public static DateTime of(String dateTime, String format) {
        DateTime result = new DateTime();
        result.format = format;
        result.source = result.parse(dateTime);

        return result;
    }

    public static DateTime of(XMLGregorianCalendar xmlGregorianCalendar) {
        Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
        DateTime result = new DateTime();
        result.source = (Date) date.clone();

        return result;
    }

    public DateTime format(String format, String timeZone) {
        DateTime result = copy();
        result.format = format;
        result.timeZone = TimeZone.getTimeZone(timeZone);

        return result;
    }

    public DateTime format(String format) {
        DateTime result = copy();
        result.format = format;

        return result;
    }

    public DateTime format(DateTimeFormat format) {
        return format(format.getValue());
    }

    public DateFormat toDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(timeZone);

        return dateFormat;
    }

    public XMLGregorianCalendar toXmlGregorianCalendar() {
        DateTime result = copy();
        String dateTime = result.toString();

        return createFactory().newXMLGregorianCalendar(dateTime);
    }

    public Date toDate() {
        DateTime result = copy();

        return result.source;
    }

    public Instant toInstant() {
        return source.toInstant();
    }

    public DateTime copy() {
        DateTime copy = of(source);
        copy.format = this.format;
        copy.timeZone = this.timeZone;

        return copy;
    }

    public String toString() {
        return toDateFormat().format(source);
    }

    private Date parse(String source) {
        try {
            return toDateFormat().parse(source);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static DatatypeFactory createFactory() {
        try {
            return DatatypeFactory.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
