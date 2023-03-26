package com.example.skillmanager.Activities.Assignment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Project;
import com.example.skillmanager.Data.Entities.Study;
import com.example.skillmanager.Fragments.DatePickerFragment;
import com.example.skillmanager.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AssignmentEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_ASSIGNMENT_ID = "assignmentId";
    private boolean addingNewAssignment = false;
    private Assignment mAssignment;
    private TextView titleField;
    private TextView topicField;
    private Spinner typeSpinner;
    private TextView requirementsField;
    private TextView referenceField;
    private TextView questionsField;
    private LinearLayout projectFields;
    private LinearLayout studyFields;
    private ArrayAdapter<CharSequence> typeSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_edit);

        long assignmentId = getIntent().getLongExtra(EXTRA_ASSIGNMENT_ID, -1);

        addToolbar();

        titleField = findViewById(R.id.assignmentTitleField);
        topicField = findViewById(R.id.topicField);
        projectFields = findViewById(R.id.projectFields);
        requirementsField = projectFields.findViewById(R.id.requirementsField);
        studyFields = findViewById(R.id.studyFields);
        referenceField = studyFields.findViewById(R.id.referenceField);
        questionsField = studyFields.findViewById(R.id.questionsField);

        typeSpinner = findViewById(R.id.typeSpinner);
        typeSpinner.setOnItemSelectedListener(this);
        typeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.assignment_type_array, android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);

        if (assignmentId == -1) {
            mAssignment = new Assignment();
            addingNewAssignment = true;
            projectFields.setVisibility(View.VISIBLE);
            return;
        }

        AssignmentViewModel assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        assignmentViewModel.findById(assignmentId).observe(this, assignment -> {
            mAssignment = assignment;
            setAssignmentViews();
        });
    }

    private void setAssignmentViews() {
        titleField.setText(mAssignment.getTitle());
        topicField.setText(mAssignment.getTopic());
        typeSpinner.setSelection(typeSpinnerAdapter.getPosition(mAssignment.getType().toString()));

        if (mAssignment.getType() == Assignment.AssignmentType.PROJECT) {
            Project project = mAssignment.getProject();
            projectFields.setVisibility(View.VISIBLE);
            requirementsField.setText(project.getRequirements());
        }

        if (mAssignment.getType() == Assignment.AssignmentType.STUDY) {
            Study study = mAssignment.getStudy();
            referenceField.setText(study.getReference());
            questionsField.setText(study.getStudyQuestions());
        }
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add/Edit Assignment");
        setSupportActionBar(toolbar);
    }

    public void handleSaveAssignmentClick(View view) {
        Project project = new Project();
        Study study = new Study();

        String title = titleField.getText().toString();
        String topic = topicField.getText().toString();
        Assignment.AssignmentType type = Assignment.AssignmentType.fromString(typeSpinner.getSelectedItem().toString());

        mAssignment.setTitle(title);
        mAssignment.setTopic(topic);
        mAssignment.setType(type);

        if (type == Assignment.AssignmentType.PROJECT) {
            String requirements = requirementsField.getText().toString();
            project.setRequirements(requirements);
        }

        if (type == Assignment.AssignmentType.STUDY) {
            String reference = referenceField.getText().toString();
            String questions = questionsField.getText().toString();
            study.setReference(reference);
            study.setStudyQuestions(questions);
        }

        mAssignment.setProject(project);
        mAssignment.setStudy(study);

//        if (title.equals("") || topic.equals("") || assignmentStartDateString.equals("")) {
//            Toast.makeText(this, "Missing required fields.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        AssignmentViewModel assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        if (addingNewAssignment) {
            assignmentViewModel.insert(mAssignment);
        } else {
            assignmentViewModel.update(mAssignment);
        }
        finish();
    }

    private void deleteAssignment() {
        AssignmentViewModel assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        assignmentViewModel.delete(mAssignment);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() != typeSpinner.getId()) {
            return;
        }

        Assignment.AssignmentType assignmentType = Assignment.AssignmentType.fromString(adapterView.getSelectedItem().toString());

        if (assignmentType == Assignment.AssignmentType.PROJECT) {
            projectFields.setVisibility(View.VISIBLE);
            studyFields.setVisibility(View.GONE);
        }

        if (assignmentType == Assignment.AssignmentType.STUDY) {
            studyFields.setVisibility(View.VISIBLE);
            projectFields.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if (!addingNewAssignment && adapterView.getId() == typeSpinner.getId()) {
            adapterView.setSelection(typeSpinnerAdapter.getPosition(mAssignment.getType().toString()));
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