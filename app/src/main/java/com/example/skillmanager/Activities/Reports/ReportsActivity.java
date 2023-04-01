package com.example.skillmanager.Activities.Reports;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Activities.ViewModels.ReportsViewModel;
import com.example.skillmanager.MainMenuProvider;
import com.example.skillmanager.R;
import com.google.android.material.navigation.NavigationBarView;

public class ReportsActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        TextView noReportsView = findViewById(R.id.noReportView);
        RecyclerView recyclerView = findViewById(R.id.reportRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ReportAdapter reportAdapter = new ReportAdapter();
        recyclerView.setAdapter(reportAdapter);

        ReportsViewModel reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        reportsViewModel.getEmailReportData().observe(this, emailReportItems -> {
            reportAdapter.submitList(emailReportItems);
            if (!emailReportItems.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                noReportsView.setVisibility(View.GONE);
            }
        });

        addNavigation();
    }

    private void addNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Email Report");
        setSupportActionBar(toolbar);
        NavigationBarView navBar = findViewById(R.id.main_nav);
        navBar.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return MainMenuProvider.navItemSelected(item, this);
    }
}