package org.techtown.smim.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;



import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.Exercise;
import org.techtown.smim.ui.dashboard.ExerciseAdapter;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public static final int customexerciseplan = 1;

    private List<CustomExercise> mExerciseList;
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CustomExerciseAdapter adapters = new CustomExerciseAdapter();

        adapters.addItem(new CustomExercise("푸시업", "가슴", R.drawable.push_up));
        adapters.addItem(new CustomExercise("풀업", "등", R.drawable.pull_up));
        adapters.addItem(new CustomExercise("플랭크", "복부", R.drawable.plank));
        adapters.addItem(new CustomExercise("덤벨 숄더 프레스", "어깨", R.drawable.dumbbell_shoulder_press));
        adapters.addItem(new CustomExercise("덤벨 레터럴 레이즈", "어깨", R.drawable.dumbbell_lateral_raise));
        adapters.addItem(new CustomExercise("벤트오버 덤벨 레터럴 레이즈", "어깨", R.drawable.dumbbell_bentover_lateral_raise));
        adapters.addItem(new CustomExercise("덤벨 컬", "팔(이두)", R.drawable.dumbbell_curl));
        adapters.addItem(new CustomExercise("덤벨 삼두 익스텐션", "팔(삼두)", R.drawable.triceps_dumbbell_extension));
        adapters.addItem(new CustomExercise("에어 스쿼트", "하체", R.drawable.squat));
        adapters.addItem(new CustomExercise("덤벨 런지", "하체", R.drawable.dumbbell_lunge));

        recyclerView.setAdapter(adapters);

        Button imageButton = root.findViewById(R.id.customplan);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), CustomExerciseMerge.class);
                startActivityForResult(intent, customexerciseplan);
            }
        });

        return root;
    }
}