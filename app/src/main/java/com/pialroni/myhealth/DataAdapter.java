package com.pialroni.myhealth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    public List<UserData> userDataList;
    private final Context context;
    private final ListClickListener mListClickListener;

    public DataAdapter(Context context, ListClickListener mListClickListener) {
        this.context = context;
        this.mListClickListener = mListClickListener;
    }

    public void setList(List<UserData> list) {
        this.userDataList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_item, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserData userData = userDataList.get(position);
        holder.date.setText(userData.getDate());
        holder.systolic_value.setText(userData.getSystolic()+"");
        holder.diastolic_value.setText(userData.getDiastolic()+"");
        holder.heart_value.setText(userData.getHeartRate()+"");

        // ---- Systolic ------ //
        if (userData.getSystolic() >= 90 && userData.getSystolic() <= 140) {
            // NORMAL SYSTOLIC
            holder.systolic_text.setTextColor(context.getResources().getColor(R.color.black));
            holder.systolic_value.setTextColor(context.getResources().getColor(R.color.black));

        } else {
            holder.systolic_text.setTextColor(context.getResources().getColor(R.color.deepRed));
            holder.systolic_value.setTextColor(context.getResources().getColor(R.color.deepRed));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lightRed));

        }

        // ---- Diastolic ------ //
        if (userData.getDiastolic() >= 60 && userData.getDiastolic() <= 90) {
            // NORMAL Diastolic
            holder.diastolic_text.setTextColor(context.getResources().getColor(R.color.black));
            holder.diastolic_value.setTextColor(context.getResources().getColor(R.color.black));

        } else {
            holder.diastolic_text.setTextColor(context.getResources().getColor(R.color.deepRed));
            holder.diastolic_value.setTextColor(context.getResources().getColor(R.color.deepRed));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lightRed));
        }

    }

    @Override
    public int getItemCount() {
        if (userDataList == null) return 0;
        return userDataList.size();
    }

    public interface ListClickListener {
        void onListClick(UserData UserData);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView systolic_text, systolic_value;
        private TextView diastolic_text, diastolic_value;
        private TextView heart_text, heart_value;
        private CardView cardView;
        private TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            systolic_text = itemView.findViewById(R.id.Systolic_id_text);
            systolic_value = itemView.findViewById(R.id.Systolic_id_single_data_item);

            diastolic_text = itemView.findViewById(R.id.dystolic_id_text);
            diastolic_value = itemView.findViewById(R.id.dystolic_id_single_data_item);

            heart_text = itemView.findViewById(R.id.heart_id_text);
            heart_value = itemView.findViewById(R.id.heatRate_id_single_data_item);

            date = itemView.findViewById(R.id.date_id_single_data_item);
            cardView = itemView.findViewById(R.id.card_view_single_data_item_id);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mListClickListener.onListClick(userDataList.get(pos));

        }


    }
}
