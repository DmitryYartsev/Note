package com.example.dmitry.note;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.dmitry.note.data.NoteContract;
import com.example.dmitry.note.data.NotesDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NotesDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Log.i("Good","Good");

        }
        catch (Exception e){
            Log.i("Bad","Bad");
        }
        onAttachedToWindow();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Activity_create_note.class));

            }
        });
        mDbHelper = new NotesDbHelper(this);
        initViews();
        try{
        displayDatabaseInfo();
            displayDatabaseInfo();

            Log.i("Good","Good1");
        }
        catch(Exception e){
            Log.i("Bad","Bad1");
        }
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);

   }
    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Adapters_notes adapter = new Adapters_notes(displayDatabaseInfo(), getApplicationContext());
        recyclerView.setAdapter(adapter);
    }


   private List<Note> displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
       List<Note> list_of_project = new ArrayList<>();

        String[] projection = {
                NoteContract.MNote._ID,
                NoteContract.MNote.COLUMN_NAME,
                NoteContract.MNote.COLUMN_TEXT,
                NoteContract.MNote.COLUMN_LABEL,
                NoteContract.MNote.COLUMN_PASSWORD };

        // Делаем запрос
        Cursor cursor = db.query(
                NoteContract.MNote.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки


        try {
            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(NoteContract.MNote._ID);
            int nameColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_NAME);
            int textColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_TEXT);
            int labelColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_LABEL);
            int passwordColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_PASSWORD);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int ID = cursor.getInt(idColumnIndex);
                String Name = cursor.getString(nameColumnIndex);
                String Text= cursor.getString(textColumnIndex);
                String Label = cursor.getString(labelColumnIndex);
                int Password= cursor.getInt(passwordColumnIndex);

             /*   displayTextView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentCity + " - " +
                        currentGender + " - " +
                        currentAge));       */
               list_of_project.add(new Note(ID,Name,ID+" "+Name+" "+Text+" "+Password+" "+Label,Password,Label));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
       return list_of_project;
    }


}
