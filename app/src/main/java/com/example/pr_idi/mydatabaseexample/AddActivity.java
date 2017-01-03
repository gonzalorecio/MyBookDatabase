package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzalo on 02/01/2017.
 */

public class AddActivity extends Activity {
    private EditText title;
    private EditText author;
    private EditText publisher;
    private EditText year;
    private EditText category;
    private RatingBar rating;
    private BookData bookData;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_view);


        bookData = new BookData(this);


        title = (EditText) findViewById(R.id.editText_title);
        author = (EditText) findViewById(R.id.editText_author);
        publisher = (EditText) findViewById(R.id.editText_publisher);
        year = (EditText) findViewById(R.id.editText_year);
        category = (EditText) findViewById(R.id.editText_category);
        rating = (RatingBar) findViewById(R.id.ratingBar);


        Button save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().length() != 0 &&
                        author.getText().length() != 0 &&
                        publisher.getText().length() != 0 &&
                        year.getText().length() != 0 &&
                        category.getText().length() != 0) {

                    bookData.open();
                    bookData.createBook(title.getText().toString(),
                            author.getText().toString(),
                            publisher.getText().toString(),
                            Integer.parseInt(year.getText().toString()),
                            category.getText().toString(),
                            Float.toString(rating.getRating()));

                    bookData.close();
                    Toast.makeText(getApplicationContext(),String.format("%s added to list.", title.getText().toString()), Toast.LENGTH_SHORT).show();
                    finish();

                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("Make sure to fill all fields. Write 'Unknown' if you are not sure about a field.")
                            .setTitle("Empty Fields");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}