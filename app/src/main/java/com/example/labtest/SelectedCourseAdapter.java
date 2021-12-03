package com.example.labtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class SelectedCourseAdapter extends RecyclerView.Adapter<SelectedCourseAdapter.ViewHolder> {

    private final ArrayList<Course> courses;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvCost;
        private final TextView tvDuration;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvCost = view.findViewById(R.id.tv_cost);
            tvDuration = view.findViewById(R.id.tv_duration);
        }
    }

    public SelectedCourseAdapter(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_selected_course, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Course course = courses.get(position);
        viewHolder.tvName.setText(course.getName());
        viewHolder.tvCost.setText(String.format(Locale.getDefault(),
                "Course Fee: $%d", course.getFee()));
        viewHolder.tvDuration.setText(String.format(Locale.getDefault(),
                "Course duration %d hours", course.getDuration()));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}