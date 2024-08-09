package com.jignesh.notesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jignesh.notesapp.models.NotesModel;
import com.jignesh.notesapp.repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    NotesRepository notesRepository;
    public LiveData<List<NotesModel>> allNotesList;
    public LiveData<List<NotesModel>> allNotesListLowToHigh;
    public LiveData<List<NotesModel>> allNotesListHighToLow;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        notesRepository = new NotesRepository(application.getApplicationContext());
        allNotesList = notesRepository.allNotesList;
        allNotesListLowToHigh = notesRepository.allNotesListLowToHigh;
        allNotesListHighToLow = notesRepository.allNotesListHighToLow;
    }

    public void insertNote(NotesModel notesModel){
        notesRepository.insertNote(notesModel);
    }

    public void updateNote(NotesModel notesModel){
        notesRepository.updateNote(notesModel);
    }

    public void deleteNote(int id){
        notesRepository.deleteNote(id);
    }
}
