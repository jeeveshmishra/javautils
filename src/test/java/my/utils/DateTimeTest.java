package my.utils;

import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.util.Date;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class DateTimeTest {

    @Test
    public void test_toString() {
        String expectedResult = "2017-01-25T15:44:11Z";

        String result = DateTime.of(expectedResult).toString();

        assertEquals(result, expectedResult);
    }

    @Test
    public void test_format() {
        String expectedResult = "2017-01-25";
        String source = "2017-01-25T15:44:11Z";

        String result = DateTime.of(source).format("yyyy-MM-dd").toString();

        assertEquals(result, expectedResult);
    }

    @Test
    public void test_format_enum() {
        String expectedResult = "2017-01-25";
        String source = "2017-01-25T15:44:11Z";

        String result = DateTime.of(source).format(DateTimeFormat.DATE).toString();

        assertEquals(result, expectedResult);
    }

    @Test
    public void test_copy_DateTime() {
        Date sourceDate = new Date();

        Date resultDate = DateTime.of(sourceDate).copy().toDate();

        assertEquals(resultDate, sourceDate);
    }

    @Test
    public void test_of_Date() {
        String source = "2017-01-25T15:44:11Z";
        Date expectedDate = DateTime.of(source).toDate();

        Date result = Date.from(Instant.parse(source));

        assertEquals(result, expectedDate);
    }

    @Test
    public void test_of_Instant() {
        String source = "2017-01-25T15:44:11Z";
        Instant expectedResult = DateTime.of(source).toInstant();

        Instant result = Instant.parse(source);

        assertEquals(result, expectedResult);
    }

    @Test
    public void test_of_dateTime_toXmlGregorianCalendar() throws DatatypeConfigurationException {
        String source = "2017-01-25T15:44:11Z";

        XMLGregorianCalendar expectedResult = DatatypeFactory.newInstance().newXMLGregorianCalendar(source);

        XMLGregorianCalendar actualResult = DateTime.of(source).toXmlGregorianCalendar();

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void test_of_dateFormat_toXmlGregorianCalendar() throws Exception {
        String source = "2017-01-25";

        XMLGregorianCalendar expectedResult = DatatypeFactory.newInstance().newXMLGregorianCalendar(source);
        XMLGregorianCalendar result = DateTime.of(source, DateTimeFormat.DATE).toXmlGregorianCalendar();

        assertEquals(result, expectedResult);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void test_Parse_Invalid_Date() {
        String testDateString = "Any String";

        DateTime dateTime = DateTime.of(testDateString);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void test_Parse_Invalid_Format() {
        String testDateString = "2017-01-25";

        DateTime dateTime = DateTime.of(testDateString, "Invalid_Format");
    }
}