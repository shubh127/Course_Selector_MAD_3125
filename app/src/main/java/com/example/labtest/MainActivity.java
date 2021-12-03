package com.example.labtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, AdapterView.OnItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private final List<Course> courseList = new ArrayList<>();
    private final List<String> courseNames = new ArrayList<>();
    private TextInputEditText tietName;
    private Spinner spCourses;
    private TextView tvCourseFee;
    private TextView tvCourseDuration;
    private Button btnAddCourse;
    private final ArrayList<Course> finalList = new ArrayList<>();
    private int totalDuration = 0;
    private RadioButton rbUnGraduated;
    private CheckBox cbAccommodation;
    private CheckBox cbMedical;
    private TextView tvTotalFee;
    private TextView tvTotalDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateData();
        initViews();
        configViews();
        applyListeners();
    }

    private void populateData() {
        courseList.add(new Course("Java", 1300, 6));
        courseList.add(new Course("Swift", 1500, 5));
        courseList.add(new Course("iOS", 1350, 5));
        courseList.add(new Course("Android", 1400, 7));
        courseList.add(new Course("Database", 1000, 4));

        for (Course course : courseList) {
            courseNames.add(course.getName());
        }
    }

    private void initViews() {
        tietName = findViewById(R.id.tiet_name);
        spCourses = findViewById(R.id.sp_courses);
        tvCourseFee = findViewById(R.id.tv_course_fees);
        tvCourseDuration = findViewById(R.id.tv_course_duration);
        btnAddCourse = findViewById(R.id.btn_add_course);
        rbUnGraduated = findViewById(R.id.rb_ungraduated);
        cbAccommodation = findViewById(R.id.cb_accommodation);
        cbMedical = findViewById(R.id.cb_medical);
        tvTotalFee = findViewById(R.id.tv_total_fees);
        tvTotalDuration = findViewById(R.id.tv_total_duration);
    }

    private void configViews() {
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courseNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCourses.setAdapter(aa);

        tvCourseFee.setText(String.format(Locale.getDefault(),
                "Course Fee: $%d", courseList.get(0).getFee()));
        tvCourseDuration.setText(String.format(Locale.getDefault(),
                "Course Duration: %d hours", courseList.get(0).getDuration()));
    }

    private void applyListeners() {
        tietName.setOnEditorActionListener(this);
        spCourses.setOnItemSelectedListener(this);
        btnAddCourse.setOnClickListener(this);
        cbMedical.setOnCheckedChangeListener(this);
        cbAccommodation.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            tietName.clearFocus();
            InputMethodManager imm = (InputMethodManager) textView.getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        setCourseData(i);
    }

    private void setCourseData(int index) {
        tvCourseFee.setText(String.format(Locale.getDefault(),
                "Course Fee: $%d", courseList.get(index).getFee()));
        tvCourseDuration.setText(String.format(Locale.getDefault(),
                "Course Duration: %d hours", courseList.get(index).getDuration()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //no op
    }

    @Override
    public void onClick(View view) {
        addCourseToTheFinalList();
    }

    private void addCourseToTheFinalList() {
        int threshHoldDuration = 21;
        if (rbUnGraduated.isChecked()) {
            threshHoldDuration = 19;
        }
        Course selectedCourse = courseList.get(spCourses.getSelectedItemPosition());
        if (finalList.contains(selectedCourse)) {
            Toast.makeText(this,
                    getString(R.string.already_added),
                    Toast.LENGTH_SHORT).show();
        } else if (totalDuration + selectedCourse.getDuration() > threshHoldDuration) {
            Toast.makeText(this,
                    getString(R.string.duration_exceeded),
                    Toast.LENGTH_SHORT).show();
        } else {
            finalList.add(selectedCourse);
            totalDuration = totalDuration + selectedCourse.getDuration();
            Toast.makeText(this,
                    getString(R.string.added_sucessfully),
                    Toast.LENGTH_SHORT).show();
            handleFinalData();
        }
    }

    private void handleFinalData() {
        int totalFee = 0;
        int totalDuration = 0;
        for (Course course : finalList) {
            totalFee = totalFee + course.getFee();
            totalDuration = totalDuration + course.getDuration();
        }
        if (cbAccommodation.isChecked()) {
            totalFee = totalFee + 1000;
        }
        if (cbMedical.isChecked()) {
            totalFee = totalFee + 700;
        }

        tvTotalFee.setText(String.format(Locale.getDefault(), "Total fee: $%d", totalFee));
        tvTotalDuration.setText(String.format(Locale.getDefault(),
                "Total duration: %d hours", totalDuration));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        handleFinalData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_show_selected_courses) {
            openSelectedCourseActivity();
            return true;
        } else if (itemId == R.id.action_exit) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSelectedCourseActivity() {
        Intent intent = new Intent(this, SelectedCourseListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SELECTED_COURSES", finalList);
        intent.putExtra("EXTRA", bundle);
        startActivity(intent);
    }
}