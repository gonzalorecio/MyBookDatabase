package com.example.pr_idi.mydatabaseexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Menu;
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

    LayerDrawable ld;
    boolean edited;
    float currentRating;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_view);

        final Intent intent = getIntent();

        //ActionBar settings
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Book Information");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            //getSupportActionBar().setCustomView(android.R.drawable.ic_menu_delete);
        }

        title       = (TextView) findViewById(R.id.editText_title);
        author      = (TextView) findViewById(R.id.editText_author);
        publisher   = (TextView) findViewById(R.id.editText_publisher);
        year        = (TextView) findViewById(R.id.editText_year);
        category    = (TextView) findViewById(R.id.editText_category);
        rating      = (RatingBar)findViewById(R.id.ratingBar);

        title       .setText(intent.getStringExtra("bTitle"));
        author      .setText(intent.getStringExtra("bAuthor"));
        publisher   .setText(intent.getStringExtra("bPublisher").toUpperCase());
        year        .setText(Integer.toString(intent.getIntExtra("bYear",-1)));
        category    .setText(intent.getStringExtra("bCategory"));
        rating      .setRating(Float.parseFloat(intent.getStringExtra("bRating")));
        id          = Long.toString(intent.getLongExtra("bId",-1));


        ld = (LayerDrawable) rating.getProgressDrawable();
        resetColorStars();
        edited = false;
        final FloatingActionButton edit = (FloatingActionButton) findViewById(R.id.floatingActionButtonEdit);
        final FloatingActionButton undo = (FloatingActionButton) findViewById(R.id.floatingActionButtonUndo);
        edit.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRating = rating.getRating();
                if (!edited){
                    edit.setImageResource(R.drawable.tick);
                    undo.setVisibility(View.VISIBLE);

                    // Change color of stars
                    DrawableCompat.setTint(DrawableCompat.wrap(ld.getDrawable(2)),
                            ContextCompat.getColor(getApplicationContext(),
                                    android.R.color.holo_orange_light));

                    rating.setIsIndicator(false);
                    rating.setEnabled(true);
                    Log.d("Edit", "CLICK " + rating.getRating());
                    edited = true;

                }else{
                    edit.setImageResource(android.R.drawable.ic_menu_edit);
                    undo.setVisibility(View.INVISIBLE);
                    resetColorStars();
                    rating.setRating(rating.getRating());
                    rating.setIsIndicator(true);

                    BookData bookData = new BookData(getApplicationContext());
                    bookData.open();
                    Book book = bookData.getBook(id);
                    bookData.deleteBook(book);
                    Book newBook = bookData.createBook(title.getText().toString(),
                            author.getText().toString(),
                            publisher.getText().toString(),
                            Integer.parseInt(year.getText().toString()),
                            category.getText().toString(),
                            Float.toString(rating.getRating()));
                    id = Long.toString(newBook.getId());
                    bookData.close();

                    Log.d("Edit", "New num stars "+ rating.getRating() );
                    String message = "changes saved successfully";
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    edited = false;

                }
            }
        });

        undo.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setImageResource(android.R.drawable.ic_menu_edit);
                undo.setVisibility(View.INVISIBLE);
                resetColorStars();
                rating.setRating(currentRating);
                rating.setIsIndicator(true);
                edited = false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_book, menu);
        // final BookViewActivity bookViewActivity = this;

        MenuItem delete = menu.findItem(R.id.deleteBook);
        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                BookData bookData = new BookData(getApplicationContext());
                bookData.open();
                final Book book = bookData.getBook(id);

                //----------------------Dialog--------------------------------------------
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(BookViewActivity.this);
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
                return true;
            }
        });

        return true;
    }

    private void resetColorStars(){
        //Partial star
        DrawableCompat.setTint(DrawableCompat.wrap(ld.getDrawable(1)),
                ContextCompat.getColor(getApplicationContext(), android.R.color.background_light));
        // Custom star
        DrawableCompat.setTint(DrawableCompat.wrap(ld.getDrawable(2)),
                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
