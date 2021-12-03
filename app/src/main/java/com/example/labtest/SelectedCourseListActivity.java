package com.example.labtest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectedCourseListActivity extends AppCompatActivity {
    private RecyclerView rvCourses;
    private ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_course_list);

        handleToolBar();
        getDataFromIntent();
        initViews();
        configViews();
    }

    private void handleToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.selected_courses);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void getDataFromIntent() {
        courses =
                (ArrayList<Course>) getIntent().getBundleExtra("EXTRA").
                        getSerializable("SELECTED_COURSES");
    }

    private void initViews() {
        rvCourses = findViewById(R.id.rv_courses);
    }

    private void configViews() {
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        rvCourses.setAdapter(new SelectedCourseAdapter(courses));
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}