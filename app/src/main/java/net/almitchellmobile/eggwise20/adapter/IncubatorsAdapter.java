package net.almitchellmobile.eggwise20.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.almitchellmobile.eggwise20.R;
import net.almitchellmobile.eggwise20.database.model.Incubator;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class IncubatorsAdapter extends RecyclerView.Adapter<IncubatorsAdapter.BeanHolder>{

    private List<Incubator> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnIncubatorItemClick onIncubatorItemClick;

    public IncubatorsAdapter(List<Incubator> list,Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onIncubatorItemClick = (OnIncubatorItemClick) context;
    }


    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.incubator_list_item,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewIncubatorName.setText(list.get(position).getIncubatorName());
        holder.textViewMFGModel.setText(list.get(position).getMFGModel());
        holder.textViewNumberOfEggs.setText(list.get(position).getNumberOfEggs().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewIncubatorName;
        TextView textViewMFGModel;
        TextView textViewNumberOfEggs;
        CardView cardView;

        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView = itemView.findViewById(R.id.cardView);
            textViewIncubatorName = itemView.findViewById(R.id.tv_incubator_name);
            textViewMFGModel = itemView.findViewById(R.id.tv_mfg_model);
            textViewNumberOfEggs = itemView.findViewById(R.id.tv_number_of_eggs1);
        }

        @Override
        public void onClick(View view) {
            onIncubatorItemClick.onIncubatorClick(getAdapterPosition());
        }
    }

    public interface OnIncubatorItemClick{
        void onIncubatorClick(int pos);
    }
}
