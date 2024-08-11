package com.jignesh.notesapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.jignesh.notesapp.R;
import com.jignesh.notesapp.adapter.NotesAdapter;
import com.jignesh.notesapp.databinding.ActivityMainBinding;
import com.jignesh.notesapp.models.NotesModel;
import com.jignesh.notesapp.viewmodel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NotesViewModel notesViewModel;

    NotesAdapter notesAdapter;

    ArrayList<NotesModel> searchedNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        notesViewModel.allNotesList.observe(this, notesList -> {
            showNotes(notesList);
        });
        
        String action = getIntent().getStringExtra("action");

        if (action != null && action.equals("delete")){

            int noteId = getIntent().getIntExtra("noteId", -1);
            String noteTitle = getIntent().getStringExtra("noteTitle");
            String noteSubTitle = getIntent().getStringExtra("noteSubTitle");
            String noteBody = getIntent().getStringExtra("noteBody");
            String noteDate = getIntent().getStringExtra("noteDate");
            String notePriority = getIntent().getStringExtra("notePriority");
            
            if (noteId != -1){
                notesViewModel.deleteNote(noteId);

                NotesModel note = new NotesModel(noteId, noteTitle, noteSubTitle, noteBody, noteDate, notePriority);
                Snackbar snackbar = Snackbar.make(binding.main, "Note deleted successfully.", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notesViewModel.insertNote(note);
                    }
                });
                snackbar.show();
            }else {
                Toast.makeText(this, "Id not found to delete the note...", Toast.LENGTH_SHORT).show();
            }
        }

        binding.searchBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.mi_no_filter){
                    filterNotes(0);
                } else if (item.getItemId() == R.id.mi_low_to_high){
                    filterNotes(1);
                } else if (item.getItemId() == R.id.mi_high_to_low) {
                    filterNotes(2);
                }

                return true;
            }
        });

        binding.searchView.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String searchedText = textView.getText().toString().trim();

                binding.searchView.hide();
                binding.searchBar.setText(searchedText);

                return true;
            }
        });

        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterSearchedNotes(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tvNoFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterNotes(0);
                binding.tvNoFilter.setBackgroundResource(R.drawable.rounded_selected_bg);
                binding.tvLowToHigh.setBackgroundResource(R.drawable.rounded_bg);
                binding.tvHighToLow.setBackgroundResource(R.drawable.rounded_bg);
            }
        });

        binding.tvLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterNotes(1);
                binding.tvNoFilter.setBackgroundResource(R.drawable.rounded_bg);
                binding.tvLowToHigh.setBackgroundResource(R.drawable.rounded_selected_bg);
                binding.tvHighToLow.setBackgroundResource(R.drawable.rounded_bg);
            }
        });

        binding.tvHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterNotes(2);
                binding.tvNoFilter.setBackgroundResource(R.drawable.rounded_bg);
                binding.tvLowToHigh.setBackgroundResource(R.drawable.rounded_bg);
                binding.tvHighToLow.setBackgroundResource(R.drawable.rounded_selected_bg);
            }
        });

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNoteIntent = new Intent(MainActivity.this, NoteActivity.class);
                addNoteIntent.putExtra("action", "add");

                startActivity(addNoteIntent);
            }
        });
    }

    private void filterSearchedNotes(String searchQuery){
        searchQuery = searchQuery.toLowerCase();

        searchedNotes.clear();

        if (!searchQuery.isEmpty()){
            for (NotesModel note: notesViewModel.allNotesList.getValue()){
                if (note.noteTitle.toLowerCase().contains(searchQuery) || note.noteBody.toLowerCase().contains(searchQuery)){
                    searchedNotes.add(note);
                }
            }
        }

        showSearchedNotes();
    }

    private void showSearchedNotes(){
        binding.rvSearchedNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(MainActivity.this, searchedNotes);
        binding.rvSearchedNotes.setAdapter(notesAdapter);
    }

    private void filterNotes(int i){
        if (i == 0){
            notesViewModel.allNotesList.observe(this, notesList -> {
                showNotes(notesList);
            });
        } else if (i == 1) {
            notesViewModel.allNotesListLowToHigh.observe(this, notesList -> {
                showNotes(notesList);
            });
        }else {
            notesViewModel.allNotesListHighToLow.observe(this, notesList -> {
                showNotes(notesList);
            });
        }
    }

    private void showNotes(List<NotesModel> notesList){
        binding.rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(MainActivity.this, notesList);
        binding.rvNotes.setAdapter(notesAdapter);
    }
}