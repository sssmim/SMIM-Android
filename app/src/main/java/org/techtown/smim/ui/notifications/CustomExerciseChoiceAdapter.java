package org.techtown.smim.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.GroupList;
import org.techtown.smim.ui.dashboard.GroupListAdapter;

import java.util.ArrayList;

public class CustomExerciseChoiceAdapter extends RecyclerView.Adapter<CustomExerciseChoiceAdapter.ItemViewHolder> implements ItemTouchHelperListener {
    ArrayList<CustomExerciseChoice> items = new ArrayList<>();
    public CustomExerciseChoiceAdapter(){
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줌
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_exercise_choice_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어준다.
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() { return items.size(); }
    public void clearItem() {
        items.clear();
    }
    public void addItem(CustomExerciseChoice person){ items.add(person); }
    public CustomExerciseChoice getItem(int position) {
        return items.get(position);
    }
    public void setItem(int position, CustomExerciseChoice item) {
        items.set(position, item);
    }
    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //이동할 객체 저장
        CustomExerciseChoice person = items.get(from_position);
        //이동할 객체 삭제
        items.remove(from_position);
        //이동하고 싶은 position에 추가
        items.add(to_position,person);

        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        int plusforced=0;
        TextView list_name,list_part,tv_count;
        ImageView list_image;
        Button add;
        Button mis;
        CustomExerciseChoice x=null;
        public ItemViewHolder(View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_part = itemView.findViewById(R.id.list_part);
            list_image = itemView.findViewById(R.id.list_image);
            tv_count = itemView.findViewById(R.id.tv_count);
            add= itemView.findViewById(R.id.btn_add);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Integer a = person.cgetCount();
                   // tv_count.setText(a.toString());
                    int position = getAdapterPosition();
                   CustomExerciseMergeFragment.addmethod(x, position);
                    plusforced++;
                }
            });
            mis= itemView.findViewById(R.id.btn_minus);
            mis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(plusforced>0) {
                        //Toast.makeText(itemView.getContext(), "value", Toast.LENGTH_LONG).show();
                        int position = getAdapterPosition();
                        CustomExerciseMergeFragment.minusmethod(x, position);

                    }
                }
            });

        }

        public void onBind(CustomExerciseChoice person) {
            list_name.setText(person.cgetName());
            list_part.setText(String.valueOf(person.cgetPart()));
            list_image.setImageResource(person.cgetImage());
            Integer a = person.cgetCount();
            tv_count.setText(a.toString());
            this.x=person;

        }
    }
}