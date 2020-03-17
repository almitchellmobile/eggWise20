package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    SharedPreferences.Editor editor;
    public static final String mypreference = "mypref";

    String prefValue = "";

    Common common;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_options);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab_configuration_options = findViewById(R.id.fab_configuration_options);
        fab_configuration_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrefrences();
                Toast.makeText(ConfigurationOptionsActivity.this, "Preferences saved!", Toast.LENGTH_SHORT).show();
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

        Common.PREF_TEMPERATURE_ENTERED_IN = "";
        Common.PREF_HUMIDITY_MEASURED_WITH = "";
        Common.PREF_WEIGHT_ENTERED_IN = "";

        Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;

        Common.PREF_DEFAULT_WEIGHT_LOSS_PERCENT_INTEGER = 3;

        Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0F;
        Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;

        Common.COMPLETED_ONBOARDING_PREF_EGG_WISE_MAIN = false;
        Common.COMPLETED_ONBOARDING_PREF_ADD_WEIGHT_LOSS = false;
        Common.COMPLETED_ONBOARDING_PREF_WEIGHT_LOSS_LIST = false;
        Common.COMPLETED_ONBOARDING_PREF_ADD_BATCH = false;
        Common.COMPLETED_ONBOARDING_PREF_BATCH_LIST = false;

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        prefValue = sharedpreferences.getString("temperature_entered_in", "Celsius");
        if (prefValue.compareToIgnoreCase("Celsius") == 0) {
            rb_celsius.setChecked(true);
        } else {
            rb_fahrenheit.setChecked(true);
        }

        prefValue = sharedpreferences.getString("humidity_measured_with", "Wet Bulb");
        if (prefValue.compareToIgnoreCase("Wet Bulb") == 0) {
            rb_wet_bulb_readings.setChecked(true);
        } else {
            rb_relative_humidity_percentage.setChecked(true);
        }

        prefValue = sharedpreferences.getString("weight_entered_in", "Grams");
        if (prefValue.compareToIgnoreCase("Grams") == 0) {
            rb_grams.setChecked(true);
        } else {
            rb_ounces.setChecked(true);
        }

        if (sharedpreferences.contains("days_to_hatcher_before_hatching")) {
            et_days_to_hatcher_before_hatching.setText(String.valueOf(sharedpreferences.getInt("days_to_hatcher_before_hatching", 3)));
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

    private void getTemperaturePref() {
        String prefValue = sharedpreferences.getString("temperature_entered_in", "Celsius");
        if (prefValue.compareToIgnoreCase("Celsius") == 0) {
            rb_celsius.setChecked(true);
        } else {
            rb_fahrenheit.setChecked(true);
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
            case R.id.send_feedback:
                final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
                _Intent.setType("text/html");
                _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, "almitchellmobile@gmail.com");
                _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "eggWISE Mobile - User Feedback");
                _Intent.putExtra(android.content.Intent.EXTRA_TEXT, Common.getExtraText(this));
                startActivity(Intent.createChooser(_Intent, "Send feedback"));
                return true;

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
            Common.PREF_TEMPERATURE_ENTERED_IN = rb_celsius.getText().toString();
        } else {
            Common.PREF_TEMPERATURE_ENTERED_IN = rb_fahrenheit.getText().toString();
        }
        Integer checkedHumidityMeasuredWith =  rg_humidity_measured_with.getCheckedRadioButtonId();
        if (rb_relative_humidity_percentage.getId() == checkedHumidityMeasuredWith) {
            Common.PREF_HUMIDITY_MEASURED_WITH = rb_relative_humidity_percentage.getText().toString();
        } else {
            Common.PREF_HUMIDITY_MEASURED_WITH = rb_wet_bulb_readings.getText().toString();
        }
        Integer checkedWeightEnteredIn =  rg_weight_entered_in.getCheckedRadioButtonId();
        if (rb_grams.getId() == checkedWeightEnteredIn) {
            Common.PREF_WEIGHT_ENTERED_IN = rb_grams.getText().toString();
        } else {
            Common.PREF_WEIGHT_ENTERED_IN = rb_ounces.getText().toString();
        }

        if (!(et_days_to_hatcher_before_hatching.getText().toString().length() == 0)) {
            Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = Integer.parseInt(et_days_to_hatcher_before_hatching.getText().toString());
        } else {
            et_days_to_hatcher_before_hatching.setHint("Please enter Batch Days To Hatcher Before Hatching.");
            et_days_to_hatcher_before_hatching.setError("Days To Hatcher Before Hatching is required.");
            et_days_to_hatcher_before_hatching.requestFocus();
            return false;
        }
        if (!(et_default_weight_loss_percentage.getText().toString().length() == 0)) {
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Float.parseFloat(et_default_weight_loss_percentage.getText().toString());
        } else {
            et_default_weight_loss_percentage.setHint("Please enter Default Weight Loss.");
            et_default_weight_loss_percentage.setError("Default Weight Loss is required.");
            et_default_weight_loss_percentage.requestFocus();
            return false;
        }
        if (!(et_warn_weight_deviation_percentage.getText().toString().length() == 0)) {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Float.parseFloat(et_warn_weight_deviation_percentage.getText().toString());
        } else {
            et_warn_weight_deviation_percentage.setHint("Please enter Warn If Weight Deviates By %.");
            et_warn_weight_deviation_percentage.setError("Warn If Weight Deviates By % is required.");
            et_warn_weight_deviation_percentage.requestFocus();
            return false;
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("temperature_entered_in", Common.PREF_TEMPERATURE_ENTERED_IN);
        editor.putString("humidity_measured_with", Common.PREF_HUMIDITY_MEASURED_WITH);
        editor.putString("weight_entered_in", Common.PREF_WEIGHT_ENTERED_IN);

        editor.putInt("days_to_hatcher_before_hatching", Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING);
        editor.putFloat("default_weight_loss_percentage", Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        editor.putFloat("warn_weight_deviation_percentage", Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE);

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
