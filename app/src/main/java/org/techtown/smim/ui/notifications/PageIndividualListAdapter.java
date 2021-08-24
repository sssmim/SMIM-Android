package org.techtown.smim.ui.notifications;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PageIndividualListAdapter extends RecyclerView.Adapter<PageIndividualListAdapter.ViewHolder> {
    ArrayList<PageIndividualList> items = new ArrayList<PageIndividualList>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.individual_info, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        PageIndividualList item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(PageIndividualList item) {
        items.add(item);
    }

    public void setItems(ArrayList<PageIndividualList> items) {
        this.items = items;
    }

    public PageIndividualList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, PageIndividualList item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        ImageView imageView;
        Bitmap bitmap;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.info_title);
            textView2 = itemView.findViewById(R.id.info_desc);
            imageView = itemView.findViewById(R.id.imageView);
            Thread mThread = new Thread(){
              @Override
              public void run(){
                  try{
                      URL url = new URL("");
                      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                      conn.setDoInput(true);
                      conn.connect();

                      InputStream is = conn.getInputStream();
                      bitmap = BitmapFactory.decodeStream(is);

                  }catch (MalformedURLException e){e.printStackTrace();}
                  catch (IOException e){e.printStackTrace();;}
              }
            };
        }

        public void setItem(PageIndividualList item) {
            textView.setText(item.getInfoTitle());
            textView2.setText(item.getInfoDesc());
            imageView.setImageBitmap(bitmap);
        }
    }

}
