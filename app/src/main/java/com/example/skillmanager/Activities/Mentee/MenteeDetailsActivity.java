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
import android.widget.TextView;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Repositories.MenteeRepository;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

import java.util.concurrent.Future;

public class MenteeDetailsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public static final String EXTRA_MENTEE_ID = "menteeId";
    private Mentee mMentee;

    TextView menteeNameView;
    TextView menteeEmailView;
    TextView menteePhoneView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(this, MenteeEditActivity.class);
            intent.putExtra(MenteeEditActivity.EXTRA_MENTEE_ID, mMentee.getMenteeId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_details);
        addToolbar();

        menteeNameView = findViewById(R.id.menteeNameView);
        menteeEmailView = findViewById(R.id.menteeEmailView);
        menteePhoneView = findViewById(R.id.menteePhoneView);

        long menteeId = getIntent().getLongExtra(EXTRA_MENTEE_ID, -1);
        MenteeViewModel menteeViewModel = new ViewModelProvider(this).get(MenteeViewModel.class);
        menteeViewModel.findById(menteeId).observe(this, mentee -> {
            mMentee = mentee;
            menteeNameView.setText(mMentee.getName());
            menteeEmailView.setText(mMentee.getEmail());
            menteePhoneView.setText(mMentee.getPhone());
        });
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mentee Information");
        setSupportActionBar(toolbar);

        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMentee = null;
    }
}