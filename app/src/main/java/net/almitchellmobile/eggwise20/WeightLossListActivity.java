package net.almitchellmobile.eggwise20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.almitchellmobile.eggwise20.adapter.EggWeightAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggDaily;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WeightLossListActivity extends AppCompatActivity implements EggWeightAdapter.OnEggWeightItemClick {

    private TextView tv_empty_weight_loss_message;
    private RecyclerView recyclerViewWeightLossList;
    private EggWiseDatabse eggWiseDatabse;
    private List<EggDaily> eggDailyList;
    private EggWeightAdapter eggWeightAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();
        displayList();
    }

    private void displayList(){
        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossListActivity.this);
        new WeightLossListActivity.RetrieveTask(this).execute();
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_empty_weight_loss_message =  (TextView) findViewById(R.id.tv_empty_weight_loss_message);
        FloatingActionButton fabWeightLossList = (FloatingActionButton) findViewById(R.id.fabWeightLossList);
        fabWeightLossList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivityForResult(new Intent(WeightLossListActivity.this, AddWeightLossActivity.class),100);
            }
        });

        recyclerViewWeightLossList = findViewById(R.id.recycler_view_weight_loss_list);
        recyclerViewWeightLossList.setLayoutManager(new LinearLayoutManager(WeightLossListActivity.this));
        eggDailyList = new ArrayList<EggDaily>();
        eggWeightAdapter = new EggWeightAdapter(eggDailyList,  WeightLossListActivity.this);
        recyclerViewWeightLossList.setAdapter(eggWeightAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                eggDailyList.add((EggDaily) data.getSerializableExtra("eggDaily"));
            } else if (resultCode == 2) {
                eggDailyList.set(pos, (EggDaily) data.getSerializableExtra("eggDaily"));
            }
            listVisibility();
        }
    }


    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggDaily>> {

        private WeakReference<WeightLossListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(WeightLossListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<EggDaily> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<EggDaily> eggDailyList) {
            if (eggDailyList!=null && eggDailyList.size()>0 ){
                activityReference.get().eggDailyList.clear();
                activityReference.get().eggDailyList.addAll(eggDailyList);
                // hides empty text view
                activityReference.get().tv_empty_weight_loss_message.setVisibility(View.GONE);
                activityReference.get().eggWeightAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onEggWeightClick(int pos) {
        new AlertDialog.Builder(WeightLossListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                eggWiseDatabse.getEggDailyDao().deleteEggDaily(eggDailyList.get(pos));
                                eggDailyList.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                WeightLossListActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(WeightLossListActivity.this,
                                                AddWeightLossActivity.class).putExtra("eggDailyList",eggDailyList.get(pos)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (eggDailyList.size() == 0){ // no item to display
            if (tv_empty_weight_loss_message.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        tv_empty_weight_loss_message.setVisibility(emptyMsgVisibility);
        eggWeightAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }
}
