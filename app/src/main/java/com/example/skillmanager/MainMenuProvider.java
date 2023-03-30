package com.example.skillmanager;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.skillmanager.Activities.Assignment.AssignmentListActivity;
import com.example.skillmanager.Activities.Mentee.MenteeListActivity;
import com.example.skillmanager.Activities.Cycle.CycleListActivity;
import com.example.skillmanager.Activities.Reports.ReportsActivity;

public abstract class MainMenuProvider {
    public static boolean navItemSelected(MenuItem item, Context context) {
        switch(item.getItemId()) {
            case R.id.action_cycles: {
                Intent intent = new Intent(context, CycleListActivity.class);
                context.startActivity(intent);
                return true;
            }
            case R.id.action_assignments: {
                Intent intent = new Intent(context, AssignmentListActivity.class);
                context.startActivity(intent);
                return true;
            }
            case R.id.action_mentees: {
                Intent intent = new Intent(context, MenteeListActivity.class);
                context.startActivity(intent);
                return true;
            }
            case R.id.action_reports: {
                Intent intent = new Intent(context, ReportsActivity.class);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }
}
