package org.techtown.smim.ui.notifications;

import android.view.View;

import org.techtown.smim.ui.dashboard.ExerciseAdapter;

public interface OnExerciseClickListener {
    public void onItemClick(CustomExerciseAdapter.ViewHolder holder, View view, int position);
}