package com.example.skillmanager.Activities.Mentee;

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
import android.widget.Toast;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Activities.ViewModels.MenteeViewModel;
import com.example.skillmanager.R;
public class MenteeEditActivity extends AppCompatActivity {
    public static final String EXTRA_MENTEE_ID = "menteeId";
    private Mentee mMentee;
    private boolean addingNewMentee;
    private TextView nameField;
    private TextView emailField;
    private TextView phoneField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_edit);
        addToolBar();

        long menteeId = getIntent().getLongExtra(EXTRA_MENTEE_ID, -1);

        nameField = findViewById(R.id.menteeNameField);
        emailField = findViewById(R.id.menteeEmailField);
        phoneField = findViewById(R.id.menteePhoneField);

        if (menteeId == -1) {
            mMentee = new Mentee();
            addingNewMentee = true;
            return;
        }

        MenteeViewModel menteeViewModel = new ViewModelProvider(this).get(MenteeViewModel.class);
        menteeViewModel.findById(menteeId).observe(this, mentee -> {
            mMentee = mentee;
            nameField.setText(mMentee.getName());
            emailField.setText(mMentee.getEmail());
            phoneField.setText(mMentee.getPhone());
        });
    }

    public void addToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add/Edit Mentee");
        setSupportActionBar(toolbar);
    }

    public void handleSaveMenteeClick(View view) {
        String instName = nameField.getText().toString();
        String instEmail = emailField.getText().toString();
        String instPhone = phoneField.getText().toString();

        if (instName.equals("") || instEmail.equals("") || instPhone.equals("")) {
            Toast.makeText(this, "* All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        mMentee.setName(instName);
        mMentee.setEmail(instEmail);
        mMentee.setPhone(instPhone);

        MenteeViewModel menteeViewModel = new ViewModelProvider(this).get(MenteeViewModel.class);

        if (addingNewMentee) {
            menteeViewModel.insert(mMentee);
        } else {
            menteeViewModel.update(mMentee);
        }

        finish();
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
            MenteeViewModel menteeViewModel = new ViewModelProvider(this).get(MenteeViewModel.class);
            menteeViewModel.delete(mMentee);
            Intent intent = new Intent(this, MenteeListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}