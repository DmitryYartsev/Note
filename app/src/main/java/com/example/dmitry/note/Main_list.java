package com.example.dmitry.note;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmitry.note.data.NoteContract;
import com.example.dmitry.note.data.NotesDbHelper;

import java.util.ArrayList;
import java.util.List;

public class Main_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener/* ,View.OnTouchListener*/ {
    private NotesDbHelper mDbHelper;
    private float x1, x2;
    private float y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onAttachedToWindow();

        mDbHelper = new NotesDbHelper(this);
        initViews();
        displayDatabaseInfo();
        TextView bar = (TextView) findViewById(R.id.bar);
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/anders.ttf");
        bar.setTypeface(custom_font2);


    }

    public void add(View v) {
        startActivity(new Intent(getApplicationContext(), Activity_create_note.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);

    }

    private void initViews() {
        List<Note> list_of_all_note = displayDatabaseInfo();
        List<Note> list_of_free_note = new ArrayList<>();
        for (int i = 0; i < list_of_all_note.size(); i++) {
            if (list_of_all_note.get(i).label.equals("free"))
                list_of_free_note.add((list_of_all_note.get(i)));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);/*{
            @Override
            public boolean canScrollVertically() {

                if (5*Math.abs(y1-y2)<Math.abs(x1-x2)) {
                  //  Log.i("moveto", "false");
                    return false;
                }  //   Log.i("moveto", "true");

                return true;
            }
        };*/
        recyclerView.setLayoutManager(linearLayoutManager);

        Adapters_notes adapter = new Adapters_notes(list_of_free_note, getApplicationContext());
        recyclerView.setAdapter(adapter);
        //recyclerView.setOnTouchListener(this);
    }
   /* @Override
    public boolean onTouch(View v, MotionEvent event)
    {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1= event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2=event.getY();

                if (5*Math.abs(y1-y2)<Math.abs(x1-x2)) {
                    Log.i("moveto", "false");}
                else  Log.i("moveto", "true");
        }
        return false;
    }*/

    private List<Note> displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Note> list_of_project = new ArrayList<>();

        String[] projection = {
                NoteContract.MNote._ID,
                NoteContract.MNote.COLUMN_NAME,
                NoteContract.MNote.COLUMN_TEXT,
                NoteContract.MNote.COLUMN_LABEL,
                NoteContract.MNote.COLUMN_PASSWORD};

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
                String Text = cursor.getString(textColumnIndex);
                String Label = cursor.getString(labelColumnIndex);
                int Password = cursor.getInt(passwordColumnIndex);

             /*   displayTextView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentCity + " - " +
                        currentGender + " - " +
                        currentAge));       */

                list_of_project.add(new Note(ID, Name, Text, Password, Label));

            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return list_of_project;
    }


    public void enterToPrivate(View view) {
        String password;
        SharedPreferences sharedPreferences = getSharedPreferences("password",
                Context.MODE_PRIVATE);
        password = sharedPreferences.getString("password", "e");
        if (!password.equals("e")) {
            startActivity(new Intent(this, Control.class));
        } else {
            startActivity(new Intent(this, CreatePassword.class));
        }
    }

}
