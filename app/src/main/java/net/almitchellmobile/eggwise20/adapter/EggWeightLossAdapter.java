package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.EggDaily;
import net.almitchellmobile.eggwise20.util.Common;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EggWeightLossAdapter extends RecyclerView.Adapter <EggWeightLossAdapter.BeanHolder>{

    private List<EggDaily> eggDailyList;
    private  EggDaily eggDaily;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggWeightItemClick onEggWeightItemClick;

    public static Boolean warnAboutDeviation = false;
    public static String warnAboutDeviationValue = "";
    public static Double eggWeightWarningDeviationPercentStatic = 0.0;
    public static Integer numberOfReadings = 0;
    public static String SPECIES_BREED = "";
    public static Integer TAXON_INCUBATION_DAYS = 0;

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

            line1 = "<B>Egg Label:</B> " + common.blankIfNullString(eggDailyList.get(position).getEggLabel()) +
                    ", <B>Egg Weight:</B> " + common.zeroIfNullDouble(eggDailyList.get(position).getEggWeight()) +
                    ", <B>Eggs Remaining:</B> " + common.zeroIfNullInteger(eggDailyList.get(position).getNumberOfEggsRemaining()) +
                    ", <B>Set Date:</B> " + common.blankIfNullString(eggDailyList.get(position).getSetDate()) +
                    ", <B>Incubator Name:</B> " + common.blankIfNullString(eggDailyList.get(position).getIncubatorName()) +
                    ", <B>Reading Date:</B> " + common.blankIfNullString(eggDailyList.get(position).getReadingDate()) +
                    ", <B>Comment:</B> " + common.blankIfNullString(eggDailyList.get(position).getEggDailyComment()) +
                    "<br>===<br>" +
                    "<B>Egg Weight Sum:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getEggWeightSum()) +
                    ", <B>Egg Weight Avg:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getEggWeightAverageCurrent()) +
                    ", <B>Actual Weight Loss %:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getActualWeightLossPercent()) +
                    ", <B>Target Weight Loss %:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getTargetWeightLossPercent()) +
                    ", <B>Weight Loss % Deviation:</B> " + String.format(Locale.getDefault(),"%.1f",eggDailyList.get(position).getWeightLossDeviation());

            styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
            holder.tv_egg_weight_line1.setText(styledText);

            currentTitle = "<B>Reading Day: " + common.zeroIfNullInteger(eggDailyList.get(position).getReadingDayNumber())  + "</B>";
            styledTextTitle = HtmlCompat.fromHtml(currentTitle, HtmlCompat.FROM_HTML_MODE_LEGACY);
            holder.tv_egg_weight_title1.setText(styledTextTitle);

            /*if (!(currentTitle.equalsIgnoreCase(previousTitle))) {
                previousTitle = currentTitle;
                holder.tv_egg_weight_title1.setVisibility(View.VISIBLE);
                //holder.tv_egg_weight_line1.setVisibility(View.VISIBLE);
            } else {
                //holder.tv_egg_weight_title.setText(styledTextTitle);
                //holder.tv_egg_weight_title.setVisibility(View.VISIBLE);
                holder.tv_egg_weight_title1.setVisibility(View.GONE);
                //holder.tv_egg_weight_line1.setVisibility(View.VISIBLE);
            }*/
            //holder.tv_egg_weight_line1.setText(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return eggDailyList.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_egg_weight_line1;
        TextView tv_egg_weight_title1;
        CardView cv_egg_weight;

        public BeanHolder(@NonNull View itemView) {
            super(itemView);

            tv_egg_weight_title1 = itemView.findViewById(R.id.tv_egg_weight_title1);
            cv_egg_weight = itemView.findViewById(R.id.cv_egg_weight);
            tv_egg_weight_line1  = itemView.findViewById(R.id.tv_egg_weight_line1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { onEggWeightItemClick.onEggWeightClick(getAdapterPosition());}
    }

    public interface OnEggWeightItemClick{
        void onEggWeightClick(int pos);
    }


}
