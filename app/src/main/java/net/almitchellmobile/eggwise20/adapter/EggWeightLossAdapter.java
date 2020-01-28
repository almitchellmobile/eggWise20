package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.EggDaily;

import java.util.HashMap;
import java.util.List;
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

    //public String previousKey;

    public Map<String, Double> eggWeightDaySumsMap = new HashMap<String, Double>();
    public Map<String, String> eggWeightAveragesMap = new HashMap<String, String>();
    public Map<String, String> actualWeightLossPercentsMap = new HashMap<String, String>();
    public Map<String, String> targetWeightLossPercentMap = new HashMap<String, String>();
    public Map<String, String> weightPercentDeviationAmountMap = new HashMap<String, String>();

    public String key = "";
    public String previousKey = "";


    public EggWeightLossAdapter(List<EggDaily> eggDailyList, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.eggDailyList = eggDailyList;
        this.context = context;
        this.onEggWeightItemClick = (OnEggWeightItemClick) context;
    }

    @Override
    public BeanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_egg_weight,parent,false);
        return new BeanHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BeanHolder holder, int position) {

        Log.e("bind", "onBindViewHolder: "+ eggDailyList.get(position));

        String line1 = "";
        line1 = "<B>Batch Label:</B> " + eggDailyList.get(position).getBatchLabel() +
                ", <B>Reading Day:</B> " + eggDailyList.get(position).getReadingDayNumber() +
                ", <B>Egg Label:</B> " + eggDailyList.get(position).getEggLabel() +
                ", <B>Egg Weight:</B> " + eggDailyList.get(position).getEggWeight() +
                ", <B>Eggs Remaining:</B> " + eggDailyList.get(position).getNumberOfEggsRemaining() +
                ", <B>Set Date:</B> " + eggDailyList.get(position).getSetDate() +
                ", <B>Incubator Name:</B> " + eggDailyList.get(position).getIncubatorName() +
                ", <B>Reading Date:</B> " + eggDailyList.get(position).getReadingDate() +
                ", <B>Comment:</B> " + eggDailyList.get(position).getEggDailyComment() +
                "<br>===<br>" +
                "<B>Egg Weight Sum:</B> " + eggDailyList.get(position).getEggWeightSum().toString() +
                ", <B>Egg Weight Avg:</B> " + eggDailyList.get(position).getEggWeightAverageCurrent().toString() +
                ", <B>Actual Weight Loss %:</B> " + eggDailyList.get(position).getActualWeightLossPercent().toString() +
                ", <B>Target Weight Loss %:</B> " + eggDailyList.get(position).getTargetWeightLossPercent().toString() +
                ", <B>Weight Loss % Deviation:</B> " + eggDailyList.get(position).getWeightLossDeviation().toString();

        CharSequence styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.tv_egg_weight_line1.setText(styledText);

    }

    @Override
    public int getItemCount() {
        return eggDailyList.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_egg_weight_line1;
        CardView cv_egg_weight;

        public BeanHolder(@NonNull View itemView) {
            super(itemView);

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
