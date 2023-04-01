package com.example.skillmanager.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.widget.ArrayAdapter;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class AssignmentPickerFragment extends DialogFragment {

    public static final String ARG_MENTEE_ID = "argMenteeId";

    public interface AssignmentPickerDialogListener {
        public void onAssignmentSelection(Assignment assignment, long menteeId);
    }

    private AssignmentPickerDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long menteeId = getArguments().getLong(ARG_MENTEE_ID);
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(getActivity().getApplication());

        List<Assignment> assignments = new ArrayList<>();
        ArrayAdapter<Assignment> assignmentAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1);
        AssignmentRepository assignmentRepository = new AssignmentRepository(db);
        Future<List<Assignment>> assignmentFuture = assignmentRepository.getAllAssignmentsSync();
        try {
            assignments = assignmentFuture.get();
            assignmentAdapter.addAll(assignments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Assignment");
        if (assignments.isEmpty()) {
            builder.setMessage("Please add assignments through the assignment menu to add to an assignment.")
                    .setPositiveButton("Okay", ((dialogInterface, i) -> { return; }));
        } else {
            builder.setAdapter(assignmentAdapter, (dialogInterface, i) -> listener.onAssignmentSelection(assignmentAdapter.getItem(i), menteeId));
        }


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AssignmentPickerDialogListener) {
            listener = (AssignmentPickerDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AssignmentPickerDialogListener.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}