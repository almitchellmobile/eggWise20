package net.almitchellmobile.eggwise20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.almitchellmobile.eggwise20.adapter.EggBatchAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggSetting;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EggBatchListActivity extends AppCompatActivity implements EggBatchAdapter.OnEggBatchItemClick{

    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private EggWiseDatabse eggWiseDatabse;
    private List<EggSetting> eggSetting;
    private EggBatchAdapter eggBatchAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_batch_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        displayList();

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void displayList(){
        eggWiseDatabse = EggWiseDatabse.getInstance(EggBatchListActivity.this);
        new EggBatchListActivity.RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggSetting>> {

        private WeakReference<EggBatchListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(EggBatchListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<EggSetting> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().eggWiseDatabse.getEggSettingDao().getEggSetting();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<EggSetting> eggSetting) {
            if (eggSetting!=null && eggSetting.size()>0 ){
                activityReference.get().eggSetting.clear();
                activityReference.get().eggSetting.addAll(eggSetting);
                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().eggBatchAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg =  (TextView) findViewById(R.id.tv_empty_egg_batches);
        FloatingActionButton fabEggBatchList = (FloatingActionButton) findViewById(R.id.fab_egg_batch_list);
        fabEggBatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivityForResult(new Intent(EggBatchListActivity.this, AddEggBatchActivity.class),100);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_egg_batch_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(EggBatchListActivity.this));
        eggSetting = new ArrayList<EggSetting>();
        eggBatchAdapter = new EggBatchAdapter(eggSetting,  EggBatchListActivity.this);
        recyclerView.setAdapter(eggBatchAdapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                eggSetting.add((EggSetting) data.getSerializableExtra("eggSetting"));
            } else if (resultCode == 2) {
                eggSetting.set(pos, (EggSetting) data.getSerializableExtra("eggSetting"));
            }
            listVisibility();
        }
    }

    @Override
    public void onEggBatchClick(final int pos) {
        new AlertDialog.Builder(EggBatchListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                eggWiseDatabse.getEggSettingDao().deleteEggSetting(eggSetting.get(pos));
                                eggSetting.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                EggBatchListActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(EggBatchListActivity.this,
                                                AddEggBatchActivity.class).putExtra("eggSetting",eggSetting.get(pos)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (eggSetting.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        eggBatchAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }
}
