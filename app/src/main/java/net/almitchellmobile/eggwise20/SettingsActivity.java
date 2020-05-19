package net.almitchellmobile.eggwise20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.util.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {



    Button btn_save_settings;
    com.google.android.material.floatingactionbutton.FloatingActionButton fab_configuration_options;
    RadioGroup rg_temperature_entered_in, rg_humidity_measured_with, rg_weight_entered_in;
    RadioButton rb_celcius, rb_fahrenheit, rb_wet_bulb_readings, rb_relative_humidity_percentage,
            rb_grams, rb_ounces;
    AutoCompleteTextView et_days_to_hatcher_before_hatching, et_default_weight_loss_percentage,
            et_warn_weight_deviation_percentage;

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static final String mypreference = "mypreference";

    String prefValue = "";

    Common common;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save_settings = findViewById(R.id.btn_save_settings);
        btn_save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrefrences();
                Toast.makeText(SettingsActivity.this, "Settings saved!", Toast.LENGTH_LONG).show();
            }

        });


        rg_temperature_entered_in = findViewById(R.id.rg_temperature_entered_in);
        rb_celcius =  findViewById(R.id.rb_celcius);
        rb_fahrenheit  =  findViewById(R.id.rb_fahrenheit);
        rg_temperature_entered_in.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup rg_temperature_entered_in, int checkedId) {
                switch(checkedId){
                    case R.id.rb_celcius:
                        Common.PREF_TEMPERATURE_ENTERED_IN = rb_celcius.getText().toString();
                        break;
                    case R.id.rb_fahrenheit:
                        Common.PREF_TEMPERATURE_ENTERED_IN = rb_fahrenheit.getText().toString();
                        break;
                }
            }
        });

        rg_humidity_measured_with  =  findViewById(R.id.rg_humidity_measured_with);
        rb_wet_bulb_readings =  findViewById(R.id.rb_wet_bulb_readings);
        rb_wet_bulb_readings.setText("Wet Bulb Reading");
        rb_relative_humidity_percentage =  findViewById(R.id.rb_relative_humidity_percentage);
        rb_relative_humidity_percentage.setText("Humidity %");
        rg_humidity_measured_with.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup rg_humidity_measured_with, int checkedId) {
            switch(checkedId){
                case R.id.rb_relative_humidity_percentage:
                    Common.PREF_HUMIDITY_MEASURED_WITH = rb_relative_humidity_percentage.getText().toString();
                    break;
                case R.id.rb_wet_bulb_readings:
                    Common.PREF_HUMIDITY_MEASURED_WITH = rb_wet_bulb_readings.getText().toString();
                    break;
            }
        }
        });

        rg_weight_entered_in  =  findViewById(R.id.rg_weight_entered_in);
        rb_grams = findViewById(R.id.rb_grams);
        rb_ounces =  findViewById(R.id.rb_ounces);
        rg_weight_entered_in.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup rg_humidity_measured_with, int checkedId) {
                switch(checkedId){
                    case R.id.rb_grams:
                        Common.PREF_WEIGHT_ENTERED_IN = rb_grams.getText().toString();
                        break;
                    case R.id.rb_ounces:
                        Common.PREF_WEIGHT_ENTERED_IN = rb_ounces.getText().toString();
                        break;
                }
            }
        });


        et_days_to_hatcher_before_hatching =  findViewById(R.id.et_days_to_hatcher_before_hatching);
        et_days_to_hatcher_before_hatching.setSelectAllOnFocus(true);
        et_days_to_hatcher_before_hatching.setInputType(InputType.TYPE_CLASS_NUMBER);

        et_default_weight_loss_percentage =  findViewById(R.id.et_default_weight_loss_percentage);
        et_default_weight_loss_percentage.setSelectAllOnFocus(true);
        et_default_weight_loss_percentage.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        et_warn_weight_deviation_percentage =  findViewById(R.id.et_warn_weight_deviation_percentage);
        et_warn_weight_deviation_percentage.setSelectAllOnFocus(true);
        et_warn_weight_deviation_percentage.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        getPrefrences();

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
            case android.R.id.home:
                Intent intent = new Intent(SettingsActivity.this, EggWiseMainActivity.class);
                startActivity(intent);
                finish();
                return true;
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

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);



        if (!(et_days_to_hatcher_before_hatching.getText().toString().length() == 0)) {
            Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = Integer.parseInt(et_days_to_hatcher_before_hatching.getText().toString());
        } else {
            et_days_to_hatcher_before_hatching.setHint("Please enter Batch Days To Hatcher Before Hatching.");
            et_days_to_hatcher_before_hatching.setError("Days To Hatcher Before Hatching is required.");
            et_days_to_hatcher_before_hatching.requestFocus();
            return false;
        }
        if (!(et_default_weight_loss_percentage.getText().toString().length() == 0)) {
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Double.valueOf(et_default_weight_loss_percentage.getText().toString());
        } else {
            et_default_weight_loss_percentage.setHint("Please enter Default Weight Loss.");
            et_default_weight_loss_percentage.setError("Default Weight Loss is required.");
            et_default_weight_loss_percentage.requestFocus();
            return false;
        }
        if (!(et_warn_weight_deviation_percentage.getText().toString().length() == 0)) {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(et_warn_weight_deviation_percentage.getText().toString());
        } else {
            et_warn_weight_deviation_percentage.setHint("Please enter Warn if Weight Deviates By %.");
            et_warn_weight_deviation_percentage.setError("Warn if Weight Deviates By % is required.");
            et_warn_weight_deviation_percentage.requestFocus();
            return false;
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("temperature_entered_in", Common.PREF_TEMPERATURE_ENTERED_IN);
        editor.putString("humidity_measured_with", Common.PREF_HUMIDITY_MEASURED_WITH);
        editor.putString("weight_entered_in", Common.PREF_WEIGHT_ENTERED_IN);

        editor.putInt("days_to_hatcher_before_hatching", Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING);
        editor.putFloat("default_weight_loss_percentage", Float.valueOf(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE.toString()));
        editor.putFloat("warn_weight_deviation_percentage", Float.valueOf(Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE.toString()));

        editor.commit();

        return true;
    }



    public void getPrefrences() {

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        prefValue = sharedpreferences.getString("temperature_entered_in", "Fahrenheit");
        if (prefValue.toLowerCase().contains("fahrenheit")) {
            rb_fahrenheit.setChecked(true);
        } else {
            rb_celcius.setChecked(true);
        }

        prefValue = sharedpreferences.getString("humidity_measured_with", "Humidity %");
        if (prefValue.toLowerCase().contains("humidity")) {
            rb_relative_humidity_percentage.setChecked(true);
        } else {
            rb_wet_bulb_readings.setChecked(true);
        }

        prefValue = sharedpreferences.getString("weight_entered_in", "Ounces");
        if (prefValue.toLowerCase().contains("ounces")) {
            rb_ounces.setChecked(true);
        } else {
            rb_grams.setChecked(true);
        }

        et_days_to_hatcher_before_hatching.setText(Integer.toString(sharedpreferences.getInt("days_to_hatcher_before_hatching", 3)));
        et_default_weight_loss_percentage.setText(Float.toString(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F)));
        et_warn_weight_deviation_percentage.setText(Float.toString(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F)));

        Common.PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "Fahrenheit");
        Common.PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "Humidity %");
        Common.PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "Ounces");
        Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = Double.valueOf(sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F));
        Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));
    }

}
