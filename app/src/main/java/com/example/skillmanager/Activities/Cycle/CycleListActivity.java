package com.example.skillmanager.Activities.Cycle;

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

import com.example.skillmanager.Activities.ViewModels.CycleListViewModel;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

/**
 * Cycle List View
 *
 * 1. Lists all cycles
 * 2. Shows alternate view if no cycles
 * 3. Has "Add Cycle" button that goes to CycleEdit with no extra
 * 4. Cycles in list are clickable and go to CycleDetail with cycleId extra
 */
public class CycleListActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
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
            Intent intent = new Intent(this, CycleEditActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_list);
        addNavigation();

        TextView noCyclesView = findViewById(R.id.noCyclesView);
        RecyclerView recyclerView = findViewById(R.id.cycle_recycler);

        noCyclesView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        // Add adapter
        CycleAdapter cycleAdapter = new CycleAdapter();
        // Add data model
        CycleListViewModel cycleListViewModel = new ViewModelProvider(this).get(CycleListViewModel.class);
        cycleListViewModel.getAllCycles().observe(this, cycles -> {
            cycleAdapter.submitList(cycles);
            if (!cycles.isEmpty()) {
                noCyclesView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        // Set adapter
        recyclerView.setAdapter(cycleAdapter);
    }

    private void addNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Assignment Cycles");
        setSupportActionBar(toolbar);

        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }
}