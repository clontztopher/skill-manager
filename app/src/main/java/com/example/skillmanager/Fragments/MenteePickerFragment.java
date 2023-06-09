package com.example.skillmanager.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.widget.ArrayAdapter;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Repositories.MenteeRepository;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class MenteePickerFragment extends DialogFragment {

    public interface MenteePickerDialogListener {
        public void onMenteeSelection(Mentee mentee);
    }

    private MenteePickerDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(getActivity().getApplication());
        List<Mentee> mentees = new ArrayList<>();
        ArrayAdapter<Mentee> menteeAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1);
        MenteeRepository menteeRepository = new MenteeRepository(db);
        Future<List<Mentee>> menteeFuture = menteeRepository.getAllMenteesSync();
        try {
            mentees = menteeFuture.get();
            menteeAdapter.addAll(mentees);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Mentee");
        if (mentees.isEmpty()) {
            builder.setMessage("Please add mentees through the mentee menu to add to a mentee.")
                    .setPositiveButton("Okay", ((dialogInterface, i) -> { return; }));
        } else {
            builder.setAdapter(menteeAdapter, (dialogInterface, i) -> listener.onMenteeSelection(menteeAdapter.getItem(i)));
        }


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenteePickerDialogListener) {
            listener = (MenteePickerDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MenteePickerDialogListener.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}