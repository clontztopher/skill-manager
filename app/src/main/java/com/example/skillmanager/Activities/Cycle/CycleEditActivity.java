package com.example.skillmanager.Activities.Cycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.Fragments.DatePickerFragment;
import com.example.skillmanager.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Add/Edit Cycle
 *
 * 1. Inputs: Cycle Name, Start Date, End Date, Assignment Add
 * 2. Assignment add goes to AssignmentEdit passing in cycleId for association
 * 3. Flag for edit/add decycleination. Accepts cycleId extra if editing.
 */
public class CycleEditActivity extends AppCompatActivity implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {
    public static final String ARG_CYCLE_ID = "cycleId";
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private boolean addingNewCycle = false;
    private Cycle mCycle;
    private TextView displayNameField;
    private TextView startDateField;
    private TextView endDateField;
    private Object activeDateSelectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_edit);

        long cycleId = getIntent().getLongExtra(ARG_CYCLE_ID, -1);

        addToolbar();

        displayNameField = findViewById(R.id.cycleNameField);
        startDateField = findViewById(R.id.cycleStartDateField);
        endDateField = findViewById(R.id.cycleEndDateField);

        startDateField.setOnFocusChangeListener(this);
        endDateField.setOnFocusChangeListener(this);

        // Adding new
        if (cycleId == -1) {
            mCycle = new Cycle();
            addingNewCycle = true;
            return;
        }

        CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
        cycleViewModel.getCycle(cycleId).observe(this, cycle -> {
            mCycle = cycle;
            displayNameField.setText(mCycle.getDisplayName());
            startDateField.setText(mCycle.getStartDateString());
            endDateField.setText(mCycle.getStartDateString());
        });
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add/Edit Assignment Cycle");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            deleteCycle();
            Intent intent = new Intent(this, CycleListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleSaveCycleClick(View view) {
        String displayName = displayNameField.getText().toString();
        String startDateString = startDateField.getText().toString();
        String endDateString = endDateField.getText().toString();

        if (displayName.equals("") || startDateString.equals("") || endDateString.equals("")) {
            Toast.makeText(this, "* All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(startDateString, dtFormatter);
            endDate = LocalDate.parse(endDateString, dtFormatter);
        } catch(Exception e) {
            Toast.makeText(this, "* Please use the correct date format: MM/DD/YYYY", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }

        if (startDate.isAfter(endDate)) {
            Toast.makeText(this, "Start date must be after end date.", Toast.LENGTH_SHORT).show();
            return;
        }

        mCycle.setDisplayName(displayName);
        mCycle.setStartDateString(startDate.format(dtFormatter));
        mCycle.setEndDateString(endDate.format(dtFormatter));

        CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);

        if (addingNewCycle) {
            cycleViewModel.insert(mCycle);
        } else {
            cycleViewModel.update(mCycle);
        }
        finish();
    }

    private void deleteCycle() {
        CycleViewModel cycleViewModel = new ViewModelProvider(this).get(CycleViewModel.class);
        cycleViewModel.delete(mCycle);
    }


    @Override
    public void onFocusChange(View view, boolean inFocus) {
        if (inFocus) {
            activeDateSelectionView = view.getTag();
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "datePicker");
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month + 1, day);
        String dateString = date.format(dtFormatter);

        if (activeDateSelectionView.equals(startDateField.getTag())) {
            startDateField.setText(dateString);
            startDateField.clearFocus();
        }

        if (activeDateSelectionView.equals(endDateField.getTag())) {
            endDateField.setText(dateString);
            endDateField.clearFocus();
        }

        activeDateSelectionView = null;
    }
}