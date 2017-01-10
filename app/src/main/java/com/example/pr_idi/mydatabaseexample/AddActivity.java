package com.example.pr_idi.mydatabaseexample;

import android.app.ActionBar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzalo on 02/01/2017.
 */

public class AddActivity extends AppCompatActivity {
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

        //Toolbar t = (Toolbar) findViewById(R.id.toolbar2);
        //setSupportActionBar(t);
        //ActionBar ab = getActionBar();
        //ab.setHomeButtonEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add a new book");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        MenuItem save = menu.findItem(R.id.save_book);

        save.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("Make sure to fill all fields. Write 'Unknown' or '-' if you are not sure about a field.")
                            .setTitle("Empty Fields Not Allowed");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return true;
            }
        });

        /*Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}