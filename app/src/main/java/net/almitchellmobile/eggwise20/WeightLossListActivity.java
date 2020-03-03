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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.almitchellmobile.eggwise20.adapter.EggWeightLossAdapter;
import net.almitchellmobile.eggwise20.database.EggWiseDatabse;
import net.almitchellmobile.eggwise20.database.model.EggBatch;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WeightLossListActivity extends AppCompatActivity implements EggWeightLossAdapter.OnEggWeightItemClick {

    SharedPreferences sharedpreferences;
    /*public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Float PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0F;
    public static Float PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
    public static Integer PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 0;*/

    public static final String mypreference = "mypref";

    public static String ORDER_BY_COLUMNS = " ReadingDayNumber ASC, EggLabel ASC";
    public static Integer ORDER_BY_SELECTION = 0;

    public ArrayList<EggDaily> eggDailyListOnClick = new ArrayList<>();
    public static LinkedHashMap<Integer ,String> sortByLinkedHashMap;
    public static ArrayList<EggDaily> EGG_DAILY_LIST_STATIC = null;
    public static HashMap<Long,EggDaily> HASHMAP_EGG_DAILY_ID_EGG_DAILY = new HashMap<Long,EggDaily>();
    public static ArrayList<EggDaily> eggDailyListFiltered = new ArrayList<>();
    public static Integer indexHashMap = 0;
    public static Integer EGG_DAILY_LIST_POS_STATIC = 0;
    public static String BATCH_LABEL = "";
    public static Long EGG_BATCH_ID = 0L;
    public static Long EGG_DAILY_ID_STATIC = 0L;
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

    private SearchView searchView;
    FloatingActionButton fab_weight_loss;
    private TextView tv_empty_weight_loss_message;
    private RecyclerView recyclerViewWeightLossList;
    private EggWiseDatabse eggWiseDatabse;
    EggBatch eggBatch;
    public ArrayList<EggDaily> eggDailyList = new ArrayList<>();
    EggDaily eggDailyOnClick = null;

    private EggWeightLossAdapter eggWeightLossAdapter;
    private int pos;
    EditText et_weight_loss_filter_label, et_weight_loss_filter_day;
    Chip chip_filter_weight_loss_apply_reset;
    ToggleButton toggle_btn_and_or;
    Boolean toggleButtonState;
    Button btn_filter_weight_loss_apply, btn_filter_weight_loss_reset;
    Spinner spinnerSortBy;

    public static CheckBox check_box_rejected_egg;

    public Long settingID = 0L;
    public EggDaily eggDaily = null;

    public String eggLabel = "";
    public String readingDate = "";
    public String setDate = "";
    public Integer readingDayNumber = 0;
    public Double eggWeight = 0.0D;
    public Double eggWeightAverage = 0.0D;
    public String eggDailyComment = "";
    public String incubatorName = "";
    public Integer numberOfEggsRemaining = 0;
    public Integer numberOfEggs = 0;
    public Integer rejectedEgg = 0;

    Common common;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_list);
        Toolbar toolbar_weight_loss_list = findViewById(R.id.toolbar_weight_loss_list);
        toolbar_weight_loss_list.inflateMenu(R.menu.main);
        setSupportActionBar(toolbar_weight_loss_list);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        Common.PREF_TEMPERATURE_ENTERED_IN = sharedpreferences.getString("temperature_entered_in", "");
        Common.PREF_HUMIDITY_MEASURED_WITH = sharedpreferences.getString("humidity_measured_with", "");
        Common.PREF_WEIGHT_ENTERED_IN = sharedpreferences.getString("weight_entered_in", "");
        if (sharedpreferences.contains("days_to_hatcher_before_hatching")) {
            Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = sharedpreferences.getInt("days_to_hatcher_before_hatching", 3);
        } else {
            Common.PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 0;
        }
        if (sharedpreferences.contains("default_weight_loss_percentage")) {
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = sharedpreferences.getFloat("default_weight_loss_percentage", 13.0F);
            //double data = PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE;
            //int value = (int)data;
            Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = Common.convertDoubleToInteger(Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE);
        } else {
            Common.PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE = 13.0F;
            Common.PREF_DEFAULT_WEIGHT_LOSS_INTEGER = 13;
        }
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F);
        } else {
            Common.PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0F;
        }

        common = new Common();

        if ( (eggBatch = (EggBatch) getIntent().getSerializableExtra("eggBatch"))!=null ){
            BATCH_LABEL = eggBatch.getBatchLabel();
            EGG_BATCH_ID = eggBatch.getEggBatchID();
            TRACKING_OPTION = eggBatch.getTrackingOption();
            getSupportActionBar().setTitle("Weight Loss List Batch: " + BATCH_LABEL);
        } else {
            getSupportActionBar().setTitle("Weight Loss List");
        }

        //check_box_rejected_egg = (CheckBox) findViewById(R.id.check_box_rejected_egg);

        spinnerSortBy = (Spinner) findViewById(R.id.spinnerSortBy);
        List<String> list = new ArrayList<String>();
        list.add("Sort By Reading Day ASC, Egg Label ASC");
        list.add("Sort By Reading Day ASC, Egg Label DESC");
        list.add("Sort By Reading Day DESC, Egg Label ASC");
        list.add("Sort By Reading Day DESC, Egg Label DESC");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortBy.setAdapter(dataAdapter);
        spinnerSortBy.setSelection(0);
        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        //Toast.makeText(parent.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber ASC, EggLabel ASC";
                        ORDER_BY_SELECTION = 0;
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber ASC, EggLabel DESC ";
                        ORDER_BY_SELECTION = 1;
                        break;
                    case 2:
                        //Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber DESC, EggLabel ASC ";
                        ORDER_BY_SELECTION = 2;
                        break;
                    case 3:
                        //Toast.makeText(parent.getContext(), "Spinner item 4!", Toast.LENGTH_SHORT).show();
                        ORDER_BY_COLUMNS = " ReadingDayNumber DESC, EggLabel DESC ";
                        ORDER_BY_SELECTION = 3;
                        break;
                }
                initializeViews();
                displayList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        /*check_box_rejected_egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CompoundButton) view).isChecked()){
                    eggWiseDatabse.getEggDailyDao().updateEggDaily(eggDaily.rejectedEgg);
                } else {

                }
            }
        });*/

        toggle_btn_and_or = (ToggleButton) findViewById(R.id.toggle_btn_and_or);
        toggle_btn_and_or.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    //toggle_btn_and_or.setChecked(false);
                    //toggle_btn_and_or.setTextOff("OR");
                } else {
                    // The toggle is disabled
                    //toggle_btn_and_or.setChecked(true);
                    //toggle_btn_and_or.setTextOff("AND");
                }
            }
        });

        btn_filter_weight_loss_apply = findViewById(R.id.btn_filter_weight_loss_apply);
        btn_filter_weight_loss_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //apply filters
                if (toggle_btn_and_or.isChecked()) {
                    if (et_weight_loss_filter_label.getText().toString().length() > 0 && et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterLabelAndReadingDay(et_weight_loss_filter_label.getText().toString(), et_weight_loss_filter_day.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() > 0 && et_weight_loss_filter_day.getText().toString().length() == 0) {
                        filterLabel(et_weight_loss_filter_label.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() == 0 && et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterDay(et_weight_loss_filter_day.getText().toString());
                    }
                } else {
                    if (et_weight_loss_filter_label.getText().toString().length() > 0 || et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterLabelAndReadingDay(et_weight_loss_filter_label.getText().toString(), et_weight_loss_filter_day.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() > 0 || et_weight_loss_filter_day.getText().toString().length() == 0) {
                        filterLabel(et_weight_loss_filter_label.getText().toString());
                    } else if (et_weight_loss_filter_label.getText().toString().length() == 0 || et_weight_loss_filter_day.getText().toString().length() > 0) {
                        filterDay(et_weight_loss_filter_day.getText().toString());
                    }
                }
                hideSoftKeyboard(WeightLossListActivity.this, v);
            }
        });


        btn_filter_weight_loss_reset = findViewById(R.id.btn_filter_weight_loss_reset);
        btn_filter_weight_loss_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset filters
                resetFilters();
                //eggDailyList = EGG_DAILY_LIST_STATIC;
                //eggWeightLossAdapter.filterEggDailyList(eggDailyList);
                initializeViews();
                displayList();
                //eggWeightLossAdapter.notifyDataSetChanged();
                hideSoftKeyboard(WeightLossListActivity.this, v);
            }
        });


        et_weight_loss_filter_label = (EditText) findViewById(R.id.et_weight_loss_filter_label);
        et_weight_loss_filter_label.setSelectAllOnFocus(true);
        et_weight_loss_filter_label.setFocusable(true);
        /*et_weight_loss_filter_label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                //filterLabel(editable.toString());
                //filter(editable.toString());
            }
        });*/

        et_weight_loss_filter_day = (EditText) findViewById(R.id.et_weight_loss_filter_day);
        et_weight_loss_filter_day.setSelectAllOnFocus(true);
        et_weight_loss_filter_day.setFocusable(true);

        /*et_weight_loss_filter_day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                //filterDay(editable.toString());
                //filter(editable.toString());
            }
        });*/

        initializeViews();
        displayList();
    }

    private void resetFilters() {
        et_weight_loss_filter_label.setText("");
        et_weight_loss_filter_day.setText("");
    }

    private void filterLabelAndReadingDay(String textLabel, String textReadingDay) {
        //new array list that will hold the filtered data
        eggDailyListFiltered = new ArrayList<>();
        indexHashMap = 0;
        for (EggDaily eggDaily : eggDailyList) {
            //if the existing elements contains the search input
            if (toggle_btn_and_or.isChecked()) { // And the filters
                if (eggDaily.getEggLabel().toLowerCase().contains(textLabel.toLowerCase()) &&
                        eggDaily.getReadingDayNumber().toString().toLowerCase().contains(textReadingDay.toLowerCase())) {
                    eggDailyListFiltered.add(eggDaily);
                    HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
                }

            } else {  //Or the filters
                if (eggDaily.getEggLabel().toLowerCase().contains(textLabel.toLowerCase()) ||
                        eggDaily.getReadingDayNumber().toString().toLowerCase().contains(textReadingDay.toLowerCase())) {
                    //adding the element to filtered list
                    eggDailyListFiltered.add(eggDaily);
                    HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
                }
            }
        }

        //calling a method of the adapter class and passing the filtered list
        eggWeightLossAdapter.filterEggDailyList(eggDailyListFiltered);
    }

    private void filterLabel(String text) {
        //new array list that will hold the filtered data
        eggDailyListFiltered = new ArrayList<>();

        //looping through existing elements
        for (EggDaily eggDaily : eggDailyList) {
            //if the existing elements contains the search input
            if (eggDaily.getEggLabel().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                eggDailyListFiltered.add(eggDaily);
                HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        eggWeightLossAdapter.filterEggDailyList(eggDailyListFiltered);
    }

    private void filterDay(String text) {
        //new array list that will hold the filtered data
        eggDailyListFiltered = new ArrayList<>();

        //looping through existing elements
        for (EggDaily eggDaily : eggDailyList) {
            //if the existing elements contains the search input
            if (eggDaily.getReadingDayNumber().toString().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                eggDailyListFiltered.add(eggDaily);
                HASHMAP_EGG_DAILY_ID_EGG_DAILY.put(eggDaily.getEggDailyID(), eggDaily);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        eggWeightLossAdapter.filterEggDailyList(eggDailyListFiltered);
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
                //eggWeightLossAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //eggWeightLossAdapter.getFilter().filter(query);
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

    private void displayList(){
        eggWiseDatabse = EggWiseDatabse.getInstance(WeightLossListActivity.this);
        new WeightLossListActivity.RetrieveTask(this).execute();

    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_empty_weight_loss_message =  (TextView) findViewById(R.id.tv_empty_weight_loss_message);
        fab_weight_loss = (FloatingActionButton) findViewById(R.id.fab_add_weight_loss);
        fab_weight_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivityForResult(new Intent(WeightLossListActivity.this,
                        AddWeightLossActivity.class).putExtra("eggDailyAdd", eggDailyList.get(0)),100);
            }
        });



        /*
        check_box_rejected_egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  rejectedEgg = 1;
                  Integer position = EGG_DAILY_LIST_POS_STATIC;
                  //eggWiseDatabse.getEggDailyDao().updateEggDaily__RejectedEgg(rejectedEgg, eggDailyList.);

              }
          }
        );*/

        /*searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

           @Override
            public boolean onQueryTextChange(String newText) {
               CharSequence = newText;
                eggWeightLossAdapter.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(CharSequence text) {
                eggWeightLossAdapter.getFilter().filter(text);
                return false;
            }
        });*/

        recyclerViewWeightLossList = findViewById(R.id.recycler_view_weight_loss_list);
        recyclerViewWeightLossList.setLayoutManager(new LinearLayoutManager(WeightLossListActivity.this));
        eggDailyList = new ArrayList<>();
        eggWeightLossAdapter = new EggWeightLossAdapter(eggDailyList,  WeightLossListActivity.this, eggWiseDatabse);
        recyclerViewWeightLossList.setAdapter(eggWeightLossAdapter);

        //RoomExplorer.show(WeightLossListActivity.this, EggWiseDatabse.class, "EggWiseDB.db");
        //Stetho.initializeWithDefaults(this);
    }

    public static void hideSoftKeyboard (AppCompatActivity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public static Double zeroIfNull(Double valueIn) {
        if (valueIn == null) {
            return 0.0D;
        } else {
            return valueIn;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 100 && resultCode > 0) {
                //eggDailyList =  eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(BATCH_LABEL);
                if (resultCode == 1) {
                    eggDailyList.add((EggDaily) data.getSerializableExtra("eggDailyList"));
                } else if (resultCode == 2) {
                    eggDailyList.set(pos, (EggDaily) data.getSerializableExtra("eggDailyList"));
                    //eggDailyList =  eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(BATCH_LABEL);
                }
                listVisibility();

                //initializeViews();
                //displayList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEggWeightClick(int pos) {


        if (eggDailyListFiltered.size() != 0 && eggDailyListFiltered.size() < eggDailyList.size()) {
            eggDailyListOnClick = eggDailyListFiltered;
        } else {
            eggDailyListOnClick = eggDailyList;
        }

        new AlertDialog.Builder(WeightLossListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update", "Weight Loss Chart", "Reject Egg"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                EGG_DAILY_ID_STATIC = eggDailyListOnClick.get(pos).getEggDailyID();
                                eggDailyOnClick = HASHMAP_EGG_DAILY_ID_EGG_DAILY.get(EGG_DAILY_ID_STATIC);
                                eggWiseDatabse.getEggDailyDao().deleteEggDaily(eggDailyOnClick);
                                eggDailyList.remove(EGG_DAILY_ID_STATIC);
                                listVisibility();
                                break;
                            case 1:

                                EGG_DAILY_ID_STATIC = eggDailyListOnClick.get(pos).getEggDailyID();
                                eggDailyOnClick = HASHMAP_EGG_DAILY_ID_EGG_DAILY.get(EGG_DAILY_ID_STATIC);
                                BATCH_LABEL = eggDailyListOnClick.get(pos).getBatchLabel();
                                startActivityForResult(
                                        new Intent(WeightLossListActivity.this,
                                                AddWeightLossActivity.class).putExtra("eggDailyUpdate", eggDailyOnClick),
                                        100);

                                break;
                            case 2:
                                //EGG_DAILY_ID_STATIC = EGG_DAILY_LIST_STATIC.get(pos).getEggDailyID();
                                //EGG_DAILY_LIST_POS_STATIC = HASHMAP_EGG_DAILY_ID_EGG_DAILY_LIST_POS.get(EGG_DAILY_ID_STATIC);;
                                //WeightLossListActivity.this.EGG_DAILY_LIST_POS_STATIC = EGG_DAILY_LIST_POS_STATIC;
                                eggDailyOnClick = HASHMAP_EGG_DAILY_ID_EGG_DAILY.get(EGG_DAILY_ID_STATIC);
                                WeightLossChartActivity.BATCH_LABEL = eggDailyList.get(EGG_DAILY_LIST_POS_STATIC).batchLabel;
                                WeightLossChartActivity.RETURN_ACTIVITY = "WeightLossListActivity";
                                startActivity(
                                        new Intent(WeightLossListActivity.this,
                                                WeightLossChartActivity.class).putExtra("eggBatch", eggBatch)
                                                .putExtra("eggDailyChart", eggDailyOnClick));
                                finish();
                                break;
                            case 3:
                                //EGG_DAILY_ID_STATIC = eggDailyListOnClick.get(pos).getEggDailyID();
                                eggDailyOnClick = eggDailyListOnClick.get(pos);
                                if(eggDailyOnClick.getRejectedEgg() == 0){
                                    //reject this egg
                                    eggDailyOnClick.setRejectedEgg(1);
                                    rejectedEgg = eggDailyOnClick.getRejectedEgg();
                                    eggWiseDatabse.getEggDailyDao().updateEggDaily_RejectedEgg(rejectedEgg,
                                            eggDailyOnClick.getEggBatchID(), eggDailyOnClick.getEggLabel());
                                }else {
                                    //unreject this egg
                                    eggDailyOnClick.setRejectedEgg(0);
                                    rejectedEgg = eggDailyOnClick.getRejectedEgg();
                                    eggWiseDatabse.getEggDailyDao().updateEggDaily_RejectedEgg(rejectedEgg,
                                            eggDailyOnClick.getEggBatchID(), eggDailyOnClick.getEggLabel());
                                }
                                //eggWeightLossAdapter.notifyDataSetChanged();
                                //listVisibility();
                                resetFilters();
                                initializeViews();
                                displayList();
                                break;
                        }
                    }
                }).show();

    }




    private static class RetrieveTask extends AsyncTask<Void,Void,List<EggDaily>> {

        private WeakReference<WeightLossListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(WeightLossListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<EggDaily> doInBackground(Void... voids) {
            List<EggDaily> returnEggDaily = null;
            if (activityReference.get()!=null) {
                //return activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay(EGG_BATCH_ID);
                switch (ORDER_BY_SELECTION) {
                    case 0:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_ASC_LABEL_ASC(EGG_BATCH_ID);
                        break;
                    case 1:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_ASC_Label_DESC(EGG_BATCH_ID);
                        break;
                    case 2:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_DESC_Label_ASC(EGG_BATCH_ID);
                        break;
                    case 3:
                        returnEggDaily = activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_BatchEggDay_Day_DESC_Label_DESC(EGG_BATCH_ID);
                    break;
                }
            } else {
                return returnEggDaily;
            }
            return returnEggDaily;
        }

        @Override
        protected void onPostExecute(List<EggDaily> eggDailyListPostEx) {

            if (eggDailyListPostEx!=null && eggDailyListPostEx.size()>0 ){


                computeAveragesAndPercents(eggDailyListPostEx);

                activityReference.get().eggDailyList.clear();
                activityReference.get().eggDailyList.addAll(eggDailyListPostEx);
                activityReference.get().EGG_DAILY_LIST_STATIC = new ArrayList<>();
                activityReference.get().EGG_DAILY_LIST_STATIC.addAll(eggDailyListPostEx);
                // hides empty text view
                activityReference.get().tv_empty_weight_loss_message.setVisibility(View.GONE);
                activityReference.get().eggWeightLossAdapter.notifyDataSetChanged();
            }
        }

        private void computeAveragesAndPercents(List<EggDaily> eggDailyListPostEx) {
            Integer index = 0;
            EGG_WEIGHT_SUM = 0.0D;
            READING_DAY_NUMBER = 0;
            EGG_LABEL = "";
            for (index = 0; index < eggDailyListPostEx.size(); index++) {

                //HASHMAP_EGG_DAILY_ID_EGG_DAILY_LIST_POS.put(eggDailyListPostEx.get(index).getEggDailyID(), index);

                /*if (eggDailyListPostEx.get(index).getRejectedEgg() == 1) {
                    check_box_rejected_egg.setChecked(true);
                } else {
                    check_box_rejected_egg.setChecked(false);
                }*/

                READING_DAY_NUMBER = eggDailyListPostEx.get(index).getReadingDayNumber();
                EGG_LABEL = eggDailyListPostEx.get(index).getEggLabel();
                if(TRACKING_OPTION == 1) { //Track entire batch
                    EGG_WEIGHT_SUM = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                    EGG_WEIGHT_AVG_CURRENT = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Batch_Tracking(EGG_BATCH_ID, READING_DAY_NUMBER));
                    EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Batch_Tracking(EGG_BATCH_ID));
                } else { //Single egg tracking
                    EGG_WEIGHT_SUM =  zeroIfNull(eggDailyListPostEx.get(index).getEggWeight());
                    EGG_WEIGHT_AVG_CURRENT = zeroIfNull(eggDailyListPostEx.get(index).getEggWeight()/1D);
                    //EGG_WEIGHT_SUM = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeSum_GroupBy_Day_EggLabel_Single_Tracking(BATCH_LABEL, READING_DAY_NUMBER, EGG_LABEL));
                    //EGG_WEIGHT_AVG_CURRENT = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_Compute_Avg_GroupBy_Day_Egglabel_Single_Tracking(BATCH_LABEL, READING_DAY_NUMBER, EGG_LABEL));
                    //EGG_WEIGHT_AVG_DAY_0 = zeroIfNull(activityReference.get().eggWiseDatabse.getEggDailyDao().getEggDaily_ComputeAvg_Day0_Single_Tracking(BATCH_LABEL, EGG_LABEL ));
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

                activityReference.get().eggWiseDatabse.getEggDailyDao().updateEggDaily_Sum_Averages_Percents(EGG_WEIGHT_SUM,
                        EGG_WEIGHT_AVG_CURRENT, EGG_WEIGHT_AVG_DAY_0, ACTUAL_WEIGHT_LOSS_PERCENT, TARGET_WEIGHT_LOSS_PERCENT,
                        WEIGHT_LOSS_DEVIATION, eggDailyListPostEx.get(index).getEggDailyID());

                EggWeightLossAdapter.WEIGHT_LOSS_DEVIATION_MESSAGE = "";
                if (WEIGHT_LOSS_DEVIATION != null) {

                    if (Math.abs(ACTUAL_WEIGHT_LOSS_PERCENT) >TARGET_WEIGHT_LOSS_PERCENT) {
                        String message = "*** Warning: Actual Weight deviates beyond Target Weight by "
                                + WEIGHT_LOSS_DEVIATION + " percent. ***";
                        EggWeightLossAdapter.WEIGHT_LOSS_DEVIATION_MESSAGE = message;

                    }
                }
            }
        }
    }



    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (eggDailyList.size() == 0){ // no item to display
            if (tv_empty_weight_loss_message.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        tv_empty_weight_loss_message.setVisibility(emptyMsgVisibility);
        eggWeightLossAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        eggWiseDatabse.cleanUp();
        super.onDestroy();
    }
}
