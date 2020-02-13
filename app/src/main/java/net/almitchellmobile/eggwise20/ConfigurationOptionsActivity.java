package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import net.almitchellmobile.eggwise20.util.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ConfigurationOptionsActivity extends AppCompatActivity {



    //Button btn_save, btn_retrieve, btn_clear;
    com.google.android.material.floatingactionbutton.FloatingActionButton fab_configuration_options;
    RadioGroup rg_temperature_entered_in, rg_humidity_measured_with, rg_weight_entered_in;
    RadioButton rb_celsius, rb_fahrenheit, rb_wet_bulb_readings, rb_relative_humidity_percentage,
            rb_grams, rb_ounces;
    AutoCompleteTextView et_days_to_hatcher_before_hatching, et_default_weight_loss_percentage,
            et_warn_weight_deviation_percentage;

    SharedPreferences sharedpreferences;
    public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;

    public static Integer PREF_DEFAULT_WEIGHT_LOSS_PERCENT_INTEGER = 3;

    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0F;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;

    public static final String mypreference = "mypref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_configuration_options = findViewById(R.id.fab_configuration_options);
        fab_configuration_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrefrences();
            }

        });

        //btn_save = findViewById(R.id.btn_save);
        //btn_retrieve =  findViewById(R.id.btn_retrieve);
        //btn_clear =  findViewById(R.id.btn_clear);

        rg_temperature_entered_in = findViewById(R.id.rg_temperature_entered_in);
        rg_humidity_measured_with  =  findViewById(R.id.rg_humidity_measured_with);
        rg_weight_entered_in  =  findViewById(R.id.rg_weight_entered_in);

        rb_celsius =  findViewById(R.id.rb_celsius);
        rb_fahrenheit  =  findViewById(R.id.rb_fahrenheit);
        rb_wet_bulb_readings =  findViewById(R.id.rb_wet_bulb_readings);
        rb_relative_humidity_percentage =  findViewById(R.id.rb_relative_humidity_percentage);
        rb_grams = findViewById(R.id.rb_grams);
        rb_ounces =  findViewById(R.id.rb_ounces);

        et_days_to_hatcher_before_hatching =  findViewById(R.id.et_days_to_hatcher_before_hatching);
        et_days_to_hatcher_before_hatching.setSelectAllOnFocus(true);

        et_default_weight_loss_percentage =  findViewById(R.id.et_default_weight_loss_percentage);
        et_default_weight_loss_percentage.setSelectAllOnFocus(true);

        et_warn_weight_deviation_percentage =  findViewById(R.id.et_warn_weight_deviation_percentage);
        et_warn_weight_deviation_percentage.setSelectAllOnFocus(true);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String prefValue = sharedpreferences.getString("temperature_entered_in", "Celsius");
        if (prefValue.toLowerCase().contains("Celsius")) {
            rb_celsius.setChecked(true);
        } else {
            rb_fahrenheit.setChecked(true);
        }

        prefValue = sharedpreferences.getString("humidity_measured_with", "Wet Bulb Readings");
        if (prefValue.toLowerCase().contains("Wet Bulb Readings")) {
            rb_wet_bulb_readings.setChecked(true);
        } else {
            rb_relative_humidity_percentage.setChecked(true);
        }

        prefValue = sharedpreferences.getString("weight_entered_in", "Grams");
        if (prefValue.toLowerCase().contains("Grams")) {
            rb_grams.setChecked(true);
        } else {
            rb_ounces.setChecked(true);
        }

        if (sharedpreferences.contains("days_to_hatcher_before_hatching")) {
            et_days_to_hatcher_before_hatching.setText(sharedpreferences.getInt("days_to_hatcher_before_hatching", 3));
        } else {
            et_days_to_hatcher_before_hatching.setText("3");
        }
        if (sharedpreferences.contains("default_weight_loss_percentage")) {
            et_default_weight_loss_percentage.setText(String.valueOf(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F)));
        } else {
            et_default_weight_loss_percentage.setText("13.0");
        }
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            et_warn_weight_deviation_percentage.setText(String.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F)));
        } else {
            et_warn_weight_deviation_percentage.setText("0.5");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            default:
                try {
                    Common common = new Common();
                    common.menuOptions(item, getApplicationContext(), this);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
        }
    }

    public Boolean savePrefrences() {

        Integer checkedTemperatureEnteredIn =  rg_temperature_entered_in.getCheckedRadioButtonId();
        if (rb_celsius.getId() == checkedTemperatureEnteredIn) {
            PREF_TEMPERATURE_ENTERED_IN = rb_celsius.getText().toString();
        } else {
            PREF_TEMPERATURE_ENTERED_IN = rb_fahrenheit.getText().toString();
        }
        Integer checkedHumidityMeasuredWith =  rg_humidity_measured_with.getCheckedRadioButtonId();
        if (rb_relative_humidity_percentage.getId() == checkedHumidityMeasuredWith) {
            PREF_HUMIDITY_MEASURED_WITH = rb_relative_humidity_percentage.getText().toString();
        } else {
            PREF_HUMIDITY_MEASURED_WITH = rb_wet_bulb_readings.getText().toString();
        }
        Integer checkedWeightEnteredIn =  rg_weight_entered_in.getCheckedRadioButtonId();
        if (rb_grams.getId() == checkedWeightEnteredIn) {
            PREF_WEIGHT_ENTERED_IN = rb_grams.getText().toString();
        } else {
            PREF_WEIGHT_ENTERED_IN = rb_ounces.getText().toString();
        }

        if (!(et_days_to_hatcher_before_hatching.getText().toString().length() == 0)) {
            PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = Integer.parseInt(et_days_to_hatcher_before_hatching.getText().toString());
        } else {
            et_days_to_hatcher_before_hatching.setHint("Please enter Batch Days To Hatcher Before Hatching.");
            et_days_to_hatcher_before_hatching.setError("Days To Hatcher Before Hatching is required.");
            et_days_to_hatcher_before_hatching.requestFocus();
            return false;
        }
        if (!(et_default_weight_loss_percentage.getText().toString().length() == 0)) {
            PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Float.parseFloat(et_default_weight_loss_percentage.getText().toString());
        } else {
            et_default_weight_loss_percentage.setHint("Please enter Default Weight Loss.");
            et_default_weight_loss_percentage.setError("Default Weight Loss is required.");
            et_default_weight_loss_percentage.requestFocus();
            return false;
        }
        if (!(et_warn_weight_deviation_percentage.getText().toString().length() == 0)) {
            PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Float.parseFloat(et_warn_weight_deviation_percentage.getText().toString());
        } else {
            et_warn_weight_deviation_percentage.setHint("Please enter Warn If Weight Deviates By %.");
            et_warn_weight_deviation_percentage.setError("Warn If Weight Deviates By % is required.");
            et_warn_weight_deviation_percentage.requestFocus();
            return false;
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("temperature_entered_in", PREF_TEMPERATURE_ENTERED_IN);
        editor.putString("humidity_measured_with", PREF_HUMIDITY_MEASURED_WITH);
        editor.putString("weight_entered_in", PREF_WEIGHT_ENTERED_IN);

        editor.putInt("days_to_hatcher_before_hatching", PREF_DAYS_TO_HATCHER_BEFORE_HATCHING);
        editor.putFloat("default_weight_loss_percentage", PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        editor.putFloat("warn_weight_deviation_percentage", PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE);

        editor.commit();

        return true;
    }



    public void getPrefrences(View view) {

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String prefValue = sharedpreferences.getString("temperature_entered_in", null);
        if (prefValue.toLowerCase().contains("celsius")) {
            rb_celsius.setChecked(true);
        } else {
            rb_fahrenheit.setChecked(true);
        }

        prefValue = sharedpreferences.getString("humidity_measured_with", null);
        if (prefValue.toLowerCase().contains("wet bulb readings")) {
            rb_wet_bulb_readings.setChecked(true);
        } else {
            rb_relative_humidity_percentage.setChecked(true);
        }

        prefValue = sharedpreferences.getString("weight_entered_in", null);
        if (prefValue.toLowerCase().contains("grams")) {
            rb_grams.setChecked(true);
        } else {
            rb_ounces.setChecked(true);
        }

        et_days_to_hatcher_before_hatching.setText(sharedpreferences.getString("days_to_hatcher_before_hatching", null));
        et_default_weight_loss_percentage.setText(sharedpreferences.getString("default_weight_loss_percentage", null));
        et_warn_weight_deviation_percentage.setText(sharedpreferences.getString("warn_weight_deviation_percentage", null));
    }

}
