package com.jignesh.notesapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.jignesh.notesapp.dao.NotesDao;
import com.jignesh.notesapp.database.NotesDatabase;
import com.jignesh.notesapp.models.NotesModel;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;

    public LiveData<List<NotesModel>> allNotesList;
    public LiveData<List<NotesModel>> allNotesListLowToHigh;
    public LiveData<List<NotesModel>> allNotesListHighToLow;

    public NotesRepository(Context context) {
        NotesDatabase notesDatabase = NotesDatabase.getNotesDatabaseInstance(context);
        notesDao = notesDatabase.notesDao();
        allNotesList = notesDao.getAllNotes();
        allNotesListLowToHigh = notesDao.getAllNotesLowToHigh();
        allNotesListHighToLow = notesDao.getAllNotesHighToLow();
    }

    public void insertNote(NotesModel notesModel){
        notesDao.insertNote(notesModel);
    }

    public void updateNote(NotesModel notesModel){
        notesDao.updateNote(notesModel);
    }

    public void deleteNote(int id){
        notesDao.deleteNote(id);
    }
}
