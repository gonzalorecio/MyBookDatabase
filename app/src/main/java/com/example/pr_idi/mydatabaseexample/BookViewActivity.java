package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

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


        Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
