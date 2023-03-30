package com.example.skillmanager.Activities.Mentee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.skillmanager.Activities.ViewModels.MenteeListViewModel;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

public class MenteeListActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_list);

        RecyclerView recyclerView = findViewById(R.id.mentee_list_recycler);
        TextView noDataView = findViewById(R.id.noMenteesListView);

        noDataView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        // Add adapter
        MenteeAdapter adapter = new MenteeAdapter();

        MenteeListViewModel menteeListViewModel = new ViewModelProvider(this).get(MenteeListViewModel.class);

        menteeListViewModel.getAllMentees().observe(this, mentees -> {
            adapter.submitList(mentees);
            if (!mentees.isEmpty()) {
                noDataView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mentees");
        setSupportActionBar(toolbar);
        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_navigation_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(this, MenteeEditActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}