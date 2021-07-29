package org.techtown.smim.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

import java.util.ArrayList;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {
    ArrayList<Youtube> items = new ArrayList<Youtube>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.youtube_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Youtube item = items.get(position);
        viewHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Youtube item) {
        items.add(item);
    }

    public void setItems(ArrayList<Youtube> items) {
        this.items = items;
    }

    public Youtube getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Youtube item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //TextView textView;
        TextView textView1;


        public ViewHolder(View itemView) {
            super(itemView);

           // textView = itemView.findViewById(R.id.youtubeimage);
            textView1 = itemView.findViewById(R.id.youtubename);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;

                }
            });


        }

        public void setItem(Youtube item) {

            textView1.setText(item.getTitle());

        }

    }
}