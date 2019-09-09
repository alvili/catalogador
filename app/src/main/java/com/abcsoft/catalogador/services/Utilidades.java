package com.abcsoft.catalogador.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static String getStringFromDate(Date date){
        return sdf.format(date);
    }

    public static Date getDateFromString(String string){
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIntegerFromBoolean(Boolean bool){
        return (bool) ? 1 : 0;
    }

    public static Boolean getBooleanFromInteger(int val){
        return (val == 1) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static String ValidateBookStr(String str){
        return (str != null && !str.isEmpty()) ? str : "";
    }

}
