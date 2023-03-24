package com.example.skillmanager.Activities.Assessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.skillmanager.Data.Entities.Assessment;
import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssessmentRepository;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.concurrent.Future;

public class AssessmentDetailsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public static final String EXTRA_ASSESSMENT_ID = "assessmentId";
    private Assessment mAssessment;
    private Assignment mAssignment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(this, AssessmentEditActivity.class);
            intent.putExtra(AssessmentEditActivity.EXTRA_ASSESSMENT_ID, mAssessment.getAssessmentId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        long assessmentId = getIntent().getLongExtra(EXTRA_ASSESSMENT_ID, -1);

        attachAssessmentData(assessmentId);
        attachAssignmentData(mAssessment.getAssocAssignmentId());
        addToolbar();
        render();
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Assignment: " + mAssignment.getTitle());
        setSupportActionBar(toolbar);

        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }

    private void attachAssessmentData(long assessmentId) {
        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
        Future<Assessment> assessmentFuture = assessmentRepo.findAssessmentById(assessmentId);
        try {
            mAssessment = assessmentFuture.get();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void attachAssignmentData(long assignmentId) {
//        AssignmentRepository assignmentRepo = new AssignmentRepository(getApplication());
//        Future<Assignment> assignmentFuture = assignmentRepo.findAssignmentById(assignmentId);
//        try {
//            mAssignment = assignmentFuture.get();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }

    private void render() {
        TextView assessmentDisplayView = findViewById(R.id.assessmentDisplayView);
        TextView assessmentStartDateView = findViewById(R.id.assessmentStartDateView);
        TextView assessmentEndDateView = findViewById(R.id.assessmentEndDateView);
        TextView assessmentTypeView = findViewById(R.id.assessmentTypeView);

        assessmentDisplayView.setText(mAssessment.getAssessmentTitle());
        String startDateStr = mAssessment.getStartDate();
        String endDateStr = mAssessment.getEndDate();
        assessmentStartDateView.setText(startDateStr);
        assessmentEndDateView.setText(endDateStr);
        assessmentTypeView.setText(mAssessment.getType());
    }

    @Override
    public void onResume() {
        super.onResume();
        attachAssessmentData(mAssessment.getAssessmentId());
        render();
    }
}