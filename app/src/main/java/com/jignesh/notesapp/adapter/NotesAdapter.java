package com.jignesh.notesapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jignesh.notesapp.R;
import com.jignesh.notesapp.activity.NoteActivity;
import com.jignesh.notesapp.models.NotesModel;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    Context context;
    List<NotesModel> allNotesModel;

    public NotesAdapter(Context context, List<NotesModel> alNotesModel) {
        this.context = context;
        this.allNotesModel = alNotesModel;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NotesModel note = allNotesModel.get(position);

        holder.tvNoteTitle.setText(note.noteTitle);
        holder.tvNoteBody.setText(note.noteBody);
        holder.tvNoteDate.setText(note.noteDate);

        switch (note.notePriority){
            case "1":
                holder.ivNotePriority.setBackgroundTintList(context.getResources().getColorStateList(R.color.green));
                break;

            case "2":
                holder.ivNotePriority.setBackgroundTintList(context.getResources().getColorStateList(R.color.amber));
                break;

            case "3":
                holder.ivNotePriority.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
                break;

            default:
                holder.ivNotePriority.setBackgroundTintList(context.getResources().getColorStateList(R.color.amber));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editNoteIntent = new Intent(context, NoteActivity.class);
                editNoteIntent.putExtra("action", "edit");
                editNoteIntent.putExtra("noteId", note.id);
                editNoteIntent.putExtra("noteTitle", note.noteTitle);
                editNoteIntent.putExtra("noteSubTitle", note.noteSubTitle);
                editNoteIntent.putExtra("noteBody", note.noteBody);
                editNoteIntent.putExtra("noteDate", note.noteDate);
                editNoteIntent.putExtra("notePriority", note.notePriority);

                context.startActivity(editNoteIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allNotesModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNoteTitle, tvNoteBody, tvNoteDate;
        ImageView ivNotePriority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNoteTitle = itemView.findViewById(R.id.tv_note_title);
            tvNoteBody = itemView.findViewById(R.id.tv_note_body);
            tvNoteDate = itemView.findViewById(R.id.tv_note_date);
            ivNotePriority = itemView.findViewById(R.id.iv_note_priority);
        }
    }
}
