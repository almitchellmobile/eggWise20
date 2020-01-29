package net.almitchellmobile.eggwise20;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

public class AddWeightLossActivity extends AppCompatActivity {

    private TextView text_batch_label,
            text_number_of_eggs_remaining,
            text_incubator_name,
            text_set_date;
    private AutoCompleteTextView et_egg_label,
            et_reading_date,
            et_reading_day_number,
            et_egg_weight,
            et_weight_loss_comment;
    private Button button_save_add_weight_loss, button_save_list_weight_loss, button_cancel;

    private EggWiseDatabse eggWiseDatabse;
    private EggDaily eggDaily;
    private EggBatch eggBatch;
    private boolean update;

    Long settingID = 0L;
    String batchLabel = "";
    String eggLabel = "";
    String readingDate = "";
    String setDate = "";
    Integer readingDayNumber = 0;
    Double eggWeight = 0.0D;
    Double eggWeightAverage = 0.0D;
    String eggDailyComment = "";
    String incubatorName = "";
    Integer numberOfEggsRemaining = 0;
    //Integer numberOfEggs = 0;
    Double eggWeightSum = 0D;
    Double actualWeightLossPercent = 0.0D;
    Double targetWeightLossPercent = 0.0D;
    Double weightLossDeviation = 0.0D;
    Integer incubationDays = 0;
    Double eggWeightAverageCurrent = 0.0D;
    Integer targetWeightLossInteger = 0;


    android.icu.util.Calendar myCalendar = android.icu.util.Calendar.getInstance();
    String myFormat = "MM/dd/yy"; //In which you need put here
    android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight_loss);
        Toolbar toolbar_add_weight_loss = findViewById(R.id.toolbar_add_weight_loss);
        setSupportActionBar(toolbar_add_weight_loss);
        //toolbar_add_weight_loss.setTitle("Add Egg Daily");


        eggWiseDatabse = EggWiseDatabse.getInstance(AddWeightLossActivity.this);

        text_batch_label = findViewById(R.id.text_batch_label);
        text_number_of_eggs_remaining = findViewById(R.id.text_number_of_eggs_remaining);
        text_incubator_name = findViewById(R.id.text_incubator_name);
        text_set_date = findViewById(R.id.text_set_date);

        et_egg_label = findViewById(R.id.et_egg_label);
        et_egg_weight = findViewById(R.id.et_egg_weight);
        et_reading_date = findViewById(R.id.et_reading_date);
        et_reading_date.setFocusable(true);

        DatePickerDialog.OnDateSetListener setDateDatePickerDialog = new
                DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(android.icu.util.Calendar.YEAR, year);
                        myCalendar.set(android.icu.util.Calendar.MONTH, monthOfYear);
                        myCalendar.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabelReadingDate();
                        //et_hatch_date.requestFocus();
                    }

                };
        et_reading_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(AddWeightLossActivity.this, setDateDatePickerDialog, myCalendar
                            .get(android.icu.util.Calendar.YEAR), myCalendar.get(android.icu.util.Calendar.MONTH),
                            myCalendar.get(android.icu.util.Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });
        //et_reading_day_number = findViewById(R.id.et_reading_day_number);
        et_weight_loss_comment = findViewById(R.id.et_weight_loss_comment);


        button_save_list_weight_loss = findViewById(R.id.button_save_list_weight_loss);
        button_save_list_weight_loss.setText("Save");
        button_save_list_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateInsertEggDaily();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        button_save_add_weight_loss = findViewById(R.id.button_save_add_weight_loss);
        button_save_add_weight_loss.setText("Save / Next");
        button_save_add_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateInsertEggDaily();
                    //setResult(eggDaily,3);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                //finish();
                //Intent intent1 = new Intent(AddWeightLossActivity.this, AddWeightLossActivity.class);
                //intent1.putExtra("WeightLossListActivity", eggBatch);
                //startActivity(intent1);
            }
        });
        button_save_add_weight_loss.setVisibility(View.GONE);

        button_cancel = findViewById(R.id.button_cancel);
        button_cancel.setText("Return");
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateInsertEggDaily();

                Intent intent1 = new Intent(AddWeightLossActivity.this, EggBatchListActivity.class);
                //intent1.putExtra("WeightLossListActivity", eggBatch);
                startActivity(intent1);
            }
        });

        getEggBatchData();
        eggDaily = null;
        if (getIntent().getSerializableExtra("eggDailyAdd") != null) {
            eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyAdd");
            update = false;
            button_save_add_weight_loss.setText("Save");
        } else if (getIntent().getSerializableExtra("eggDailyUpdate") != null) {
            eggDaily = (EggDaily) getIntent().getSerializableExtra("eggDailyUpdate");
            update = true;
            button_save_add_weight_loss.setText("Update");
        }
        if ( eggDaily!=null ){
            getSupportActionBar().setTitle("Update Egg Weight");

            batchLabel = eggDaily.getBatchLabel();
            setDate = eggDaily.getSetDate();
            incubatorName = eggDaily.getIncubatorName();
            numberOfEggsRemaining = eggDaily.getNumberOfEggsRemaining();
            targetWeightLossInteger = eggDaily.getTargetWeightLossInteger();
            incubationDays = eggDaily.getIncubationDays();

            text_batch_label.setText(parseHTMLBold("<B>Batch Label:</B> " + eggDaily.getBatchLabel()));
            text_set_date.setText(parseHTMLBold("<B>Set Date:</B> " + eggDaily.getSetDate()));
            text_incubator_name.setText(parseHTMLBold("<B>Incubator Name:</B> " + eggDaily.getIncubatorName()));
            text_number_of_eggs_remaining.setText(parseHTMLBold("<B>Number Of Eggs Remaining:</B> " + eggDaily.getNumberOfEggsRemaining()));

            if (update == true) {
                eggLabel = eggDaily.getEggLabel();
                readingDate = eggDaily.getReadingDate();
                readingDayNumber = eggDaily.getReadingDayNumber();
                eggWeight = eggDaily.getEggWeight();
                eggDailyComment = eggDaily.getEggDailyComment();

                et_egg_label.setText(eggLabel);
                et_reading_date.setText(readingDate);
                et_egg_weight.setText(eggWeight.toString());
                et_weight_loss_comment.setText(eggDailyComment);
            }

        }

    }

    private void getEggBatchData() {
        eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch");
        if ( eggBatch!=null ){

           //getSupportActionBar().setTitle("Update Egg Batch");
            //update = true;
            //button_save_add_weight_loss.setText("Update");

            batchLabel = eggBatch.getBatchLabel();
            setDate = eggBatch.getSetDate();
            incubatorName = eggBatch.getIncubatorName();
            numberOfEggsRemaining = eggBatch.getNumberOfEggs();
            targetWeightLossInteger = eggBatch.getTargetWeightLoss();
            incubationDays = eggBatch.getIncubationDays();
            text_batch_label.setText(parseHTMLBold("<B>Batch Label:</B> " + eggBatch.getBatchLabel()));
            text_set_date.setText(parseHTMLBold("<B>Set Date:</B> " + eggBatch.getSetDate()));
            text_incubator_name.setText(parseHTMLBold("<B>Incubator Name:</B> " + eggBatch.getIncubatorName()));
            text_number_of_eggs_remaining.setText(parseHTMLBold("<B>Number Of Eggs Remaining:</B> " + eggBatch.getNumberOfEggs()));
        }
    }


    private CharSequence parseHTMLBold(String InputString) {

        return  HtmlCompat.fromHtml(InputString, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    private void updateLabelReadingDate() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat(myFormat, Locale.US);
        readingDate = sdf.format(myCalendar.getTime());
        et_reading_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateInsertEggDaily() throws java.text.ParseException {

        //numberOfEggsRemaining = 7;

        if (update){

            eggDaily.setBatchLabel(batchLabel);
            eggDaily.setSetDate(setDate);
            eggDaily.setIncubatorName(incubatorName);

            eggDaily.setNumberOfEggsRemaining(numberOfEggsRemaining);
            eggDaily.setIncubationDays(incubationDays);
            eggDaily.setTargetWeightLossInteger(targetWeightLossInteger);

            eggDaily.setEggLabel(et_egg_label.getText().toString());
            eggDaily.setEggWeight(Double.parseDouble(et_egg_weight.getText().toString()));
            eggDaily.setReadingDate(et_reading_date.getText().toString());
            readingDayNumber = computeReadingDateNumber(setDate,et_reading_date.getText().toString());
            eggDaily.setReadingDayNumber(readingDayNumber);
            eggDaily.setEggDailyComment(et_weight_loss_comment.getText().toString());
            try {
                eggWiseDatabse.getEggDailyDao().updateEggDaily((eggDaily));
                setResult(eggDaily,2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            eggDaily = new EggDaily(settingID,
                    batchLabel,
                    eggLabel,
                    readingDate,
                    setDate,
                    readingDayNumber,
                    eggWeight,
                    eggWeightAverage,
                    eggDailyComment,
                    incubatorName,
                    numberOfEggsRemaining,

                    eggWeightSum,
                    actualWeightLossPercent,
                    targetWeightLossPercent,
                    weightLossDeviation,
                    eggWeightAverageCurrent,
                    targetWeightLossInteger,
                    incubationDays);

            new InsertTask(AddWeightLossActivity.this,eggDaily).execute();
        }
    }

    private boolean isEmptyField (EditText editText){
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(this, "Field " + editText.getHint() + " is empty!", Toast.LENGTH_SHORT).show();
        return result;
    }

    private Integer computeReadingDateNumber(String eggSetDate, String readingDate)
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

    private void setResult(EggDaily eggDaily, int flag){
        setResult(flag,new Intent().putExtra("eggDaily",eggDaily)
                .putExtra("eggBatch", eggBatch));
        finish();
        /*if (flag == 1) {
            Intent intent1 = new Intent(AddWeightLossActivity.this, WeightLossListActivity.class);
            intent1.putExtra("eggBatch", eggBatch);
            intent1.putExtra("eggDaily", eggDaily);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(AddWeightLossActivity.this, AddWeightLossActivity.class);
            intent2.putExtra("eggBatch", eggBatch);
            intent2.putExtra("eggDaily", eggDaily);
        }*/
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddWeightLossActivity> activityReference;
        private EggDaily eggDaily;



        // only retain a weak reference to the activity
        InsertTask(AddWeightLossActivity context, EggDaily eggDaily) {
            activityReference = new WeakReference<>(context);
            this.eggDaily = eggDaily;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().eggWiseDatabse.getEggDailyDao().insertEggDaily(eggDaily);
            eggDaily.setEggDailyID(j);
            Log.e("ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(eggDaily,1);
                //activityReference.get().finish();
            }

        }
    }

}
