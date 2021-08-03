package org.techtown.smim.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    ArrayList<Exercise> items = new ArrayList<Exercise>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.excercise_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Exercise item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Exercise item) {
        items.add(item);
    }

    public void setItems(ArrayList<Exercise> items) {
        this.items = items;
    }

    public Exercise getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Exercise item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Exercise item) {
            textView.setText(item.getGe_start_time().toString());
            textView1.setText(item.getGe_end_time().toString());
            textView2.setText(item.getGe_desc());
        }

    }
}