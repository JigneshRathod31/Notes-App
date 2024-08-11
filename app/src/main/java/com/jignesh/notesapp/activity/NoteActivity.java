package com.jignesh.notesapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.jignesh.notesapp.R;
import com.jignesh.notesapp.databinding.ActivityNoteBinding;
import com.jignesh.notesapp.models.NotesModel;
import com.jignesh.notesapp.viewmodel.NotesViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    ActivityNoteBinding binding;
    NotesViewModel notesViewModel;

    String action = "";
    int noteId;
    String noteTitle, noteSubTitle, noteBody, notePriority, noteDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.note_activity_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        action = getIntent().getStringExtra("action");

        notePriority = "2";

        if (action.equals("edit")){
            binding.ivDeleteNote.setVisibility(View.VISIBLE);
            setNoteDetails();
        }

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ivDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notesViewModel.deleteNote(noteId);

                navigateToMainActivity();
            }
        });

        binding.ivSetPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPriorityDialog();
            }
        });
    }

    private void showPriorityDialog(){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);

            View setPriorityDialogLayout = getLayoutInflater().inflate(R.layout.alert_dialog_priority, (ViewGroup) findViewById(R.id.alert_dialog_priority_root));

            AlertDialog alertDialog = builder.create();
            alertDialog.setView(setPriorityDialogLayout);
            builder.setCancelable(true);

            ImageView ivCancel = setPriorityDialogLayout.findViewById(R.id.iv_cancel);
            MaterialButtonToggleGroup tgNotePriority = setPriorityDialogLayout.findViewById(R.id.tg_note_priority);
            Button btnOk = setPriorityDialogLayout.findViewById(R.id.btn_ok);

            ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btnSelectedPriority = setPriorityDialogLayout.findViewById(tgNotePriority.getCheckedButtonId());
                    String priority = btnSelectedPriority.getText().toString().trim();

                    switch (priority){
                        case "Low":
                            notePriority = "1";
                            break;

                        case "Medium":
                            notePriority = "2";
                            break;

                        case "High":
                            notePriority = "3";
                            break;

                        default:
                            notePriority = "2";
                    }

                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setNoteDetails(){
        noteId = getIntent().getIntExtra("noteId", 0);
        noteTitle = getIntent().getStringExtra("noteTitle");
        noteSubTitle = getIntent().getStringExtra("noteSubTitle");
        noteBody = getIntent().getStringExtra("noteBody");
        noteDate = getIntent().getStringExtra("noteDate");
        notePriority = getIntent().getStringExtra("notePriority");

        binding.etNoteTitle.setText(noteTitle);
        binding.etNoteSubTitle.setText(noteSubTitle);
        binding.etNoteBody.setText(noteBody);
    }

    private void navigateToMainActivity(){
        Intent deleteNoteIntent = new Intent(this, MainActivity.class);

        deleteNoteIntent.putExtra("action", "delete");
        deleteNoteIntent.putExtra("noteId", noteId);
        deleteNoteIntent.putExtra("noteTitle", noteTitle);
        deleteNoteIntent.putExtra("noteSubTitle", noteSubTitle);
        deleteNoteIntent.putExtra("noteBody", noteBody);
        deleteNoteIntent.putExtra("noteDate", noteDate);
        deleteNoteIntent.putExtra("notePriority", notePriority);

        startActivity(deleteNoteIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");

        noteTitle = binding.etNoteTitle.getText().toString().trim();
        noteSubTitle = binding.etNoteSubTitle.getText().toString().trim();
        noteBody = binding.etNoteBody.getText().toString().trim();
        noteDate = dateFormatter.format(date);


        if (action.equals("add")){
            if (!noteTitle.isEmpty() || !noteSubTitle.isEmpty() || !noteBody.isEmpty()){
                NotesModel note = new NotesModel(noteTitle, noteSubTitle, noteBody, noteDate, notePriority);

                notesViewModel.insertNote(note);
            }
        }else{
            NotesModel note = new NotesModel(noteId, noteTitle, noteSubTitle, noteBody, noteDate, notePriority);

            if (!noteTitle.isEmpty() || !noteSubTitle.isEmpty() || !noteBody.isEmpty()){
                notesViewModel.updateNote(note);
            }else {
                notesViewModel.deleteNote(noteId);
            }
        }
    }
}