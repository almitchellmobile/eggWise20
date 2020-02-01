package net.almitchellmobile.eggwise20.util;

import android.content.Context;
import android.net.ParseException;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Common {

    public Common() {
    }

    public static String blankIfNullString (String value) {
        String newValue = "";
        try {
            if (value != null ) {
                newValue = value;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return	newValue;
    }

    public static  Date blankIfNullDate (Date value) {
        Date newValue = null;
        try {
            if (value != null ) {
                newValue = value;
            } else {
                newValue = null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return	newValue;
    }

    public static  Integer zeroIfNullInteger (Integer value) {
        Integer newValue = 0;
        try {
            if (value != null ) {
                newValue = value;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newValue;
    }

    /*public static  Integer zeroIfNullInteger (String value) {
        Integer newValue = 0;
        try {
            if (value != null ) {
                newValue = value;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newValue;
    }*/

    public static  Double zeroIfNullDouble (Double value) {
        Double newValue = 0.0;
        try {
            if (value != null ) {
                newValue = value;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newValue;
    }

    public static  Integer notSelectedIfNull (Integer value) {

        if (value == null ) {
            return -1;
        } else {
            return value;
        }
    }
    public static  String checkForNull (String valueIn) {

        String valueOut = "Not assigned";;
        try {

            if (valueIn != null) {
                valueOut = valueIn;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return valueOut;
        }
        return valueOut;
    }

    public static  boolean checkRequiredField (EditText editText, Context context){
        boolean result = editText.getText().toString().length() <= 0;
        if (result) {
            Toast.makeText(context, "Field " + editText.getHint() + " is a required field!", Toast.LENGTH_SHORT).show();
            editText.requestFocus();
        }
        return result;
    }

    public static  boolean checkRequiredFieldNumber (EditText editText, Context context){
        boolean result = editText.getText().toString().length() <= 0;
        if (result) {
            Toast.makeText(context, "Field " + editText.getHint() + " is a required field!", Toast.LENGTH_SHORT).show();
            editText.requestFocus();
        }
        return result;
    }

    public  static Integer computeReadingDateNumber(String eggSetDate, String readingDate)
            throws java.text.ParseException {
        Integer readingDayNumber = 0;
        android.icu.text.SimpleDateFormat format = new android.icu.text.SimpleDateFormat("MM/dd/yy", Locale.US);
        if (!(readingDate).equalsIgnoreCase("")) {
            Calendar cal = Calendar.getInstance();
            //Date today = null;
            Date setDate = null;
            Date readingDate_DateFormat = null;
            //String currentDateandTime = format.format(new Date());
            try {
                readingDate_DateFormat = format.parse(readingDate);//catch exception
                setDate = format.parse(eggSetDate.toString());//catch exception
                //today = format.parse(currentDateandTime);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            readingDayNumber = daysBetween(setDate,readingDate_DateFormat);

        }
        return readingDayNumber;
    }

    public static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public static Integer daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);
        Integer daysBetween = 0;

        if (sDate.equals(eDate)) {
            //Do nothing
        } else {
            while (sDate.before(eDate)) {
                sDate.add(Calendar.DAY_OF_MONTH, 1);
                daysBetween++;
            }
        }
        return daysBetween;
    }


}
