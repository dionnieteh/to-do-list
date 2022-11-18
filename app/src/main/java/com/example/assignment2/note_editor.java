package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

public class note_editor extends AppCompatActivity {
    int noteId; // we need to know the task ID, different task different ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        // get the edit text object from xml file
        EditText editText = (EditText) findViewById(R.id.editText);
        // get the intent object from main activity
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);
        // note id variable transferred from main activity

        if (noteId != -1) { // if note id is not -1, already has item
            // display the contents that you pull out from the file
            editText.setText(MainActivity.notes.get(noteId));
        } else {
            MainActivity.notes.add(""); // display nothing
            noteId = MainActivity.notes.size() - 1; // arraylist item start 0
            MainActivity.arrayAdapter.notifyDataSetChanged();
            // update arraylist
        }

        // the above if else code used to check whether the array list
        // is empty or not, if its empty display nothing, and add nothing
        // because this is the first time we used the app, there are no user
        // input in the shared preferences
        // we need add a text box event listener here to listen to the
        // events generated from the textbox, when the user type inside
        // the textbox save all the user input into shared preferences
        editText.addTextChangedListener(new TextWatcher() {
            // what happen before the changes in the textbox
            public void beforeTextChanged(CharSequence charSequence,
                                          int i, int i1, int i2) {
            } // nothing happen
            // what happen during the changes in the textbox
            // when the user input data in the textbox
            public void onTextChanged(CharSequence charSequence, int i, int i1,
                                      int i2) {
                // get user data stored in the array list
                // use array adapter update the list view with user input
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                // open the shared preferences object for user to write data
                // open the file for user to save data into the file
                SharedPreferences sharedPreferences =
                        getApplicationContext().getSharedPreferences
                                ("com.example.assignment2", Context.MODE_PRIVATE);
                // convert the notes array list into hash set
                // because SP cannot read data from notes array list
                HashSet<String> set = new HashSet(MainActivity.notes);
                // open the SP in edit mode
                // use put() method to save data into the SP
                // apply() means save the entire file, apply the changes
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            // what happen after the changes in the textbox
            // after the user leave the textbox
            public void afterTextChanged(Editable editable) {

            } // nothing happen
        }); // end of listener
    }

    public void back (View view){
        startActivity(new Intent(note_editor.this, MainActivity.class));
    }
}