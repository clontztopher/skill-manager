package com.example.skillmanager.Activities.Assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.skillmanager.Activities.ViewModels.AddAssignmentViewModel;
import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.MenteeWithAssignment;
import com.example.skillmanager.R;

public class AddAssignmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_CYCLE_ID = "cycleId";
    public static final String EXTRA_ASSIGNMENT_ID = "assignmentId";
    public static final String EXTRA_MENTEE_ID = "menteeId";

    private long mCycleId;
    private long mAssignmentId;
    private long mMenteeId;

    private MenteeWithAssignment mMenteeWithAssignment;

    private Spinner menteeSpinner;
    private Spinner assignmentSpinner;
    private Spinner statusSpinner;
    private TextView statusLabel;

    private boolean updatingStatus = false;

    private ArrayAdapter<Mentee> menteeAdapter;
    private ArrayAdapter<Assignment> assignmentAdapter;
    private ArrayAdapter<CharSequence> statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        mCycleId = getIntent().getLongExtra(EXTRA_CYCLE_ID, -1);
        mMenteeId = getIntent().getLongExtra(EXTRA_MENTEE_ID, -1);
        mAssignmentId = getIntent().getLongExtra(EXTRA_ASSIGNMENT_ID, -1);

        menteeSpinner = findViewById(R.id.menteeSpinner);
        menteeSpinner.setOnItemSelectedListener(this);
        menteeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        menteeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menteeSpinner.setAdapter(menteeAdapter);

        assignmentSpinner = findViewById(R.id.assignmentSpinner);
        assignmentSpinner.setOnItemSelectedListener(this);
        assignmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        assignmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignmentSpinner.setAdapter(assignmentAdapter);

        statusLabel = findViewById(R.id.assignmentStatusLabel);
        statusSpinner = findViewById(R.id.assignmentStatusSpinner);
        statusSpinner.setOnItemSelectedListener(this);
        statusAdapter = ArrayAdapter.createFromResource(this, R.array.assignment_proficiency, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        AddAssignmentViewModel addAssignmentViewModel = new ViewModelProvider(this).get(AddAssignmentViewModel.class);
        if (mAssignmentId == -1) {
            addAssignmentViewModel.getMentees().observe(this, mentees -> menteeAdapter.addAll(mentees));
            addAssignmentViewModel.getAssignments().observe(this, assignments -> assignmentAdapter.addAll(assignments));
        } else {
            updatingStatus = true;
            addAssignmentViewModel.getMenteeWithAssignment(mCycleId, mMenteeId, mAssignmentId).observe(this, menteeWithAssignment -> {
                mMenteeWithAssignment = menteeWithAssignment;

                menteeAdapter.add(menteeWithAssignment.getMentee());
                assignmentAdapter.add(menteeWithAssignment.getAssignment());
                menteeSpinner.setEnabled(false);
                menteeSpinner.setClickable(false);
                assignmentSpinner.setEnabled(false);
                assignmentSpinner.setClickable(false);

                statusLabel.setVisibility(View.VISIBLE);
                statusSpinner.setVisibility(View.VISIBLE);
            });
        }

    }

    public void handleAddAssignmentAction(View view) {
        AddAssignmentViewModel addAssignmentViewModel = new ViewModelProvider(this).get(AddAssignmentViewModel.class);
        Mentee chosenMentee = (Mentee) menteeSpinner.getSelectedItem();
        Assignment chosenAssignment = (Assignment) assignmentSpinner.getSelectedItem();
        if (!updatingStatus) {
            addAssignmentViewModel.saveAssignment(mCycleId, chosenMentee.getMenteeId(), chosenAssignment.getAssignmentId());
        } else {
            MenteeAssignmentCrossRef.Status chosenStatus = MenteeAssignmentCrossRef.Status.fromString(statusSpinner.getSelectedItem().toString());
            addAssignmentViewModel.updateAssignment(new MenteeAssignmentCrossRef(mCycleId, mMenteeId, mAssignmentId, chosenStatus));
        }
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Empty
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if (adapterView.getId() == statusSpinner.getId()) {
            adapterView.setSelection(statusAdapter.getPosition(mMenteeWithAssignment.getStatus().toString()));
        }
    }
}