package com.example.skillmanager.Activities.Cycle;

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
import android.widget.TextView;

import com.example.skillmanager.Activities.Assignment.AssignmentListActivity;
import com.example.skillmanager.Activities.Mentee.MenteeListActivity;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.concurrent.Future;

/**
 * Cycle Details
 *
 * 1. Accepts cycleId extra for retrieving cycle data
 * 2. Has one nav menu item for edit that goes to CycleEdit and passes cycleId extra
 * 3. Grabs cycle data and list of assignments associated with cycle for display
 * 4. Has recycler view for assignments and alternate view if no assignments
 */
public class CycleDetailsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    public static final String EXTRA_TERM_ID = "cycleId";
    private Cycle mCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_details);

        addNavigation();

        long cycleId = getIntent().getLongExtra(EXTRA_TERM_ID, -1);
        CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
        cycleViewModel.getCycle(cycleId).observe(this, cycle -> {
            mCycle = cycle;
            render();
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
        inflater.inflate(R.menu.details_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(this, CycleEditActivity.class);
            intent.putExtra(CycleEditActivity.ARG_TERM_ID, mCycle.getId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void render() {
        TextView cycleDisplayView = findViewById(R.id.cycleDisplayName);
        TextView startDateView = findViewById(R.id.cycleStartView);
        TextView endDateView = findViewById(R.id.cycleEndDateView);

        cycleDisplayView.setText(mCycle.getDisplayName());
        String startDateStr = mCycle.getStartDateString();
        String endDateStr = mCycle.getEndDateString();
        startDateView.setText(startDateStr);
        endDateView.setText(endDateStr);
    }

    public void handleViewMenteesClick(View view) {
//        Intent intent = new Intent(this, MenteeListActivity.class);
//        intent.putExtra(MenteeListActivity.EXTRA_CYCLE_ID, mCycle.getId());
//        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCycle = null;
    }
}