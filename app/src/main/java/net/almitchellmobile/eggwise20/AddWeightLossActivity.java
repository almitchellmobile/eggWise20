package net.almitchellmobile.eggwise20;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddWeightLossActivity extends AppCompatActivity {

    private TextView text_batch_label,
            text_number_of_eggs_remaining,
            text_incubator_name,
            text_set_date;
    private EditText et_egg_label,
            et_reading_date,
            et_set_date,
            et_reading_day_number,
            et_egg_weight,
            et_weight_loss_comment;

    private EggWiseDatabse eggWiseDatabse;
    private EggDaily eggDaily;
    private  EggBatch eggBatch;
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
    Integer numberOfEggs = 0;

    Button button_save_add_weight_loss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight_loss);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eggWiseDatabse = EggWiseDatabse.getInstance(AddWeightLossActivity.this);

        text_batch_label = findViewById(R.id.text_batch_label);
        text_number_of_eggs_remaining = findViewById(R.id.text_number_of_eggs_remaining);
        text_incubator_name = findViewById(R.id.text_incubator_name);
        text_set_date = findViewById(R.id.text_set_date);

        et_egg_label = findViewById(R.id.et_egg_label);
        et_egg_weight = findViewById(R.id.et_egg_weight);
        et_reading_date = findViewById(R.id.et_reading_date);
        et_reading_day_number = findViewById(R.id.et_reading_day_number);
        et_weight_loss_comment = findViewById(R.id.et_weight_loss_comment);

        button_save_add_weight_loss = findViewById(R.id.button_save_add_weight_loss);

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatchWeightLoss"))!=null ){

            batchLabel = eggBatch.getBatchLabel();
            setDate = eggBatch.getSetDate();
            incubatorName = eggBatch.getIncubatorName();
            numberOfEggs = eggBatch.getNumberOfEggs();
        }

        button_save_add_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(isEmptyField(et_egg_label))) {
                    eggLabel = et_egg_label.getText().toString();
                }
                if (!(isEmptyField(et_egg_weight))) {
                    eggWeight = Double.parseDouble(et_egg_weight.getText().toString());
                }
                if (!(isEmptyField(et_reading_date))) {
                    readingDate = et_reading_date.getText().toString();
                }
                if (!(isEmptyField(et_reading_day_number))) {
                    readingDayNumber = Integer.parseInt(et_reading_day_number.getText().toString());
                }
                if (!(isEmptyField(et_weight_loss_comment))) {
                    eggDailyComment = et_weight_loss_comment.getText().toString();
                }

                if (update){
                    eggDaily.setEggLabel(eggLabel);
                    eggDaily.setEggWeight(eggWeight);
                    eggDaily.setReadingDayNumber(readingDayNumber);
                    eggDaily.setReadingDate(readingDate);
                    eggDaily.setEggDailyComment(eggDailyComment);

                    eggWiseDatabse.getEggDailyDao().updateEggDaily((eggDaily));
                    setResult(eggDaily,5);

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
                            numberOfEggsRemaining);

                    new InsertTask(AddWeightLossActivity.this,eggDaily).execute();
                }
            }
        });
    }

    private boolean isEmptyField (EditText editText){
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(this, "Field " + editText.getHint() + " is empty!", Toast.LENGTH_SHORT).show();
        return result;
    }

    private void setResult(EggDaily eggDaily, int flag){
        setResult(flag,new Intent().putExtra("eggDaily",eggDaily));
        finish();
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
                activityReference.get().finish();
            }
        }
    }

}
