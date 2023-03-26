package com.example.skillmanager.Activities.Assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Project;
import com.example.skillmanager.Data.Entities.Study;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

public class AssignmentDetailActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public static final String EXTRA_ASSIGNMENT_ID = "assignmentId";

    private Assignment mAssignment;

    TextView titleView;
    TextView topicView;
    TextView typeView;
    TextView requirementsView;
    TextView feedbackView;
    TextView referenceView;
    TextView studyQuestionsView;
    LinearLayout projectViewGroup;
    LinearLayout studyViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_detail);

        addToolbar();

        Intent intent = getIntent();
        long assignmentId = intent.getLongExtra(EXTRA_ASSIGNMENT_ID, -1);

        titleView = findViewById(R.id.assignmentDisplayName);
        topicView = findViewById(R.id.topicTextView);
        typeView = findViewById(R.id.typeTextView);
        projectViewGroup = findViewById(R.id.projectViewGroup);
        studyViewGroup = findViewById(R.id.studyViewGroup);

        AssignmentViewModel assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        assignmentViewModel.findById(assignmentId).observe(this, assignment -> {
            mAssignment = assignment;
            render();
        });
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Assignment");
        setSupportActionBar(toolbar);

        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(this, AssignmentEditActivity.class);
            intent.putExtra(AssignmentEditActivity.EXTRA_ASSIGNMENT_ID, mAssignment.assignmentId);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
    }

    public void render() {
        titleView.setText(mAssignment.getTitle());
        topicView.setText(mAssignment.getTopic());

        if (mAssignment.getType() == Assignment.AssignmentType.PROJECT) {
            Project project = mAssignment.getProject();
            requirementsView = projectViewGroup.findViewById(R.id.requirementsTextView);
            requirementsView.setText(project.getRequirements());
        } else {
            projectViewGroup.setVisibility(View.GONE);
        }

        if (mAssignment.getType() == Assignment.AssignmentType.STUDY) {
            Study study = mAssignment.getStudy();
            referenceView = studyViewGroup.findViewById(R.id.referenceTextView);
            referenceView.setText(study.getReference());
            studyQuestionsView = studyViewGroup.findViewById(R.id.studyQuestionsTextView);
            studyQuestionsView.setText(study.getStudyQuestions());
        } else {
            studyViewGroup.setVisibility(View.GONE);
        }
    }



//    public void handleShareNotes(View view) {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Notes for " + mAssignment.getTitle());
//        intent.putExtra(Intent.EXTRA_TEXT, mAssignment.getNotes());
//
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            Intent chooser = Intent.createChooser(intent, "Share notes");
//            startActivity(chooser);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAssignment = null;
    }
}