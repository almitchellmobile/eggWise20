package net.almitchellmobile.eggwise20;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.almitchellmobile.eggwise20.adapter.EggBatchAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EggBatchListActivity extends AppCompatActivity implements EggBatchAdapter.OnEggBatchItemClick{

    SharedPreferences sharedpreferences;
    public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0F;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
    public static Integer PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 0;

    public static final String mypreference = "mypref";

    //public static String BATCH_LABEL = "";
    public static Long EGG_BATCH_ID = 0L;
    public static String EGG_LABEL = "";
    public static Double EGG_WEIGHT_SUM = 0.0;
    public static Double EGG_WEIGHT_AVG_CURRENT = 0.0;
    public static Double EGG_WEIGHT_AVG_DAY_0 = 0.0;
    public static Double ACTUAL_WEIGHT_LOSS_PERCENT = 0.0;
    public static Double TARGET_WEIGHT_LOSS_PERCENT = 0.0;
    public static Double WEIGHT_LOSS_DEVIATION = 0.0;
    public static String WEIGHT_LOSS_DEVIATION_MESSAGE = "";
    public static Integer TRACKING_OPTION = 0;
    public static Integer READING_DAY_NUMBER = 0;
    public static Integer TARGET_WEIGHT_LOSS_INTEGER = 0;
    public static Integer INCUBATION_DAYS = 0;
    public static Integer NUMBER_OF_EGGS_REMAINING = 0 ;
    public static DecimalFormat df = new DecimalFormat("00.00");

    private TextView textViewMsg;
    private RecyclerView recyclerViewEggBatchList;
    private EggWiseDatabse eggWiseDatabse;
    private List<EggBatch> eggBatchList;
    private EggBatchAdapter eggBatchAdapter;
    private int pos;
    Toolbar toolbar_egg_batch_list;
    SearchView searchView;

    Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_batch_list);
        toolbar_egg_batch_list = findViewById(R.id.toolbar_egg_batch_list);
        setSupportActionBar(toolbar_egg_batch_list);

        common = new Common();

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "");
        PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "");
        PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "");
        if (sharedpreferences.contains("days_to_hatcher_before_hatching")) {
            PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        } else {
            PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 0;
        }
        if (sharedpreferences.contains("default_weight_loss_percentage")) {
            PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F);
            //double data = PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE;
            //int value = (int)data;
            PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        } else {
            PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = 13.0F;
            PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 13;
        }
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F);
        } else {
            PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
        }


        initializeViews();
        displayList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                eggBatchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                eggBatchAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_search:
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


    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


    /*@Override
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
    }*/

    private void displayList(){
        eggWiseDatabse = EggWiseDatabse.getInstance(EggBatchListActivity.this);
        new EggBatchListActivity.RetrieveTask(this).execute();
    }

    public static Double zeroIfNull(Double valueIn) {
        if (valueIn == null) {
            return 0.0D;
        } else {
            return valueIn;
        }
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggBatch>> {

        private WeakReference<EggBatchListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(EggBatchListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<EggBatch> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().eggWiseDatabse.getEggBatchDao().getAllEggBatch();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<EggBatch> eggBatch) {
            if (eggBatch!=null && eggBatch.size()>0 ){

                activityReference.get().eggBatchList.clear();
                activityReference.get().eggBatchList.addAll(eggBatch);
                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().eggBatchAdapter.notifyDataSetChanged();
            }
        }

        private void computeAveragesAndPercents() {

            List<EggDaily> eggDailyListPostEx = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(EGG_BATCH_ID);

            Integer index = 0;
            EGG_WEIGHT_SUM = 0.0D;
            READING_DAY_NUMBER = 0;
            EGG_LABEL = "";
            for (index = 0; index < eggDailyListPostEx.size(); index++) {
                READING_DAY_NUMBER = eggDailyListPostEx.get(index).getReadingDayNumber();
                EGG_LABEL = eggDailyListPostEx.get(index).getEggLabel();
                if(TRACKING_OPTION == 1) { //Track entire batch
                    EGG_WEIGHT_SUM = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                    EGG_WEIGHT_AVG_CURRENT = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                    EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Batch_Tracking(EGG_BATCH_ID));
                } else { //Single egg tracking
                    EGG_WEIGHT_SUM =  zeroIfNull(eggDailyListPostEx.get(index).getEggWeight());
                    EGG_WEIGHT_AVG_CURRENT = zeroIfNull(eggDailyListPostEx.get(index).getEggWeight()/1D);
                    if (READING_DAY_NUMBER == 0) {
                        EGG_WEIGHT_AVG_DAY_0 = EGG_WEIGHT_AVG_CURRENT;
                    }
                }

                eggDailyListPostEx.get(index).setEggWeightSum(EGG_WEIGHT_SUM);
                eggDailyListPostEx.get(index).setEggWeightAverageCurrent(EGG_WEIGHT_AVG_CURRENT);
                eggDailyListPostEx.get(index).setEggWeightAverageDay0(EGG_WEIGHT_AVG_DAY_0);

                ACTUAL_WEIGHT_LOSS_PERCENT = 100 * ((EGG_WEIGHT_AVG_DAY_0 - EGG_WEIGHT_AVG_CURRENT) / EGG_WEIGHT_AVG_DAY_0);

                READING_DAY_NUMBER = (eggDailyListPostEx.get(index).getReadingDayNumber());
                TARGET_WEIGHT_LOSS_INTEGER = eggDailyListPostEx.get(index).getTargetWeightLossInteger();
                INCUBATION_DAYS = eggDailyListPostEx.get(index).getIncubationDays();
                Double targetWeightLossDouble = Double.valueOf(TARGET_WEIGHT_LOSS_INTEGER);
                Double readingDayNumberDouble = Double.valueOf(READING_DAY_NUMBER);
                Double incubationDaysDouble = Double.valueOf(INCUBATION_DAYS);
                TARGET_WEIGHT_LOSS_PERCENT = ((targetWeightLossDouble * readingDayNumberDouble) / incubationDaysDouble);

                WEIGHT_LOSS_DEVIATION = TARGET_WEIGHT_LOSS_PERCENT - ACTUAL_WEIGHT_LOSS_PERCENT;

                eggDailyListPostEx.get(index).setActualWeightLossPercent(Common.round(ACTUAL_WEIGHT_LOSS_PERCENT,1));
                eggDailyListPostEx.get(index).setTargetWeightLossPercent(Common.round(TARGET_WEIGHT_LOSS_PERCENT,1));
                eggDailyListPostEx.get(index).setWeightLossDeviation(Common.round(WEIGHT_LOSS_DEVIATION, 2));

                //activityReference.get().eggWiseDatabse.getEggDailyDao().updateEggDaily((eggDaily));

                /*if (WEIGHT_LOSS_DEVIATION != null) {

                    if (Math.abs(ACTUAL_WEIGHT_LOSS_PERCENT) >TARGET_WEIGHT_LOSS_PERCENT) {
                        String message = "*** Warning: Actual Weight deviates beyond Target Weight by "
                                + WEIGHT_LOSS_DEVIATION + " percent. ***";
                        EggWeightLossAdapter.WEIGHT_LOSS_DEVIATION_MESSAGE = message;

                    }
                }*/
            }
        }
    }

    private void initializeViews(){
        toolbar_egg_batch_list = (Toolbar) findViewById(R.id.toolbar_egg_batch_list);
        setSupportActionBar(toolbar_egg_batch_list);
        textViewMsg =  (TextView) findViewById(R.id.tv_empty_egg_batches1);
        FloatingActionButton fabEggBatchList = (FloatingActionButton) findViewById(R.id.fab_egg_batch_list);
        fabEggBatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivityForResult(new Intent(EggBatchListActivity.this, AddEggBatchActivity.class),100);
            }
        });

        recyclerViewEggBatchList = findViewById(R.id.recycler_view_egg_batch_list);
        recyclerViewEggBatchList.setLayoutManager(new LinearLayoutManager(EggBatchListActivity.this));
        eggBatchList = new ArrayList<EggBatch>();
        eggBatchAdapter = new EggBatchAdapter(eggBatchList,  EggBatchListActivity.this);
        recyclerViewEggBatchList.setAdapter(eggBatchAdapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                eggBatchList.add((EggBatch) data.getSerializableExtra("eggBatchList"));
            } else if (resultCode == 2) {
                eggBatchList.set(pos, (EggBatch) data.getSerializableExtra("eggBatchList"));
            }
            listVisibility();
        } else if (requestCode == 200) {
            Intent intent1 = new Intent(EggBatchListActivity.this, WeightLossListActivity.class);
            intent1.putExtra("eggBatchList", eggBatchList.get(pos));
            startActivity(intent1);
            //finish();

        }
    }

    @Override
    public void onEggBatchClick(final int pos) {
        new AlertDialog.Builder(EggBatchListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update", "Enter Weight Loss", "List Weight Loss", "Weight Loss Chart"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                eggWiseDatabse.getEggBatchDao().deleteEggBatch(eggBatchList.get(pos));
                                eggBatchList.remove(pos);
                                listVisibility();
                                break;
                            case 1:
                                EggBatchListActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(EggBatchListActivity.this,
                                                AddEggBatchActivity.class).putExtra("UpdateEggBatch", eggBatchList.get(pos)),
                                        100);
                                break;
                            case 2:
                                EggBatchListActivity.this.pos = pos;
                                //WeightLossListActivity.BATCH_LABEL = eggBatchList.get(pos).getBatchLabel();
                                Intent intent1 = new Intent(EggBatchListActivity.this, AddWeightLossActivity.class);
                                intent1.putExtra("eggBatch", eggBatchList.get(pos));
                                startActivity(intent1);
                                finish();
                                break;
                            case 3:
                                EggBatchListActivity.this.pos = pos;
                                Intent intent2 = new Intent(EggBatchListActivity.this, WeightLossListActivity.class);
                                intent2.putExtra("eggBatch",eggBatchList.get(pos));
                                startActivity(intent2);
                                finish();
                                break;
                            case 4:
                                EggBatchListActivity.this.pos = pos;
                                Intent intent3 = new Intent(EggBatchListActivity.this, WeightLossChartActivity.class);
                                intent3.putExtra("eggBatch",eggBatchList.get(pos));
                                startActivity(intent3);
                                finish();
                                break;


                        }
                    }
                }).show();

    }

    private void listVisibility() {
        int emptyMsgVisibility = View.GONE;
        if (eggBatchList != null) {
            if (eggBatchList.size() == 0) { // no item to display
                if (textViewMsg.getVisibility() == View.GONE)
                    emptyMsgVisibility = View.VISIBLE;
            }
            textViewMsg.setVisibility(emptyMsgVisibility);
            eggBatchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }
}
