package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.EggDaily;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

public class EggWeightAdapter extends RecyclerView.Adapter <EggWeightAdapter.BeanHolder>{

    private List<EggDaily> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnEggWeightItemClick onEggWeightItemClick;


    public EggWeightAdapter(List<EggDaily> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onEggWeightItemClick = (EggWeightAdapter.OnEggWeightItemClick) context;
    }

    @Override
    public BeanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_egg_weight,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BeanHolder holder, int position) {

        Log.e("bind", "onBindViewHolder: "+ list.get(position));

        String line1 = "";
        line1 = "<B>Batch Label:</B> " + list.get(position).getBatchLabel() +
                ", <B>Eggs Remaining:</B> " + list.get(position).getNumberOfEggsRemaining() +
                ", <B>Set Date:</B> " + list.get(position).getSetDate() +
                ", <B>Incubator Name:</B> " + list.get(position).getIncubatorName() +
                ", <B>Egg Label:</B> " + list.get(position).getEggLabel() +
                ", <B>Egg Weight:</B> " + list.get(position).getEggWeight() +
                ", <B>Reading Date:</B> " + list.get(position).getReadingDate() +
                ", <B>Reading Day Number Weight:</B> " + list.get(position).getReadingDayNumber().toString() +
                ", <B>Comment:</B> " + list.get(position).getEggDailyComment()
        ;

        CharSequence styledText = HtmlCompat.fromHtml(line1, HtmlCompat.FROM_HTML_MODE_LEGACY);
        holder.tv_egg_weight_line1.setText(styledText);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_egg_weight_line1;
        CardView cv_egg_weight;


        public BeanHolder(@NonNull View itemView) {
            super(itemView);

            cv_egg_weight = itemView.findViewById(R.id.cv_egg_weight);
            tv_egg_weight_line1  = itemView.findViewById(R.id.tv_egg_weight_line1);
        }

        @Override
        public void onClick(View v) {
            onEggWeightItemClick.onEggWeightClick(getAdapterPosition());

        }
    }

    public interface OnEggWeightItemClick{
        void onEggWeightClick(int pos);
    }
}
