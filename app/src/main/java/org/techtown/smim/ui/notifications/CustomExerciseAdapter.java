package org.techtown.smim.ui.notifications;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.Exercise;
import org.techtown.smim.ui.dashboard.ExerciseAdapter;

import java.util.ArrayList;

public class CustomExerciseAdapter extends RecyclerView.Adapter<CustomExerciseAdapter.ViewHolder> implements OnExerciseClickListener {
    ArrayList<CustomExercise> items = new ArrayList<CustomExercise>();

    OnExerciseClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_exercise_list, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CustomExercise item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CustomExercise item) {
        items.add(item);
    }

    public void setItems(ArrayList<CustomExercise> items) {
        this.items = items;
    }

    public CustomExercise getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, CustomExercise item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnExerciseClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view1;
        TextView nametextview;
        TextView partextview;

        ImageView imageView1;

        public ViewHolder(View itemView, final OnExerciseClickListener listener) {
            super(itemView);

            view1 = itemView;
            nametextview = itemView.findViewById(R.id.nametextview);
            partextview = itemView.findViewById(R.id.partextview);

            imageView1 = itemView.findViewById(R.id.imageView1);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

        }
        //setItem 부분
        public void setItem(CustomExercise item) {
            nametextview.setText(String.valueOf(item.igetName()));
            partextview.setText(item.igetPart());
            imageView1.setImageResource(item.igetImageRes());
        }

    }
}