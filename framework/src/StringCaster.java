package etu1840.framework.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


public class StringCaster {

    public static Object cast(String input, Class<?> classe) {
        // Try to cast to integer
        System.out.println(classe.getName()+"name caster");

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {}
        // Try to cast to float

        if(classe.getName().compareTo("boolean")==0 || classe.getName().compareTo("java.lang.Boolean")==0){
            return Boolean.parseBoolean(input);
        }
        if(classe.getName().compareTo("float")==0 || classe.getName().compareTo("java.lang.Float")==0){
            return Float.parseFloat(input);
        }

        if(classe.getName().compareTo("double")==0 || classe.getName().compareTo("java.lang.Double")==0){
            return Double.parseDouble(input);
        }

        if(classe.getName().compareTo("long")==0 || classe.getName().compareTo("java.lang.Long")==0){
            return Long.parseLong(input);
        }

        

        if(classe.getName().compareTo("java.util.Date")==0){
            System.out.println("dutil.ate");
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
                    java.util.Date date = dateFormat.parse(input);
                    return date;
                } catch (ParseException e) {}
            }
        }
        if(classe.getName().compareTo("java.sql.Date")==0){
            return java.sql.Date.valueOf(input);
        }
        if(classe.getName().compareTo("java.sql.Time")==0){
            // Try to cast to time
            DateFormat[] timeFormats = {
                new SimpleDateFormat("HH:mm:ss"),
                new SimpleDateFormat("hh:mm:ss a")
            };
            for (DateFormat timeFormat : timeFormats) {
                try {
                    java.util.Date time = timeFormat.parse(input);
                    return new Time(time.getTime());
                } catch (ParseException e) {}
            }
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
                java.util.Date timestamp = timestampFormat.parse(input);
                return new Timestamp(timestamp.getTime());
            } catch (ParseException e) {}
        }

        // Could not cast to any known type, return input as string
        System.out.println("String string");
        return input;
    }

}
