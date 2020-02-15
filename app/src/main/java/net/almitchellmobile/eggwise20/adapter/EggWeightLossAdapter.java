package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EggWeightLossAdapter extends RecyclerView.Adapter <EggWeightLossAdapter.BeanHolder>  implements Filterable {

    SharedPreferences sharedpreferences;
    public static String PREF_TEMPERATURE_ENTERED_IN = "";
    public static String PREF_HUMIDITY_MEASURED_WITH = "";
    public static String PREF_WEIGHT_ENTERED_IN = "";

    public static Integer PREF_DAYS_TO_HATCHER_BEFORE_HATCHING = 3;
    public static Double PREF_DEFAULT_WEIGHT_LOSS_PRECENTAGE= 0.0D;
    public static Double PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.0D;

    public static final String mypreference = "mypref";

    //private List<Contact> contactList;
    //private List<Contact> contactListFiltered;

    private List<EggDaily> eggDailyList;
    private List<EggDaily> eggDailyListFiltered;

    private EggDaily eggDaily;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggWeightItemClick onEggWeightItemClick;

    private EggWeightLossAdapterListener eggWeightLossAdapter;

    public static Boolean warnAboutDeviation = false;
    public static String warnAboutDeviationValue = "";
    public static Double warningDeviationPercentAmount = 0.0;
    public static Integer numberOfReadings = 0;
    public static String SPECIES_BREED = "";
    public static Integer TAXON_INCUBATION_DAYS = 0;
    public static String WEIGHT_LOSS_DEVIATION_MESSAGE = "";

    public Integer eggsRemaining = -1;
    public Integer numberOfEggs = 0;
    public Integer sumEggsLost = 0;
    public Integer previousReadingDayNumber = 0;
    public Double previousEggWeightAverage = 0.0;
    public Double eggWeightAverage = 0.0;
    public Double eggWeightSum = 0.0;
    public Double actualWeightLossPercent = 0.0;
    public Double targetWeightLossPercent = 0.0;
    public Double computedTargetWeightLossPercent = 0.0;
    public Integer incubationPeriod = 0;
    public Integer readingDayNumber = 0;
    public Double eggWeight = 0.0;
    public Double eggWeightWarningDeviationPercent = 0.0;
    public Double eggWeightPercentDeviationAmount = 0.0;

    String previousTitle = "";
    String currentTitle = "";

    public Map<String, Double> eggWeightDaySumsMap = new HashMap<String, Double>();
    public Map<String, String> eggWeightAveragesMap = new HashMap<String, String>();
    public Map<String, String> actualWeightLossPercentsMap = new HashMap<String, String>();
    public Map<String, String> targetWeightLossPercentMap = new HashMap<String, String>();
    public Map<String, String> weightPercentDeviationAmountMap = new HashMap<String, String>();

    public String key = "";
    public String previousKey = "";


    Common common;


    public EggWeightLossAdapter(List<EggDaily> eggDailyList, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.eggDailyList = eggDailyList;
        this.context = context;
        this.onEggWeightItemClick = (OnEggWeightItemClick) context;
        common = new Common();
        sharedpreferences = context.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains("warn_weight_deviation_percentage")) {
            PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = Double.valueOf(sharedpreferences.getFloat("warn_weight_deviation_percentage", 0.5F));
        } else {
            PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE = 0.5D;
        }
    }

    @Override
    public BeanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_egg_weight,parent,false);
        return new BeanHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BeanHolder holder, int position) {

        String line1 = "";
        try {
            Log.e("bind", "onBindViewHolder: "+ eggDailyList.get(position));
            CharSequence styledTextTitle = "";
            CharSequence styledText = "";


            /*line1 = "<B>Batch Label:</B> " + common.blankIfNullString(eggDailyList.get(position).getBatchLabel()) +
                    ", <B>Reading Day:</B> " + common.zeroIfNullInteger(eggDailyList.get(position).getReadingDayNumber()) +*/

            line1 = "<B>Label:</B> " + common.blankIfNullString(eggDailyList.get(position).getEggLabel()) +
                    ", <B>Weight:</B> " + common.zeroIfNullDouble(eggDailyList.get(position).getEggWeight()) +
                    ", <B># Remaining:</B> " + common.zeroIfNullInteger(eggDailyList.get(position).getNumberOfEggsRemaining()) +
                    ", <B>Set Date:</B> " + common.blankIfNullString(eggDailyList.get(position).getSetDate()) +
                    ", <B>Incubator:</B> " + common.blankIfNullString(eggDailyList.get(position).getIncubatorName()) +
                    ", <B>Reading Date:</B> " + common.blankIfNullString(eggDailyList.get(position).getReadingDate()) +
                    ", <B>Comment:</B> " + common.blankIfNullString(eggDailyList.get(position).getEggDailyComment()) +
                    "<br>***<br>" +
                    //"<B>Weight Sum:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getEggWeightSum()) +
                    "<B>Weight Avg:</B> " + String.format(Locale.getDefault(),"%.2f",eggDailyList.get(position).getEggWeightAverageCurrent()) +
                    ", <B>Actual Loss %:</B> " + String.format(Locale.getDefault(),"%.2f",eggDailyList.get(position).getActualWeightLossPercent()) +
                    ", <B>Target Loss %:</B> " + String.format(Locale.getDefault(),"%.2f",eggDailyList.get(position).getTargetWeightLossPercent()) +
                    ", <B>% Deviation:</B> " + String.format(Locale.getDefault(),"%.2f",Math.abs(eggDailyList.get(position).getWeightLossDeviation()));

            if (eggDailyList.get(position).getWeightLossDeviation() != null) {
                if (eggDailyList.get(position).getWeightLossDeviation() > PREF_WARN_WEIGHT_DEVIATION_PERCENTAGE) {
                    line1 += "<br>***<br>";
                    line1 += "*** Warning: Actual Weight deviates beyond Target Weight by ";
                    line1 += (String.format(Locale.getDefault(),"%.2f",Math.abs(eggDailyList.get(position).getWeightLossDeviation()))) + " percent. ***";
                }
            }


            styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
            holder.tv_egg_weight_line1.setText(styledText);

            currentTitle = "<B>Reading Day: " + common.zeroIfNullInteger(eggDailyList.get(position).getReadingDayNumber())  + "</B>";
            styledTextTitle = HtmlCompat.fromHtml(currentTitle, HtmlCompat.FROM_HTML_MODE_LEGACY);
            holder.tv_egg_weight_title1.setText(styledTextTitle);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return eggDailyList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    eggDailyListFiltered = eggDailyList;
                } else {
                    List<EggDaily> filteredList = new ArrayList<>();
                    for (EggDaily row : eggDailyList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getEggLabel().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    eggDailyListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = eggDailyListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                eggDailyListFiltered = (ArrayList<EggDaily>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Filterable {

        TextView tv_egg_weight_line1;
        TextView tv_egg_weight_title1;
        CardView cv_egg_weight;

        public BeanHolder(@NonNull View itemView) {
            super(itemView);

            tv_egg_weight_title1 = itemView.findViewById(R.id.tv_egg_weight_title1);
            cv_egg_weight = itemView.findViewById(R.id.cv_egg_weight);
            tv_egg_weight_line1  = itemView.findViewById(R.id.tv_egg_weight_line1);
            itemView.setOnClickListener(this);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    eggWeightLossAdapter.on.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            onEggWeightItemClick.onEggWeightClick(getAdapterPosition());}

        @Override
        public Filter getFilter() {
            return null;
        }
    }

    public interface OnEggWeightItemClick{
        void onEggWeightClick(int pos);
    }

    public interface EggWeightLossAdapterListener {
        void EggWeightLossAdapterListener(EggDaily eggDaily);
    }


}
