package net.almitchellmobile.eggwise20;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddIncubatorActivity extends AppCompatActivity {

    private TextView et_incubator_name, et_,et_mfg_model,et_number_of_eggs;
    private EggWiseDatabse eggWiseDatabse;
    private Incubator incubator;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incubator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        et_incubator_name = findViewById(R.id.et_incubator_name);
        et_mfg_model = findViewById(R.id.et_mfg_model);
        et_number_of_eggs = findViewById(R.id.et_number_of_eggs);
        eggWiseDatabse = EggWiseDatabse.getInstance(AddIncubatorActivity.this);
        Button button = findViewById(R.id.button_save);
        if ( (incubator = (Incubator) getIntent().getSerializableExtra("incubator"))!=null ){
            getSupportActionBar().setTitle("Update Note");
            update = true;
            button.setText("Update");
            et_incubator_name.setText(incubator.getIncubatorName());
            et_mfg_model.setText(incubator.getMFGModel());
            et_number_of_eggs.setText(incubator.getNumberOfEggs());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    incubator.setIncubatorName(et_incubator_name.getText().toString());
                    incubator.setMFGModel(et_mfg_model.getText().toString());
                    incubator.setNumberOfEggs(Integer.parseInt(et_number_of_eggs.getText().toString()));
                    eggWiseDatabse.getIncubatorDao().updateIncubator((incubator));
                    setResult(incubator,3);
                }else {
                    incubator = new Incubator(et_incubator_name.getText().toString(),
                            et_mfg_model.getText().toString(), Integer.parseInt(et_number_of_eggs.getText().toString()));
                    new InsertTask(AddIncubatorActivity.this,incubator).execute();
                }
            }
        });
    }

    private void setResult(Incubator incubator, int flag){
        setResult(flag,new Intent().putExtra("incubator",incubator));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddIncubatorActivity> activityReference;
        private Incubator incubator;



        // only retain a weak reference to the activity
        InsertTask(AddIncubatorActivity context, Incubator incubator) {
            activityReference = new WeakReference<>(context);
            this.incubator = incubator;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().eggWiseDatabse.getIncubatorDao().insertIncubator(incubator);
            incubator.setIncubatorId(j);
            Log.e("ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(incubator,1);
                activityReference.get().finish();
            }
        }
    }

}
