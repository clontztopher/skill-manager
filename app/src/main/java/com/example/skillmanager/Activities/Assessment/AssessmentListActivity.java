package com.example.skillmanager.Activities.Assessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.skillmanager.Data.Entities.Assessment;
import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssessmentRepository;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class AssessmentListActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public static final String EXTRA_COURSE_ID = "assignmentId";
    private List<Assessment> mAssessments = new ArrayList<>();
    private Assignment mAssignment;

    RecyclerView recyclerView;
    TextView noAssessmentsView;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, AssessmentEditActivity.class);
            intent.putExtra(AssessmentEditActivity.EXTRA_COURSE_ID, mAssignment.getAssignmentId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        long assignmentId = getIntent().getLongExtra(EXTRA_COURSE_ID, -1);

        noAssessmentsView = findViewById(R.id.noAssessmentsView);
        recyclerView = findViewById(R.id.assignment_assessment_recycler);

        setUpRecycler();

        attachAssignment(assignmentId);
        addNavigation();

        showHideAssessments();
    }

    private void addNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Assignment: " + mAssignment.getTitle());
        setSupportActionBar(toolbar);

        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }

    private void setUpRecycler() {
        // Set up recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        // Add adapter
        AssessmentAdapter assessmentAdapter = new AssessmentAdapter(mAssessments);
        recyclerView.setAdapter(assessmentAdapter);
    }

    private void attachAssignment(long assignmentId) {
//        AssignmentRepository assignmentRepository = new AssignmentRepository(getApplication());
//        Future<Assignment> assignmentFuture = assignmentRepository.findAssignmentById(assignmentId);
//        try {
//            mAssignment = assignmentFuture.get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private boolean attachAssessmentData(long assignmentId) {
        AssessmentRepository assessmentRepository = new AssessmentRepository(getApplication());
        Future<List<Assessment>> assessmentsFuture;

        assessmentsFuture = assessmentRepository.getAssessmentsForAssignment(assignmentId);

        try {
            List<Assessment> assessments = assessmentsFuture.get();
            mAssessments.clear();
            mAssessments.addAll(assessments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !mAssessments.isEmpty();
    }

    private void showHideAssessments() {
        boolean hasAssessments = attachAssessmentData(mAssignment.getAssignmentId());

        if (!hasAssessments) {
            noAssessmentsView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noAssessmentsView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showHideAssessments();
        setUpRecycler();
    }
}