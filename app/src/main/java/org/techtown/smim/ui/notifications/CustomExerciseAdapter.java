package org.techtown.smim.ui.notifications;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Color;
import android.util.Log;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

import java.util.ArrayList;

public class CustomExerciseAdapter extends RecyclerView.Adapter<CustomExerciseAdapter.ViewHolder> implements OnExerciseClickListener {
    static ArrayList<CustomExercise> itemList = new ArrayList<CustomExercise>();
    private static final String TAG = "MainCustomExerciseAdapter";

    OnExerciseClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_exercise_list, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Log.d(TAG, "onBindViewHolder: position ▶ " + position);
        CustomExercise item = itemList.get(position);
        holder.setItem(item);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(CustomExercise item) {
        itemList.add(item);
    }

    public void setItems(ArrayList<CustomExercise> items) {
        this.itemList = items;
    }

    public CustomExercise getItem(int position) { return itemList.get(position); }

    public void setItem(int position, CustomExercise item) {
        itemList.set(position, item);
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

        static ImageView imageView1;

        public ViewHolder(@Nullable View itemView, final OnExerciseClickListener listener) {
            super(itemView);

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
            nametextview.setText(item.igetName());
            partextview.setText(item.igetPart());
            imageView1.setImageResource(item.igetImageRes());
        }

    }
}