package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Gonzalo on 03/01/2017.
 */

public class BookViewActivity extends AppCompatActivity {
    TextView title;
    TextView author;
    TextView publisher;
    TextView year;
    TextView category;
    RatingBar rating;
    String id;

    boolean edited;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_view);

        Intent intent = getIntent();

        //ActionBar settings
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Book Information");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        title = (TextView) findViewById(R.id.editText_title);
        author = (TextView) findViewById(R.id.editText_author);
        publisher = (TextView) findViewById(R.id.editText_publisher);
        year = (TextView) findViewById(R.id.editText_year);
        category = (TextView) findViewById(R.id.editText_category);
        rating = (RatingBar) findViewById(R.id.ratingBar);

        title.setText(intent.getStringExtra("bTitle"));
        author.setText(intent.getStringExtra("bAuthor"));
        publisher.setText(intent.getStringExtra("bPublisher").toUpperCase());
        year.setText(Integer.toString(intent.getIntExtra("bYear",-1)));
        category.setText(intent.getStringExtra("bCategory"));
        rating.setRating(Float.parseFloat(intent.getStringExtra("bRating")));
        id = Long.toString(intent.getLongExtra("bId",-1));

        Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button delete = (Button) findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                BookData bookData = new BookData(getApplicationContext());
                System.out.println(id);
                bookData.open();
                final Book book = bookData.getBook(id);

                //----------------------Dialog--------------------------------------------
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(book.getTitle()+" is going to be deleted.").setTitle("Delete Book");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        //.removeAt(position);
                        BookData bookData2 = new BookData(getApplicationContext());
                        bookData2.open();
                        bookData2.deleteBook(book);
                        bookData2.close();
                        Toast.makeText(getApplicationContext(), book.getTitle()+" has been deleted",
                                       Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
                //----------------------Dialog--------------------------------------------

                bookData.close();
            }
        });

        edited = false;
        final FloatingActionButton edit = (FloatingActionButton) findViewById(R.id.floatingActionButtonEdit);
        edit.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edited){
                    edit.setImageResource(R.drawable.tick);

                    rating.setIsIndicator(false);
                    rating.setEnabled(true);
                    Log.d("Edit", "CLICK " + rating.getRating());

                }else{
                    edit.setImageResource(android.R.drawable.ic_menu_edit);
                    rating.setRating(rating.getRating());
                    rating.setIsIndicator(true);
                    Log.d("Edit", "New num stars "+ rating.getRating() );
                    String message = "changes saved successfully";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                }
                edited = !edited;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
