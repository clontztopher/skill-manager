package com.example.skillmanager.Activities.Assessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

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

import com.example.skillmanager.Data.Entities.Assessment;
import com.example.skillmanager.Data.Repositories.AssessmentRepository;
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

public class AssessmentEditActivity extends AppCompatActivity implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    /**
     * Extra argument if editing an existing assessment
     */
    public static final String EXTRA_ASSESSMENT_ID = "assessmentId";

    /**
     * Extra argument if visiting activity from assignment's list of assessments
     */
    public static final String EXTRA_COURSE_ID = "assignmentId";
    private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Assessment to add/update
     */
    private Assessment mAssessment;

    /**
     * Whether assessment is being added or updated
     */
    private boolean addingNewAssessment;

    private TextView displayNameField;
    private TextView startDateField;
    private TextView endDateField;
    private Object activeDateSelectionView;
    private Spinner assessmentTypeSpinner;
    private ArrayAdapter<CharSequence> assessmentAdapter;
    private Switch startDateToggle;
    private Switch endDateToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        addToolBar();

        long assessmentId = getIntent().getLongExtra(EXTRA_ASSESSMENT_ID, -1);
        long associatedAssignmentId = getIntent().getLongExtra(EXTRA_COURSE_ID, -1);

        displayNameField = findViewById(R.id.assessmentNameField);
        startDateField = findViewById(R.id.assessmentStartDateField);
        endDateField = findViewById(R.id.assessmentEndDateField);
        startDateToggle = findViewById(R.id.assessmentStartDateAlert);
        endDateToggle = findViewById(R.id.assessmentEndDateAlert);

        startDateField.setOnFocusChangeListener(this);
        endDateField.setOnFocusChangeListener(this);

        assessmentTypeSpinner = findViewById(R.id.assessmentTypeSpinner);
        assessmentAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type_array, android.R.layout.simple_spinner_item);
        assessmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(assessmentAdapter);

        if (assessmentId == -1) {
            mAssessment = new Assessment();
            mAssessment.setAssocAssignmentId(associatedAssignmentId);
            addingNewAssessment = true;
            return;
        }

        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
        Future<Assessment> futureAssessment = assessmentRepo.findAssessmentById(assessmentId);
        try {
            mAssessment = futureAssessment.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        displayNameField.setText(mAssessment.getAssessmentTitle());
        startDateField.setText(mAssessment.getStartDate());
        endDateField.setText(mAssessment.getEndDate());
        startDateToggle.setChecked(mAssessment.hasStartAlert());
        endDateToggle.setChecked(mAssessment.hasEndAlert());
    }

    public void addToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add/Edit Assessment");
        setSupportActionBar(toolbar);
    }

    public void handleSaveAssessmentClick(View view) {
        String displayName = displayNameField.getText().toString();
        String startDateString = startDateField.getText().toString();
        String endDateString = endDateField.getText().toString();
        String assessmentType = assessmentTypeSpinner.getSelectedItem().toString();

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

        mAssessment.setAssessmentTitle(displayName);
        mAssessment.setStartDate(startDate.format(dtFormatter));
        mAssessment.setEndDate(endDate.format(dtFormatter));
        mAssessment.setType(assessmentType);
        mAssessment.setStartAlert(startDateToggle.isChecked());
        mAssessment.setEndAlert(endDateToggle.isChecked());

        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
        Future<?> assessmentEditFuture;

        if (addingNewAssessment) {
            assessmentEditFuture = assessmentRepo.insert(mAssessment);
        } else {
            assessmentEditFuture = assessmentRepo.update(mAssessment);
        }

        try {
            assessmentEditFuture.get();
            if (startDateToggle.isChecked() || endDateToggle.isChecked()) {
                setAlerts();
            }
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void deleteAssessment() {
        AssessmentRepository assessmentRepository = new AssessmentRepository(getApplication());
        Future<?> deleteFuture = assessmentRepository.delete(mAssessment);
        try {
            deleteFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
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
            Intent intent = new Intent(this, AssessmentListActivity.class);
            intent.putExtra(AssessmentListActivity.EXTRA_COURSE_ID, mAssessment.getAssocAssignmentId());
            deleteAssessment(); // Delete after getting assignment id
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Mandatory callback for interface
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if (!addingNewAssessment && adapterView.getId() == assessmentTypeSpinner.getId()) {
            assessmentTypeSpinner.setSelection(assessmentAdapter.getPosition(mAssessment.getType()));
        }
    }

    private void setAlerts() {
        Duration oneWeek = Duration.ofDays(7);
        if (startDateToggle.isChecked()) {
            LocalDateTime start = LocalDate.parse(mAssessment.getStartDate(), dtFormatter).atStartOfDay();
            LocalDateTime startAlertTime = start.minus(oneWeek);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(startAlertTime, ZoneId.systemDefault());
            long startAlertMillis = zonedDateTime.toInstant().toEpochMilli();
            Intent startAlertIntent = new Intent(AssessmentEditActivity.this, AlertIntentService.class);
            startAlertIntent.putExtra(AlertIntentService.EXTRA_ALERT_TITLE, "Assessment Alert: " + mAssessment.getAssessmentTitle() + " starts on " + mAssessment.getStartDate());
            PendingIntent startAlertSender = PendingIntent.getBroadcast(AssessmentEditActivity.this, ++AlertIntentService.numAlert, startAlertIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, startAlertMillis, startAlertSender);
        }
        if (endDateToggle.isChecked()) {
            LocalDateTime end = LocalDate.parse(mAssessment.getEndDate(), dtFormatter).atStartOfDay();
            LocalDateTime endAlertTime = end.minus(oneWeek);
            ZonedDateTime zonedDateTime = ZonedDateTime.of(endAlertTime, ZoneId.systemDefault());
            long endAlertMillis = zonedDateTime.toInstant().toEpochMilli();
            Intent endAlertIntent = new Intent(AssessmentEditActivity.this, AlertIntentService.class);
            endAlertIntent.putExtra(AlertIntentService.EXTRA_ALERT_TITLE, "Assessment Alert: " + mAssessment.getAssessmentTitle() + " ends on " + mAssessment.getEndDate());
            PendingIntent endAlertSender = PendingIntent.getBroadcast(AssessmentEditActivity.this, ++AlertIntentService.numAlert, endAlertIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, endAlertMillis, endAlertSender);
        }
    }
}