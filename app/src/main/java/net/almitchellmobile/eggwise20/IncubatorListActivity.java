package net.almitchellmobile.eggwise20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IncubatorListActivity extends AppCompatActivity implements IncubatorsAdapter.OnIncubatorItemClick{

    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private EggWiseDatabse eggWiseDatabse;
    private List<Incubator> incubators;
    private IncubatorsAdapter incubatorAdapter;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incubator_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();
        displayList();
    }


    private void displayList(){
        eggWiseDatabse = EggWiseDatabse.getInstance(IncubatorListActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<Incubator>> {

        private WeakReference<IncubatorListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(IncubatorListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Incubator> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().eggWiseDatabse.getIncubatorDao().getIncubators();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Incubator> incubators) {
            if (incubators!=null && incubators.size()>0 ){
                activityReference.get().incubators.clear();
                activityReference.get().incubators.addAll(incubators);
                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().incubatorAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textViewMsg =  (TextView) findViewById(R.id.tv_empty_incubators);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                startActivityForResult(new Intent(IncubatorListActivity.this,AddIncubatorActivity.class),100);
            }
        });

        recyclerView = findViewById(R.id.recycler_view_incubator);
        recyclerView.setLayoutManager(new LinearLayoutManager(IncubatorListActivity.this));
        incubators = new ArrayList<Incubator>();
        incubatorAdapter = new IncubatorsAdapter(incubators,  IncubatorListActivity.this);
        recyclerView.setAdapter(incubatorAdapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                incubators.add((Incubator) data.getSerializableExtra("incubator"));
            } else if (resultCode == 2) {
                incubators.set(pos, (Incubator) data.getSerializableExtra("incubator"));
            }
            listVisibility();
        }
    }

    @Override
    public void onIncubatorClick(final int pos) {
        new AlertDialog.Builder(IncubatorListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                eggWiseDatabse.getIncubatorDao().deleteIncubator(incubators.get(pos));
                                incubators.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                IncubatorListActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(IncubatorListActivity.this,
                                                AddIncubatorActivity.class).putExtra("incubators",incubators.get(pos)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (incubators.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        incubatorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }


}
