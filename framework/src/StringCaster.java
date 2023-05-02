package etu1840.framework.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class StringCaster {

    public static Object cast(String input) {
    	//try to cast a boolean
    	try {
            return Boolean.parseBoolean(input);
        } catch (NumberFormatException e) {}
        // Try to cast to integer
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {}
        // Try to cast to float
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {}

        // Try to cast to double
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {}

        // Try to cast to long

        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {}


        // Try to cast to date
        DateFormat[] dateFormats = {
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("MM/dd/yyyy"),
            new SimpleDateFormat("MM-dd-yyyy"),
            new SimpleDateFormat("dd-MM-yyyy"),
            new SimpleDateFormat("dd/MM/yyyy")
        };
        for (DateFormat dateFormat : dateFormats) {
            try {
                Date date = dateFormat.parse(input);
                return date;
            } catch (ParseException e) {}
        }

        // Try to cast to time
        DateFormat[] timeFormats = {
            new SimpleDateFormat("HH:mm:ss"),
            new SimpleDateFormat("hh:mm:ss a")
        };
        for (DateFormat timeFormat : timeFormats) {
            try {
                Date time = timeFormat.parse(input);
                return new Time(time.getTime());
            } catch (ParseException e) {}
        }


        // Try to cast to timestamp
        DateFormat[] timestampFormats = {
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS"),
            new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"),
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"),
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        };
        for (DateFormat timestampFormat : timestampFormats) {
            try {
                Date timestamp = timestampFormat.parse(input);
                return new Timestamp(timestamp.getTime());
            } catch (ParseException e) {}
        }

        // Could not cast to any known type, return input as string
        return input;
    }

}
