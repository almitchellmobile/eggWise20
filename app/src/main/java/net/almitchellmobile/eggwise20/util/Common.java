package net.almitchellmobile.eggwise20.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.os.Build;
import android.provider.CalendarContract;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.ConfigurationOptionsActivity;
import net.almitchellmobile.eggwise20.EggBatchListActivity;
import net.almitchellmobile.eggwise20.EggWiseMainActivity;
import net.almitchellmobile.eggwise20.FeedbackActivity;
import net.almitchellmobile.eggwise20.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class Common {

    public static String PREF_TEMPERATURE_ENTERED_IN = "Celsius";
    public static String PREF_HUMIDITY_MEASURED_WITH = "Humidity %";
    public static String PREF_WEIGHT_ENTERED_IN = "Grams";
    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Integer PREF_DEFAULT_WEIGHT_LOSS_PERCENT_INTEGER = 3;
    public static Integer PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 3;
    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 13.0F;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.5F;

    public static Boolean COMPLETED_ONBOARDING_PREF_EGG_WISE_MAIN = false;
    public static Boolean COMPLETED_ONBOARDING_PREF_ADD_WEIGHT_LOSS = false;
    public static Boolean COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST = false;
    public static Boolean COMPLETED_ONBOARDING_PREF_ADD_BATCH = false;
    public static Boolean COMPLETED_ONBOARDING_PREF_BATCH_LIST = false;
    public static Boolean COMPLETED_ONBOARDING_PREF_ALL = false;
    public static Boolean SET_UP_ONBOARDING_PREF = false;

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";

    public Common() {
    }

    public static String getExtraText(Context ctx) {

        String extraText = "";
        String versionName = "";
        int versionCode = 0;
        try {
            versionName = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
            versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        extraText = "Version Name: " + versionName + ", Version Code: " + versionCode + ".";

        return extraText;
    }

    public void menuOptions (MenuItem item, Context ctx, AppCompatActivity act) {

        try {
            switch (item.getItemId()) {
                case R.id.egg_batch_mngt:
                    Intent intent = new Intent(ctx,
                            EggBatchListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    act.startActivity(intent);
                    break;
                case R.id.quick_start:
                    sharedpreferences = act.getSharedPreferences(mypreference,Context.MODE_PRIVATE);
                    editor = sharedpreferences.edit();
                    editor.putBoolean("COMPLETED_ONBOARDING_PREF_SET_UP", false);
                    editor.commit();
                    Intent intent4 = new Intent(ctx,
                            EggWiseMainActivity.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    act.startActivity(intent4);
                    break;
                case R.id.settings:
                    Intent intent1 = new Intent(ctx,
                            ConfigurationOptionsActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    act.startActivity(intent1);
                    break;
                case R.id.home:
                    Intent intent2 = new Intent(ctx,
                            EggWiseMainActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    act.startActivity(intent2);
                    break;
                case R.id.send_feedback:
                    Intent intent3 = new Intent(ctx,
                            FeedbackActivity.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    act.startActivity(intent3);
                    break;

                case R.id.create_alert:
                    if (Build.VERSION.SDK_INT >= 14) {
                        //Calendar beginTime = null;
                        //Calendar endTime = null;
                        Calendar cal = Calendar.getInstance();
                        Intent intent6 = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000)
                                .putExtra(CalendarContract.Events.TITLE, "")
                                .putExtra(CalendarContract.Events.DESCRIPTION, "")
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                                .putExtra(Intent.EXTRA_EMAIL, "");
                        act.startActivity(intent6);
                    } else {
                        Calendar cal = Calendar.getInstance();
                        Intent intent7 = new Intent(Intent.ACTION_EDIT);
                        intent7.setType("vnd.android.cursor.item/event");
                        intent7.putExtra("beginTime", cal.getTimeInMillis());
                        intent7.putExtra("allDay", true);
                        intent7.putExtra("rrule", "");
                        intent7.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                        intent7.putExtra("title", "");
                        act.startActivity(intent7);
                    }
                    break;

                case R.id.exit_eggwise:
                    Intent intent6 = new Intent(act, EggWiseMainActivity.class);
                    intent6.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent6.putExtra("EXIT", true);
                    act.startActivity(intent6);
                    break;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*public void sendFeedback() {
        String extraText = "";
        String versionName = "";
        int versionCode = 0;

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        extraText = "Version Name: " + versionName + ", Version Code: " + versionCode + ".";


        final Intent _Intent = new Intent(Intent.ACTION_SEND);
        _Intent.setType("text/html");
        _Intent.putExtra(Intent.EXTRA_EMAIL, "almitchellmobile@gmail.com");
        _Intent.putExtra(Intent.EXTRA_SUBJECT, "eggWISE Mobile - User Feedback");
        _Intent.putExtra(Intent.EXTRA_TEXT, extraText);
        ctx.startActivity(Intent.createChooser(_Intent, "Send feedback"));

        Intent intent2 = new Intent(FeedbackActivity.this,
                EggWiseMainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }*/

    public static int convertDoubleToInteger(double valueIn) {
        double data = valueIn;
        return (int)data;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
