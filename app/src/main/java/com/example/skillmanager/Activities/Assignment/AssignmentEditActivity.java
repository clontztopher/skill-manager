package com.example.skillmanager.Activities.Assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssessmentRepository;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Fragments.DatePickerFragment;
import com.example.skillmanager.R;
import com.example.skillmanager.Utility.AlertIntentService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Future;

public class AssignmentEditActivity extends AppCompatActivity implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    public static final String EXTRA_ASSIGNMENT_ID = "assignmentId";
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private boolean addingNewAssignment = false;
    private Assignment mAssignment;
    private TextView assignmentTitleField;
    private TextView startDateField;
    private TextView endDateField;
    private Spinner statusSpinner;
    private TextView assignmentNotesField;
    private Switch startAlertToggle;
    private Switch endAlertToggle;
    private Object activeDateSelection;
    private ArrayAdapter<CharSequence> statusSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_edit);

        long assignmentId = getIntent().getLongExtra(EXTRA_ASSIGNMENT_ID, -1);

        addToolbar();

        assignViews();

        if (assignmentId == -1) {
            mAssignment = new Assignment();
            addingNewAssignment = true;
            return;
        }

        AssignmentViewModel assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        assignmentViewModel.findById(assignmentId).observe(this, assignment -> {
            mAssignment = assignment;
            setAssignmentViews();
        });
    }

    private void assignViews() {
        // Text input fields
        assignmentTitleField = findViewById(R.id.assignmentTitleField);
        assignmentNotesField = findViewById(R.id.assignmentNotesInput);

        // Date fields
        startDateField = findViewById(R.id.assignmentStartDateField);
        endDateField = findViewById(R.id.assignmentEndDateField);
        startDateField.setOnFocusChangeListener(this);
        endDateField.setOnFocusChangeListener(this);

        // Spinners
        statusSpinner = findViewById(R.id.statusSpinner);
        statusSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.assignment_status_array, android.R.layout.simple_spinner_item);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusSpinnerAdapter);

        // Alert Toggles
        startAlertToggle = findViewById(R.id.assignmentStartAlertSwitch);
        endAlertToggle = findViewById(R.id.assignmentEndAlertSwitch);
    }

    private void setAssignmentViews() {
        assignmentTitleField.setText(mAssignment.getTitle());
        startDateField.setText(mAssignment.getStartDateString());
        endDateField.setText(mAssignment.getEndDateString());
        assignmentNotesField.setText(mAssignment.getNotes());
        startAlertToggle.setChecked(mAssignment.hasStartAlert());
        endAlertToggle.setChecked(mAssignment.hasEndAlert());
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add/Edit Assignment");
        setSupportActionBar(toolbar);
    }

    public void handleSaveAssignmentClick(View view) {
        String assignmentTitle = assignmentTitleField.getText().toString();
        String assignmentStartDateString = startDateField.getText().toString();
        String assignmentEndDateString = endDateField.getText().toString();
        String assignmentNotesString = assignmentNotesField.getText().toString();
        String assignmentStatus = statusSpinner.getSelectedItem().toString();

        if (assignmentTitle.equals("") || assignmentEndDateString.equals("") || assignmentStartDateString.equals("")) {
            Toast.makeText(this, "Missing required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(assignmentStartDateString, dtFormatter);
            endDate = LocalDate.parse(assignmentEndDateString, dtFormatter);
        } catch (Exception e) {
            Toast.makeText(this, "Please use the correct date format: MM/DD/YYYY", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }

        if (startDate.isAfter(endDate)) {
            Toast.makeText(this, "Start date must be after end date.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAssignment.setTitle(assignmentTitle);
        mAssignment.setStartDateString(startDate.format(dtFormatter));
        mAssignment.setEndDateString(endDate.format(dtFormatter));
        mAssignment.setStatus(assignmentStatus);
        mAssignment.setNotes(assignmentNotesString);
        mAssignment.setStartAlert(startAlertToggle.isChecked());
        mAssignment.setEndAlert(endAlertToggle.isChecked());

        AssignmentViewModel assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        if (addingNewAssignment) {
            assignmentViewModel.insert(mAssignment);
        } else {
            assignmentViewModel.update(mAssignment);
        }
        finish();
    }

    @Override
    public void onFocusChange(View view, boolean inFocus) {
        if(inFocus) {
            activeDateSelection = view.getTag();
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "datePicker");
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month + 1, day);
        String dateString = date.format(dtFormatter);

        if (activeDateSelection.equals(startDateField.getTag())) {
            startDateField.setText(dateString);
            startDateField.clearFocus();
        }

        if (activeDateSelection.equals(endDateField.getTag())) {
            endDateField.setText(dateString);
            endDateField.clearFocus();
        }

        activeDateSelection = null;
    }

    private void deleteAssignment() {
        AssignmentViewModel assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        assignmentViewModel.delete(mAssignment);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Mandatory callback for interface
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if (!addingNewAssignment && adapterView.getId() == statusSpinner.getId()) {
            adapterView.setSelection(statusSpinnerAdapter.getPosition(mAssignment.getStatus()));
        }
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
            deleteAssignment();
            Intent intent = new Intent(this, AssignmentListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void setAlerts() {
//        Duration oneWeek = Duration.ofDays(7);
//        if (startAlertToggle.isChecked()) {
//            LocalDateTime start = LocalDate.parse(assignment.getStartDateString(), dtFormatter).atStartOfDay();
//            LocalDateTime startAlertTime = start.minus(oneWeek);
//            ZonedDateTime zonedDateTime = ZonedDateTime.of(startAlertTime, ZoneId.systemDefault());
//            long startAlertMillis = zonedDateTime.toInstant().toEpochMilli();
//            Intent startAlertIntent = new Intent(AssignmentEditActivity.this, AlertIntentService.class);
//            startAlertIntent.putExtra(AlertIntentService.EXTRA_ALERT_TITLE, "Assignment Alert: " + assignment.getTitle() + " starts on " + assignment.getStartDateString());
//            PendingIntent startAlertSender = PendingIntent.getBroadcast(AssignmentEditActivity.this, ++AlertIntentService.numAlert, startAlertIntent, PendingIntent.FLAG_IMMUTABLE);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, startAlertMillis, startAlertSender);
//        }
//        if (endAlertToggle.isChecked()) {
//            LocalDateTime end = LocalDate.parse(assignment.getEndDateString(), dtFormatter).atStartOfDay();
//            LocalDateTime endAlertTime = end.minus(oneWeek);
//            ZonedDateTime zonedDateTime = ZonedDateTime.of(endAlertTime, ZoneId.systemDefault());
//            long endAlertMillis = zonedDateTime.toInstant().toEpochMilli();
//            Intent endAlertIntent = new Intent(AssignmentEditActivity.this, AlertIntentService.class);
//            endAlertIntent.putExtra(AlertIntentService.EXTRA_ALERT_TITLE, "Assignment Alert: " + assignment.getTitle() + " ends on " + assignment.getEndDateString());
//            PendingIntent endAlertSender = PendingIntent.getBroadcast(AssignmentEditActivity.this, ++AlertIntentService.numAlert, endAlertIntent, PendingIntent.FLAG_IMMUTABLE);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, endAlertMillis, endAlertSender);
//        }
//    }
}