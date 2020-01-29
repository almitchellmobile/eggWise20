package net.almitchellmobile.eggwise20.util;

import java.util.Date;

public class Common {

    public Common() {
    }

    public String blankIfNullString (String value) {
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

    public Date blankIfNullDate (Date value) {
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

    public Integer zeroIfNullInteger (Integer value) {
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

    public Double zeroIfNullDouble (Double value) {
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

    public Integer notSelectedIfNull (Integer value) {

        if (value == null ) {
            return -1;
        } else {
            return value;
        }
    }
    public String checkForNull (String valueIn) {

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

}
