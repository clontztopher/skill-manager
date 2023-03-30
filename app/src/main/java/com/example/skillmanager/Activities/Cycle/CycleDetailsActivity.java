package com.example.skillmanager.Activities.Cycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.skillmanager.Activities.Assignment.AddAssignmentActivity;
import com.example.skillmanager.Activities.ViewModels.CycleViewModel;
import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.MenteeWithAssignments;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Cycle Details
 *
 * 1. Accepts cycleId extra for retrieving cycle data
 * 2. Has one nav menu item for edit that goes to CycleEdit and passes cycleId extra
 * 3. Grabs cycle data and list of assignments associated with cycle for display
 * 4. Has recycler view for assignments and alternate view if no assignments
 */
public class CycleDetailsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener, TextWatcher {
    public static final String EXTRA_CYCLE_ID = "cycleId";
    private Cycle mCycle;
    private List<MenteeWithAssignments> mMentees;
    private TextView cycleDisplayView;
    private TextView startDateView;
    private TextView endDateView;
    private ScrollView menteeListScrollView;
    private TextView menteeSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_details);

        addNavigation();

        cycleDisplayView = findViewById(R.id.cycleDisplayName);
        startDateView = findViewById(R.id.cycleStartView);
        endDateView = findViewById(R.id.cycleEndDateView);
        menteeListScrollView = findViewById(R.id.menteeListScrollView);
        menteeSearchView = findViewById(R.id.menteeSearch);

        long cycleId = getIntent().getLongExtra(EXTRA_CYCLE_ID, -1);
        CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
        cycleViewModel.getCycle(cycleId).observe(this, cycle -> {
            mCycle = cycle;
            renderCycleData();
        });

        cycleViewModel.getMenteesForCycle(cycleId).observe(this, menteeWithAssignments -> {
            mMentees = menteeWithAssignments;
            renderMenteeList();
        });

        menteeSearchView.addTextChangedListener(this);
    }

    private void addNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cycle Details");
        setSupportActionBar(toolbar);

        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cycle_details_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(this, CycleEditActivity.class);
            intent.putExtra(CycleEditActivity.ARG_CYCLE_ID, mCycle.getCycleId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void renderCycleData() {
        cycleDisplayView.setText(mCycle.getDisplayName());
        String startDateStr = mCycle.getStartDateString();
        String endDateStr = mCycle.getEndDateString();
        startDateView.setText(startDateStr);
        endDateView.setText(endDateStr);
    }

    private void renderMenteeList() {
        LinearLayout menteeListContainer = menteeListScrollView.findViewById(R.id.menteeListContainer);
        menteeListContainer.removeAllViews();
        String searchParams = menteeSearchView.getText().toString();
        List<MenteeWithAssignments> mentees = searchParams.equals("")
                ? mMentees
                : mMentees.stream()
                    .filter(menteeWithAssignments -> menteeWithAssignments.getMentee().getName().toLowerCase().contains(searchParams.toLowerCase()))
                    .collect(Collectors.toList());
        for (MenteeWithAssignments mentee: mentees) {
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout menteeItem = (LinearLayout) inflater.inflate(R.layout.list_item_cycle_mentee, null);
            TextView nameView = menteeItem.findViewById(R.id.cycleMenteeName);
            nameView.setText(mentee.getMentee().getName());
            Button sendAssignmentsBtn = menteeItem.findViewById(R.id.sendAssignmentsBtn);
            sendAssignmentsBtn.setTag(mentee.getMentee().getMenteeId());
            sendAssignmentsBtn.setOnClickListener(this);

            LinearLayout assignmentContainer = menteeItem.findViewById(R.id.menteeAssignmentContainer);
            for (Assignment assignment: mentee.getAssignments()) {
                LayoutInflater assignmentInflater = LayoutInflater.from(this);
                LinearLayout assignmentView = (LinearLayout) assignmentInflater.inflate(R.layout.list_item_assignment_nested, null);
                long[] tag = {mCycle.getCycleId(), mentee.getMentee().getMenteeId(), assignment.getAssignmentId()};
                assignmentView.setTag(tag);
                assignmentView.setOnClickListener(this);
                TextView titleView = assignmentView.findViewById(R.id.assignmentTitleColumnNested);
                titleView.setText(assignment.getTitle());
                Button removeAssignment = assignmentView.findViewById(R.id.removeAssignment);
                removeAssignment.setTag(new long[]{mentee.getMentee().getMenteeId(), assignment.getAssignmentId()});
                removeAssignment.setOnClickListener(this);
                assignmentContainer.addView(assignmentView);
            }

            menteeListContainer.addView(menteeItem);
        }
        menteeListScrollView.requestLayout();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCycle = null;
        mMentees = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.removeAssignment) {
            long[] tag = (long[]) view.getTag();
            CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
            cycleViewModel.removeAssignment(mCycle.getCycleId(), tag[0], tag[1]);
        }
        if (view.getId() == R.id.assignmentListViewNested) {
            Intent intent = new Intent(this, AddAssignmentActivity.class);
            long[] tag = (long[]) view.getTag();
            intent.putExtra(AddAssignmentActivity.EXTRA_CYCLE_ID, tag[0]);
            intent.putExtra(AddAssignmentActivity.EXTRA_MENTEE_ID, tag[1]);
            intent.putExtra(AddAssignmentActivity.EXTRA_ASSIGNMENT_ID, tag[2]);
            startActivity(intent);
        }
        if (view.getId() == R.id.sendAssignmentsBtn) {
            Optional<MenteeWithAssignments> menteeWithAssignmentsOptional = mMentees.stream().filter(mentee -> mentee.getMentee().getMenteeId() == (long) view.getTag()).findFirst();
            if (!menteeWithAssignmentsOptional.isPresent()) {
                return;
            }
            MenteeWithAssignments menteeWithAssignments = menteeWithAssignmentsOptional.get();
            Mentee mentee = menteeWithAssignments.getMentee();
            List<Assignment> assignments = menteeWithAssignments.getAssignments();

            CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
            cycleViewModel.updateEmailSent(mCycle.getCycleId(), mentee.getMenteeId());

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, mentee.getEmail());
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Assignments: " + mCycle.getDisplayName());
            StringBuilder body = new StringBuilder(String.format("Hello %s,\r\nHere are assignments for cycle, %s.\r\n", mentee.getName(), mCycle.getDisplayName()));
            int count = 0;
            for (Assignment assignment : assignments) {
                body.append(String.format(Locale.getDefault(), "Assignment %d: %s\r\n", ++count, assignment.getTitle()));
                body.append(String.format(Locale.getDefault(), "Topic: %s\r\n", assignment.getTopic()));
                if (assignment.getType() == Assignment.AssignmentType.PROJECT) {
                    body.append(assignment.getProject().getRequirements() + "\r\n");
                } else {
                    body.append(String.format(Locale.getDefault(), "<a title=\"Reference\" href=\"%1$s\">%1$s</a>\r\n", assignment.getStudy().getReference()));
                    body.append(assignment.getStudy().getStudyQuestions() + "\r\n");
                }
            }

            emailIntent.putExtra(Intent.EXTRA_TEXT, body.toString());
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                Intent chooser = Intent.createChooser(emailIntent, "Email Assignments");
                startActivity(chooser);
            }
        }
    }

    public void handleAddAssignment(View view) {
        Intent intent = new Intent(this, AddAssignmentActivity.class);
        intent.putExtra(AddAssignmentActivity.EXTRA_CYCLE_ID, mCycle.getCycleId());
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Empty
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        renderMenteeList();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Empty
    }
}