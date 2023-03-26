package com.example.skillmanager.Activities.Cycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.skillmanager.Activities.Assignment.AssignmentListActivity;
import com.example.skillmanager.Activities.Mentee.MenteeListActivity;
import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.CycleWithMentees;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.Fragments.AssignmentPickerFragment;
import com.example.skillmanager.Fragments.MenteePickerFragment;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Cycle Details
 *
 * 1. Accepts cycleId extra for retrieving cycle data
 * 2. Has one nav menu item for edit that goes to CycleEdit and passes cycleId extra
 * 3. Grabs cycle data and list of assignments associated with cycle for display
 * 4. Has recycler view for assignments and alternate view if no assignments
 */
public class CycleDetailsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, MenteePickerFragment.MenteePickerDialogListener, AssignmentPickerFragment.AssignmentPickerDialogListener, View.OnClickListener {
    public static final String EXTRA_CYCLE_ID = "cycleId";
    private Cycle mCycle;
    private List<Mentee> mMentees;

    TextView cycleDisplayView;
    TextView startDateView;
    TextView endDateView;

    LinearLayout menteeListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_details);

        addNavigation();

        cycleDisplayView = findViewById(R.id.cycleDisplayName);
        startDateView = findViewById(R.id.cycleStartView);
        endDateView = findViewById(R.id.cycleEndDateView);
        menteeListContainer = findViewById(R.id.menteeListContainer);

        long cycleId = getIntent().getLongExtra(EXTRA_CYCLE_ID, -1);
        CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
        cycleViewModel.getCycleWithMentees(cycleId).observe(this, cycleWithMentees -> {
            mCycle = cycleWithMentees.getCycle();
            mMentees = cycleWithMentees.getMentees();
            renderCycleData();
            renderMenteeData();
        });
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
        if (item.getItemId() == R.id.action_add) {
            DialogFragment addMenteeDialog = new MenteePickerFragment();
            addMenteeDialog.show(getSupportFragmentManager(), "menteePicker");
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

    private void renderMenteeData() {
        menteeListContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Mentee mentee: mMentees) {
            LinearLayout menteeItem = (LinearLayout) inflater.inflate(R.layout.list_item_cycle_mentee, null);
            Button assignButton = menteeItem.findViewById(R.id.addAssignment);
            assignButton.setTag(mentee.getMenteeId());
            assignButton.setOnClickListener(this);
            Button removeButton = menteeItem.findViewById(R.id.removeMenteeFromCycle);
            removeButton.setTag(mentee.getMenteeId());
            removeButton.setOnClickListener(this);
            TextView nameView = menteeItem.findViewById(R.id.cycleMenteeName);
            nameView.setText(mentee.getName());
            menteeListContainer.addView(menteeItem);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCycle = null;
    }

    @Override
    public void onMenteeSelection(Mentee mentee) {
        CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
        cycleViewModel.addMentee(mentee.getMenteeId(), mCycle.getCycleId());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addAssignment) {
            DialogFragment addAssignmentDialog = new AssignmentPickerFragment();
            Bundle bundle = new Bundle();
            bundle.putLong(AssignmentPickerFragment.ARG_MENTEE_ID, (long) view.getTag());
            addAssignmentDialog.setArguments(bundle);
            addAssignmentDialog.show(getSupportFragmentManager(), "assignmentPicker");
        }
        if (view.getId() == R.id.removeMenteeFromCycle) {
            CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
            cycleViewModel.removeMentee((long) view.getTag(), mCycle.getCycleId());
        }
    }

    @Override
    public void onAssignmentSelection(Assignment assignment, long menteeId) {
        // TODO: add assignment to mentee with cycle id
    }
}